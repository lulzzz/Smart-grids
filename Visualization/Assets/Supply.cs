using UnityEngine;
using System.Collections.Generic;
using System.IO;

public class Supply : MonoBehaviour
{
    public GameObject supply;
    public List<GameObject> wires = new List<GameObject>();

    void Start ()
    {
        List<float> list = new List<float>() { 1, 2, 3 };
        JSONObject array = new JSONObject(JSONObject.Type.ARRAY);
        foreach(float elem in list) array.Add(elem);
        JSONObject json = new JSONObject();
        json.AddField("cityModel", "path");
        json.AddField("outputFolder", "path");
        json.AddField("hour", 10);
        json.AddField("minute", 0);
        json.AddField("frames", 12);
        json.AddField("timeStep", 5);
        json.AddField("houses", array);
        json.AddField("wires", array);
        print("json" + json.ToString());
        File.WriteAllText(Application.dataPath + "/Simulator/input.json", json.ToString());
    }
    void Update ()
    {
        float max = 0;
        foreach( GameObject wire in wires )
        {
            if (wire.transform.localScale.x > max)
                max = wire.transform.localScale.x;
        }

        supply.transform.localScale = max * 2.2f * Vector3.one;
    }
}
