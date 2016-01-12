
using System;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

public class JsonParser
{
    private string jsonPath;
    private GameObject city;
    private List<GameObject> wireGOs;
    // make static
     
    public JsonParser( string jsonPath, GameObject city, List<GameObject> wires )
    {
        this.jsonPath = jsonPath;
        this.city = city;
        this.wireGOs = wires;
    }

    public void createPanels( GameObject infoPanelPrefab )
    {
        string sjson = File.ReadAllText(jsonPath);
        JSONObject jsonObject = new JSONObject(sjson);

        JSONObject frame = jsonObject.GetField("frames").list[0];

        JSONObject houses = frame.GetField("houses");
        foreach (JSONObject house in houses.list)
        {
            int id = int.Parse(house.GetField("id").ToString());
            Transform t = city.transform.GetChild(id);

            // Add collider and info panel
            BoxCollider collider = t.gameObject.AddComponent<BoxCollider>();
            GameObject infoPanel = MonoBehaviour.Instantiate(infoPanelPrefab, t.position + collider.size.y * Vector3.up, Quaternion.identity) as GameObject;
            infoPanel.transform.SetParent(t);
            InfoPanel h = infoPanel.GetComponent<InfoPanel>();
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

                InfoPanel h = city.transform.GetChild(id).GetComponentInChildren<InfoPanel>();


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
                    TranslationAnimator spark = w.GetComponentInChildren<TranslationAnimator>();
                    if (spark.from == city.transform.GetChild(origin).position && spark.to == city.transform.GetChild(destination).position)
                        spark.addFlow(flow);
                }
            }
        }
    }
}
