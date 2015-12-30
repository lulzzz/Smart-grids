using System;
using UnityEngine;
using UnityEngine.UI;
using System.Collections.Generic;

public class Manager : MonoBehaviour
{
    public DaynightCycle dayNightCycle;
    public GameObject flare;
    public List<GameObject> houses;
    private string json;
    public Dictionary<int, Dictionary<string, double>> links;
    public Dictionary<int, Dictionary<int, List<string>>> offerTable;
    public static Manager Instance;

    public bool readJSON;
    public string jsonPath;

    void Awake()
    {
        if (Instance == null) Instance = this;
        json = System.IO.File.ReadAllText(@""+jsonPath);
        print(json);
    }
    
    public void setHour(float value)
    {
        stopSimulation();
        dayNightCycle.setHour(value);
        drawLinks(value);
        //drawOffers((int)value);
    }

    public void startSimulation()
    {
        dayNightCycle.simulating = !dayNightCycle.simulating;
    }

    public void stopSimulation()
    {
        dayNightCycle.simulating = false;
    }

    private void nextHour()
    {
        CancelInvoke("nextHour");
    }

    public void drawLinks(float hour)
    {
        if (!readJSON)
        {
            int h = (int)hour;
            if (h != 0)
            {
                foreach (GameObject go in GameObject.FindGameObjectsWithTag("flare")) Destroy(go);

                GameObject flareGO = Instantiate(flare, houses[h % 3].transform.position, Quaternion.identity) as GameObject;
                flareGO.GetComponent<MoveToTarget>().origin = houses[h % 3].transform;
                flareGO.GetComponent<MoveToTarget>().target = houses[(h + 1) % 3].transform;
                flareGO.tag = "flare";

                flareGO = Instantiate(flare, houses[(h + 1) % 3].transform.position, Quaternion.identity) as GameObject;
                flareGO.GetComponent<MoveToTarget>().origin = houses[(h + 1) % 3].transform;
                flareGO.GetComponent<MoveToTarget>().target = houses[(h + 2) % 3].transform;
                flareGO.tag = "flare";
            }
            else
            {
                foreach (GameObject go in GameObject.FindGameObjectsWithTag("flare")) Destroy(go);

                GameObject flareGO = Instantiate(flare, houses[1].transform.position, Quaternion.identity) as GameObject;
                flareGO.GetComponent<MoveToTarget>().origin = houses[1].transform;
                flareGO.GetComponent<MoveToTarget>().target = houses[2].transform;
                flareGO.tag = "flare";

                flareGO = Instantiate(flare, houses[2].transform.position, Quaternion.identity) as GameObject;
                flareGO.GetComponent<MoveToTarget>().origin = houses[2].transform;
                flareGO.GetComponent<MoveToTarget>().target = houses[0].transform;
                flareGO.tag = "flare";
            }
        }
        else
        {
            Dictionary<string, double> l = links[(int)hour];

            foreach (GameObject go in GameObject.FindGameObjectsWithTag("flare")) Destroy(go);

            foreach (KeyValuePair<string, double> kv in l)
            {
                int origin = int.Parse(kv.Key.ToCharArray()[2].ToString());
                int target = int.Parse(kv.Key.ToCharArray()[4].ToString());

                if (kv.Value == 0) continue;

                GameObject flareGO = Instantiate(flare, houses[origin].transform.position, Quaternion.identity) as GameObject;
                flareGO.GetComponent<MoveToTarget>().origin = kv.Value > 0 ? houses[origin].transform : houses[target].transform;
                flareGO.GetComponent<MoveToTarget>().target = kv.Value > 0 ? houses[target].transform : houses[origin].transform;
                flareGO.tag = "flare";

                TextMesh textMesh = flareGO.GetComponentInChildren<TextMesh>();
                if (textMesh != null)
                    textMesh.text = Mathf.Abs((float)kv.Value) + " kw";
            }
        }
    }
    
    

    
    
}
