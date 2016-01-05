using UnityEngine;
using System.Collections;
using System.IO;
using System.Collections.Generic;

public class Manager : MonoBehaviour
{
    [SerializeField]
    private string jsonPath;

    private GameObject city;
    private List<House> hs;

	void Start ()
    {
        hs = new List<House>();
        parseJSON();
        print(hs.Count);
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

                House h = new House(id);
                if (!hs.Contains(h))
                {
                    hs.Add(h);
                }
                else
                {
                    h = hs[id];
                }
                
                JSONObject battery = house.GetField("battery");
                float level = float.Parse(battery.GetField("level").ToString());
                float capacity = float.Parse(battery.GetField("capacity").ToString());

                h.battery.addPercent(level / capacity);

                JSONObject appliances = house.GetField("appliances");
                foreach (JSONObject appliance in appliances.list)
                {
                    string type = appliance.GetField("type").ToString();
                    string state = appliance.GetField("state").ToString();
                    float progress = float.Parse(appliance.GetField("progress").ToString());
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
