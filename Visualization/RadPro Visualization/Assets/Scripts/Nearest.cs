using UnityEngine;
using System.Collections.Generic;
using System.IO;

public class Nearest : MonoBehaviour
{
    public void Start()
    {
        List<Vector3> positions = new List<Vector3>();
        foreach (Transform child in gameObject.transform)
        {
            positions.Add(child.position);
        }
        File.WriteAllLines("output/edges.txt", makeWires(positions));
    }

    public string[] makeWires( List<Vector3> positions )
    {
        List<string> res = new List<string>();
        res.Add("Edges:");
        List<int> done = new List<int>();
        done.Add(0);

        for (int i = 0; i < positions.Count -1; i++)
        {
            // Get vertex at minimal distance
            double shortest = Vector3.Distance(positions[i], positions[(i+1) % positions.Count]);
            int index = i+1 % positions.Count;

            for (int other = 0; other < positions.Count; other++)
            {
                if (other == i) continue;
                double distance = Vector3.Distance(positions[i], positions[other]);
                if (distance < shortest)
                {
                    shortest = distance;
                    index = other;
                }
            }

            // Add wire from i to other
            res.Add(string.Format("{0}\t{1}\n", i, index));
        }
        return res.ToArray(); 
    }
}
