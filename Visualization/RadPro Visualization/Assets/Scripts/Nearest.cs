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
        File.WriteAllLines("tree.txt", makeWires(positions));
    }

    public string[] makeWires( List<Vector3> positions )
    {
        List<string> res = new List<string>();
        res.Add("Edges:");
        List<int> done = new List<int>();
        done.Add(0);

        for (int i = 1; i < positions.Count; i++)
        {
            // Get vertex at minimal distance
            double shortest = Vector3.Distance(positions[i], positions[i-1]);
            int index = 0;

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
