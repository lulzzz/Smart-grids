
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;
using UnityEngine.UI;

public class JsonParser
{

    private List<InfoPanel> panels;

    private string jsonPath;
    private GameObject city;
    private List<GameObject> wireGOs;

    public JsonParser( string jsonPath, GameObject city, List<GameObject> wireGOs )
    {
        panels = new List<InfoPanel>();
        this.jsonPath = jsonPath;
        this.city = city;
        this.wireGOs = wireGOs;
    }

    public void createPanels( GameObject infoPanelPrefab, GameObject appliancePrefab, GameObject generatorPrefab, ApplianceSpriteDictionary spriteOfAppliance, GeneratorSpriteDictionary spriteOfGenerator )
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

            GameObject infoPanelGO = MonoBehaviour.Instantiate(infoPanelPrefab, t.position + collider.size.y * Vector3.up, Quaternion.identity) as GameObject;
            InfoPanel infoPanel = infoPanelGO.GetComponent<InfoPanel>();
            infoPanel.transform.SetParent(t);

            // Add show hide infopanel effect
            ShowHideCanvas showHide = t.gameObject.AddComponent<ShowHideCanvas>();
            showHide.target = infoPanel.gameObject;

            // Create panel struct
            panels.Add(infoPanel);


            JSONObject appliances = house.GetField("appliances");
            foreach (JSONObject appliance in appliances.list)
            {
                string type = appliance.GetField("type").ToString().Replace("\"", "");
                createAppliance(infoPanel, (ApplianceType)Enum.Parse(typeof(ApplianceType), type.Trim()), appliancePrefab, spriteOfAppliance);
            }

            JSONObject generators = house.GetField("generators");
            foreach (JSONObject generator in generators.list)
            {
                string type = generator.GetField("type").ToString().Replace("\"", "");
                createGenerator(infoPanel, (GeneratorType)Enum.Parse(typeof(GeneratorType), type.Trim()), generatorPrefab, spriteOfGenerator);
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
            JSONObject houses = frame.GetField("houses");
            for (int i = 0; i < houses.list.Count; i++)
            {
                JSONObject house = houses.list[i];
                InfoPanel panel = panels[i];

                // Battery percent
                JSONObject battery = house.GetField("battery");
                float level = float.Parse(battery.GetField("level").ToString());
                float capacity = float.Parse(battery.GetField("capacity").ToString());

                FillImageAnimator animator = panel.battery.GetComponentsInChildren<FillImageAnimator>(true)[0];
                animator.addFrame(level / capacity);

                // Appliances progress
                JSONObject appliances = house.GetField("appliances");
                for (int app = 0; app < appliances.list.Count; app++ )
                {
                    JSONObject appliance = appliances.list[app];
                    float progress = float.Parse(appliance.GetField("progress").ToString());
                    Transform t = panel.applianceParent.transform.GetChild(app);
                    t.GetComponentsInChildren<FillImageAnimator>(true)[0].addFrame(progress);
                }

                // Generators efficiencies
                JSONObject generators = house.GetField("generators");
                for(int gen = 0; gen < generators.list.Count; gen++)
                {
                    JSONObject generator = generators.list[gen];

                    float efficiency = float.Parse(generator.GetField("efficiency").ToString());
                    Transform t = panel.generatorParent.transform.GetChild(gen);
                    t.GetComponentsInChildren<FillImageAnimator>(true)[0].addFrame(efficiency);
                }

                // Bid plots
                string plotFile = house.GetField("bid").GetField("plotFile").ToString().Replace("\"","");
                panel.bid.GetComponent<ChangeImageAnimator>().addPlot(plotFile);
            }

            JSONObject wires = frame.GetField("wires");
            foreach (JSONObject wire in wires.list)
            {

                int origin = int.Parse(wire.GetField("originId").ToString());
                int destination = int.Parse(wire.GetField("destinationId").ToString());

                float flow = float.Parse(wire.GetField("flow").ToString());

                foreach (GameObject w in wireGOs)
                {
                    WireIdentity identity = w.GetComponent<WireIdentity>();

                    if (identity.from.transform.position == city.transform.GetChild(origin).position && 
                        identity.to.transform.position == city.transform.GetChild(destination).position)
                    {
                        identity.spark.GetComponent<TranslationAnimator>().addFlow(flow);
                    }
                }
            }
        }
    }

    private void createAppliance( InfoPanel infoPanel, ApplianceType type, GameObject appliancePrefab, ApplianceSpriteDictionary spriteOfAppliance)
    {
        GameObject applianceGO = MonoBehaviour.Instantiate(appliancePrefab) as GameObject;
        applianceGO.transform.GetChild(0).GetComponent<Image>().sprite = spriteOfAppliance[type];
        applianceGO.transform.SetParent(infoPanel.applianceParent.transform);

        applianceGO.GetComponent<RectTransform>().localScale = Vector3.one;
        applianceGO.GetComponent<RectTransform>().offsetMin = Vector2.zero;
        applianceGO.GetComponent<RectTransform>().offsetMax = Vector2.zero;
        applianceGO.transform.localPosition = new Vector3(applianceGO.transform.localPosition.x, applianceGO.transform.localPosition.y, 0);
    }

    private void createGenerator( InfoPanel infoPanel, GeneratorType type, GameObject generatorPrefab, GeneratorSpriteDictionary spriteOfGenerator)
    {
        GameObject applianceGO = MonoBehaviour.Instantiate(generatorPrefab) as GameObject;
        applianceGO.transform.GetChild(0).GetComponent<Image>().sprite = spriteOfGenerator[type];
        applianceGO.transform.SetParent(infoPanel.generatorParent.transform);

        applianceGO.GetComponent<RectTransform>().localScale = Vector3.one;
        applianceGO.GetComponent<RectTransform>().offsetMin = Vector2.zero;
        applianceGO.GetComponent<RectTransform>().offsetMax = Vector2.zero;
        applianceGO.transform.localPosition = new Vector3(applianceGO.transform.localPosition.x, applianceGO.transform.localPosition.y, 0);
    }
    
}





























/*
            JSONObject moment = frame.GetField("moment");
            string sMoment = moment.GetField("hour").ToString() + ":" + moment.GetField("minute").ToString();

            JSONObject weather = frame.GetField("weather");
            float cloudPercentage = float.Parse(weather.GetField("cloudPercentage").ToString());
            float windSpeed  = float.Parse(weather.GetField("windSpeed").ToString());
            */

/*
float totalTraded = float.Parse(house.GetField("totalTraded").ToString());
float totalGenerated = float.Parse(house.GetField("totalGenerated").ToString());
float totalApplied = float.Parse(house.GetField("totalApplied").ToString());
float baseConsum = float.Parse(house.GetField("baseConsum").ToString());
float balance = float.Parse(house.GetField("balance").ToString());
*/
//float productionPerHour = float.Parse(generator.GetField("productionPerHour").ToString());

