using UnityEngine;
using UnityEditor;
using System.Collections.Generic;
using System.IO;

public class DetectBuildings : MonoBehaviour
{
    public Transform city;
    public string outputPath;

    public static int dimensions = 2;

    
	// Use this for initialization
	void Start ()
    {
        city = Selection.activeGameObject.transform;
        int numHouses = city.childCount;

        List<string> positions = new List<string>();

        print(numHouses);

        positions.Add(string.Format("{0} {1}", numHouses, dimensions));

        foreach( Transform house in city )
        {
            positions.Add(string.Format("{0} {1}", house.position.x, house.position.z));
        }

        File.WriteAllLines(outputPath, positions.ToArray() );

        int i = 0;

        Dictionary<int, int> links = new Dictionary<int, int>()
    {
        { 1,   7 },
        { 2,   8 },
        { 3,   6 },
        { 4,   7 },
        { 7,   6},
        { 5,   8},
        { 6,   8}};

        foreach (KeyValuePair<int, int> kv in links)
        {
            LineRenderer l = city.GetChild(i).gameObject.AddComponent<LineRenderer>();
            if (l == null) continue;
            l.SetWidth(.1f, .1f);
            l.SetPosition(0, city.GetChild(kv.Key-1).position);
            l.SetPosition(1, city.GetChild(kv.Value-1).position);
            i++;
        }
	}
}
