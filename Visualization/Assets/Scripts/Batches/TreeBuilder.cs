using UnityEngine;
using System.Collections.Generic;
using System.IO;

public class TreeBuilder
{
    private GameObject city;

    public TreeBuilder(GameObject city)
    {
        this.city = city;
    }

    public void buildNearestTree(string filePath)
    {
        Dictionary<int, int> wires = nearestTree();
        saveEdges(wires, filePath);
    }

    public Dictionary<int, int> nearestTree()
    {
        Dictionary<int, List<int>> dontAssign = new Dictionary<int, List<int>>();
        Dictionary<int, int> edges = new Dictionary<int, int>();
        for (int i = 0; i < city.transform.childCount - 1; i++)
        {
            int pair = (i + 1) % city.transform.childCount;
            Transform house = city.transform.GetChild(i);

            float minDistance = Vector3.Distance(house.position, city.transform.GetChild(pair).position);

            for (int j = 0; j < city.transform.childCount; j++)
            {
                if (dontAssign.ContainsKey(j) && !dontAssign[j].Contains(i) && i != j)
                {
                    float distance = Vector3.Distance(house.position, city.transform.GetChild(j).position);
                    if (distance < minDistance)
                    {
                        minDistance = distance;
                        pair = j;
                    }
                }
            }
            if (!dontAssign.ContainsKey(pair)) dontAssign.Add(pair, new List<int>());
            dontAssign[pair].Add(i);
            edges.Add(i, pair);
        }
        return edges;
    }

    public void saveEdges(Dictionary<int, int> edges, string filePath)
    {
        List<string> lines = new List<string>();
        lines.Add("Edges:");
        foreach (KeyValuePair<int, int> entry in edges)
        {
            lines.Add(string.Format("{0}\t{1}", entry.Key, entry.Value));
        }

        File.WriteAllLines(filePath, lines.ToArray());
    }
}
