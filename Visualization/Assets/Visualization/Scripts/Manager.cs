using UnityEngine;
using System.IO;
using UnityEngine.UI;

public class Manager : MonoBehaviour
{
    [SerializeField]
    private string jsonPath;
    [SerializeField]
    private GameObject city;
    [SerializeField]
    private GameObject appliancePrefab;
    [SerializeField]
    private bool repeat = false;

    public Sprite sprite;

    public static Manager Instance { get; private set; }

    void Start ()
    {
        if (Instance == null) Instance = this;
        Application.targetFrameRate = 30;
        createPanels();
        parseJSON();
        city.transform.GetChild(0).GetComponentsInChildren<Appliance>()[0].ready();
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
            print(h);
            JSONObject appliances = house.GetField("appliances");
            int i = 0;
            foreach (JSONObject appliance in appliances.list)
            {
                string type = appliance.GetField("type").ToString();
                GameObject applianceGO = Instantiate(appliancePrefab) as GameObject;
                applianceGO.GetComponent<Image>().sprite = sprite;
                applianceGO.transform.SetParent(h.applianceParent.transform);
                applianceGO.GetComponent<RectTransform>().anchorMin = new Vector2(0f, .1f);
                applianceGO.GetComponent<RectTransform>().anchorMax = new Vector2(1f, .3f);
                applianceGO.GetComponent<RectTransform>().localScale = Vector3.one;

                applianceGO.GetComponent<RectTransform>().offsetMin = Vector2.zero;
                applianceGO.GetComponent<RectTransform>().offsetMax = Vector2.zero;
                i++;
            }

            JSONObject generators = house.GetField("generators");
            foreach (JSONObject generator in generators.list)
            {
                string type = generator.GetField("type").ToString();
                GameObject applianceGO = Instantiate(appliancePrefab) as GameObject;
                applianceGO.GetComponent<Image>().sprite = sprite;
                applianceGO.transform.SetParent(h.applianceParent.transform);
                applianceGO.GetComponent<RectTransform>().anchorMin = new Vector2(0f, .3f);
                applianceGO.GetComponent<RectTransform>().anchorMax = new Vector2(1f, .5f);
                applianceGO.GetComponent<RectTransform>().localScale = Vector3.one;
                applianceGO.GetComponent<RectTransform>().offsetMin = Vector2.zero;
                applianceGO.GetComponent<RectTransform>().offsetMax = Vector2.zero;
                i++;
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
            JSONObject moment = frame.GetField("moment");
            string sMoment = moment.GetField("hour").ToString() + ":" + moment.GetField("minute").ToString();

            JSONObject weather = frame.GetField("weather");
            float cloudPercentage = float.Parse(weather.GetField("cloudPercentage").ToString());
            float windSpeed  = float.Parse(weather.GetField("windSpeed").ToString());

            JSONObject houses = frame.GetField("houses");
            foreach (JSONObject house in houses.list)
            {
                int id = int.Parse(house.GetField("id").ToString());

                House h = city.transform.GetChild(id).GetComponent<House>();
                
                
                JSONObject battery = house.GetField("battery");
                float level = float.Parse(battery.GetField("level").ToString());
                float capacity = float.Parse(battery.GetField("capacity").ToString());

                JSONObject appliances = house.GetField("appliances");
                foreach (JSONObject appliance in appliances.list)
                {
                    string type = appliance.GetField("type").ToString();
                    string state = appliance.GetField("state").ToString();
                    float progress = float.Parse(appliance.GetField("progress").ToString());
                    h.GetComponentsInChildren<Appliance>()[0].addProgress(progress);
                    break;
                }

                JSONObject generators = house.GetField("generators");
                foreach (JSONObject generator in generators.list)
                {
                    string type = generator.GetField("type").ToString();
                    float productionPerHour = float.Parse(generator.GetField("productionPerHour").ToString());
                    float efficiency = float.Parse(generator.GetField("efficiency").ToString());
                }

                string plotFile = house.GetField("bid").GetField("plotFile").ToString();

                float totalTraded = float.Parse(house.GetField("totalTraded").ToString());
                float totalGenerated = float.Parse(house.GetField("totalGenerated").ToString());
                float totalApplied = float.Parse(house.GetField("totalApplied").ToString());
                float baseConsum = float.Parse(house.GetField("baseConsum").ToString());
                float balance = float.Parse(house.GetField("balance").ToString());
                float toPay = float.Parse(house.GetField("toPay").ToString());
            }

            JSONObject wires = frame.GetField("wires");
            foreach (JSONObject wire in wires.list)
            {
                int origin = int.Parse(wire.GetField("originId").ToString());
                int destination = int.Parse(wire.GetField("destinationId").ToString());
                float flow = float.Parse(wire.GetField("flow").ToString());
            }
        }
    }
}
