using UnityEngine;
using System.IO;
using System;
using System.Collections.Generic;
using System.Linq;

public class Manager : MonoBehaviour
{
    public GameObject city;
    [SerializeField]
    private GameObject wirePrefab;
    [SerializeField]
    private GameObject infoPanelPrefab;
    [SerializeField]
    private bool repeat = false;

    private List<GameObject> wireGOs = new List<GameObject>();

    public executeSimulation.SimulationInput simulationInput;

    public static Manager Instance { get; private set; }

    private executeSimulation simulation;

    void Start ()
    {
        Application.runInBackground = true;

        simulationInput.folder = Application.dataPath + "/Simulator";
        simulationInput.startingHour = 6;
        simulationInput.startingMinute = 0;
        simulationInput.endingHour = 18;
        simulationInput.endingMinute = 0;
        simulationInput.timeStep = 5;
        
        if (Instance == null)
        {
            Instance = this;
        }
        else
        {
            Destroy(gameObject);
        }
        DontDestroyOnLoad(gameObject);
	}

    public void setCity(GameObject city)
    {
        this.city = city;
        GetComponent<MeshRenderer>().enabled = true;
        GetComponent<BoxCollider>().enabled = true;
    }

    void OnMouseDown()
    {
        // Compute net
        Dictionary<int,int> wires = addWires();
        saveEdges(wires);
        
        simulation = new executeSimulation();
        simulation.input = simulationInput;
        simulation.Start();
        GetComponent<Animator>().enabled = true;
        
        createWires(wires);
        //simulationReady(Application.dataPath+"/Simulator/simulation.json");
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            #if UNITY_EDITOR
                        UnityEditor.EditorApplication.isPlaying = false;
            #elif UNITY_WEBPLAYER
                     Application.OpenURL(webplayerQuitURL);
            #else
                     Application.Quit();
            #endif
        }

