using UnityEngine;
using System.IO;
using System;

public class Manager : MonoBehaviour
{
    [SerializeField]
    public string jsonPath;
    [SerializeField]
    public GameObject cityPrefab;
    public GameObject city;
    [SerializeField]
    private GameObject appliancePrefab;
    [SerializeField]
    private bool repeat = false;

    public static Manager Instance { get; private set; }

    void Start ()
    {
        Application.runInBackground = true;
        jsonPath = null;
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
        
    }

    public void simulationReady()
    {
        print("woohoo");
    }

    void OnLevelWasLoaded()
    {
        if (Application.loadedLevel == 1)
        {
            createPanels();
            parseJSON();
            foreach (Transform child in city.transform)
            {
                child.GetComponent<House>().ready();
            }
        }
    }

    public void setJSON( string path )
    {
        jsonPath = path;
    }

    public bool repeatAnimation() { return repeat; }

    private void createPanels()
    {
        string sjson = File.ReadAllText(jsonPath);
        JSONObject jsonObject = new JSONObject(sjson);

        JSONObject frame = jsonObject.GetField("frames").list[0];
        
        JSONObject houses = frame.GetField("houses");
        foreach (JSONObject house in houses.list)
        {
            int id = int.Parse(house.GetField("id").ToString());
            House h = city.transform.GetChild(id).GetComponent<House>();

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

    public void parseJSON()
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

                House h = city.transform.GetChild(id).GetComponent<House>();
                
                
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
            /*
            JSONObject wires = frame.GetField("wires");
            foreach (JSONObject wire in wires.list)
            {

                int origin = int.Parse(wire.GetField("originId").ToString());
                int destination = int.Parse(wire.GetField("destinationId").ToString());
                float flow = float.Parse(wire.GetField("flow").ToString());
            }*/
        }
    }
}