        if( simulation != null && simulation.Update() )
        {
            simulation = null;
        }
        
    }

    public void simulationReady( string jsonPath )
    {
        print("ready");
        createPanels( jsonPath );
        parseJSON(jsonPath);
        foreach( Transform house in city.transform )
        {
            House h = house.GetComponentInChildren<House>();
            if (h != null) h.ready();
            Spark spark = house.GetComponentInChildren<Spark>();
            if (spark != null) spark.ready();
        }
    }

    public bool repeatAnimation() { return repeat; }

    private void createPanels( string jsonPath )
    {
        string sjson = File.ReadAllText(jsonPath);
        JSONObject jsonObject = new JSONObject(sjson);

        JSONObject frame = jsonObject.GetField("frames").list[0];
        
        JSONObject houses = frame.GetField("houses");
        foreach (JSONObject house in houses.list)
        {
            int id = int.Parse(house.GetField("id").ToString());
            Transform t = city.transform.GetChild(id);
            print(id);

            // Add collider and info panel
            BoxCollider collider = t.gameObject.AddComponent<BoxCollider>();
            GameObject infoPanel = Instantiate(infoPanelPrefab, t.position + collider.size.y * Vector3.up, Quaternion.identity) as GameObject;
            infoPanel.transform.SetParent(t);
            House h = infoPanel.GetComponent<House>();
            ShowHide showHide = t.gameObject.AddComponent<ShowHide>();
            showHide.target = infoPanel;

            JSONObject appliances = house.GetField("appliances");
            foreach (JSONObject appliance in appliances.list)
            {
                string type = appliance.GetField("type").ToString().Replace("\"", "");
                h.addAppliance((ApplianceType)Enum.Parse(typeof(ApplianceType), type));
            }

            JSONObject generators = house.GetField("generators");
            foreach (JSONObject generator in generators.list)
            {
                string type = generator.GetField("type").ToString().Replace("\"", "");
                h.addGenerator((GeneratorType)Enum.Parse(typeof(GeneratorType), type.Trim()));
            }
        }
    }

    public void parseJSON( string jsonPath )
    {
        string sjson = File.ReadAllText(jsonPath);
        JSONObject jsonObject = new JSONObject(sjson);

        JSONObject frames = jsonObject.GetField("frames");
        foreach (JSONObject frame in frames.list)
        {
            /*
            JSONObject moment = frame.GetField("moment");
            string sMoment = moment.GetField("hour").ToString() + ":" + moment.GetField("minute").ToString();

            JSONObject weather = frame.GetField("weather");
            float cloudPercentage = float.Parse(weather.GetField("cloudPercentage").ToString());
            float windSpeed  = float.Parse(weather.GetField("windSpeed").ToString());
            */

            JSONObject houses = frame.GetField("houses");
            foreach (JSONObject house in houses.list)
            {
                int id = int.Parse(house.GetField("id").ToString());

                House h = city.transform.GetChild(id).GetComponentInChildren<House>();
                
                
                JSONObject battery = house.GetField("battery");
                float level = float.Parse(battery.GetField("level").ToString());
                float capacity = float.Parse(battery.GetField("capacity").ToString());

                h.addBatteryPercent(level / capacity);

                JSONObject appliances = house.GetField("appliances");
                foreach (JSONObject appliance in appliances.list)
                {
                    int applianceId = int.Parse(appliance.GetField("id").ToString());

                    float progress = float.Parse(appliance.GetField("progress").ToString());
                    h.addApplianceProgress(applianceId, progress);
                }

                JSONObject generators = house.GetField("generators");
                foreach (JSONObject generator in generators.list)
                {
                    int generatorId = int.Parse(generator.GetField("id").ToString());

                    //float productionPerHour = float.Parse(generator.GetField("productionPerHour").ToString());
                    float efficiency = float.Parse(generator.GetField("efficiency").ToString());
                    h.addGeneratorEfficiency(generatorId, efficiency);
                }

                string plotFile = house.GetField("bid").GetField("plotFile").ToString();
                h.addBidPlot(plotFile);
                /*
                float totalTraded = float.Parse(house.GetField("totalTraded").ToString());
                float totalGenerated = float.Parse(house.GetField("totalGenerated").ToString());
                float totalApplied = float.Parse(house.GetField("totalApplied").ToString());
                float baseConsum = float.Parse(house.GetField("baseConsum").ToString());
                float balance = float.Parse(house.GetField("balance").ToString());
                */
            }
            
            JSONObject wires = frame.GetField("wires");
            foreach (JSONObject wire in wires.list)
            {

                int origin = int.Parse(wire.GetField("originId").ToString());
                int destination = int.Parse(wire.GetField("destinationId").ToString());
                
                float flow = float.Parse(wire.GetField("flow").ToString());

                foreach (GameObject w in wireGOs)
                {
                    Spark spark = w.GetComponentInChildren<Spark>();
                    if (spark.start == city.transform.GetChild(origin).position && spark.end == city.transform.GetChild(destination).position)
                        spark.addFlow(flow);
                }
            }
        }
    }

    public Dictionary<int,int> addWires()
    {
        Dictionary<int, List<int>> dontAssign = new Dictionary<int, List<int>>();
        Dictionary<int, int> edges = new Dictionary<int, int>();
        for ( int i = 0; i < city.transform.childCount - 1; i++ )
        {
            int pair = (i + 1) % city.transform.childCount;
            Transform house = city.transform.GetChild(i);

            float minDistance = Vector3.Distance(house.position, city.transform.GetChild(pair).position);

            for (int j = 0; j < city.transform.childCount; j++ )
            {
                if ( dontAssign.ContainsKey(j) && !dontAssign[j].Contains(i) && i != j) 
                {
                    float distance = Vector3.Distance(house.position, city.transform.GetChild(j).position);
                    if (distance < minDistance)
                    {
                        minDistance = distance;
                        pair = j;
                    }
                }
            }
            if (!dontAssign.ContainsKey(pair)) dontAssign.Add(pair, new List<int>());
            dontAssign[pair].Add(i);
            edges.Add(i, pair);
        }
        return edges;
    }

    public void saveEdges(Dictionary<int,int> edges)
    {
        List<string> lines = new List<string>();
        lines.Add("Edges:");
        foreach( KeyValuePair<int,int> entry in edges )
        {
            lines.Add(string.Format("{0}\t{1}",entry.Key, entry.Value));
        }

        File.WriteAllLines(Application.dataPath + "/Simulator/edges.txt", lines.ToArray());
    }

    public void createWires( Dictionary<int,int> wires )
    {
        foreach( KeyValuePair<int,int> entry in wires )
        {
            GameObject wire = Instantiate(wirePrefab, Vector3.zero, Quaternion.identity) as GameObject;
            LineRenderer lineRenderer = wire.GetComponent<LineRenderer>();

            wire.transform.SetParent(city.transform);
            lineRenderer.SetPosition(0, city.transform.GetChild(entry.Key).position);
            lineRenderer.SetPosition(1, city.transform.GetChild(entry.Value).position);

            Spark spark = wire.GetComponentInChildren<Spark>();
            spark.start = city.transform.GetChild(entry.Key).position;
            spark.end = city.transform.GetChild(entry.Value).position;

            wireGOs.Add(wire);
        }
    }
}
