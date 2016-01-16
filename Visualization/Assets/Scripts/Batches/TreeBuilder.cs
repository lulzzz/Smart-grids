using UnityEngine;
using System.Collections.Generic;
using System.IO;

public class TreeBuilder
{
    private GameObject city;
    private Dictionary<int, int> wires;

    public TreeBuilder(GameObject city)
    {
        this.city = city;
    }

    public void buildNearestTree()
    {
        wires = nearestTree();
    }

    public Dictionary<int, int> nearestTree()
    {
        Dictionary<int, int> edges = new Dictionary<int, int>();
        for (int i = 1; i < city.transform.childCount; i++)
        {
            int pair = (i - 1);
            Transform house = city.transform.GetChild(i);

            float minDistance = Vector3.Distance(house.position, city.transform.GetChild(pair).position);

            for (int j = 0; j < i; j++)
            {
                float distance = Vector3.Distance(house.position, city.transform.GetChild(j).position);
                if (distance < minDistance)
                {
                    minDistance = distance;
                    pair = j;
                }
                
            }
            edges.Add(i, pair);
        }
        return edges;
    }

    public List<GameObject> createWires(GameObject wirePrefab, GameObject sparkPrefab)
    {
        List<GameObject> wireGOs = new List<GameObject>();
        foreach (KeyValuePair<int, int> entry in wires)
        {
            GameObject wire = MonoBehaviour.Instantiate(wirePrefab, Vector3.zero, wirePrefab.transform.rotation) as GameObject;

            wire.transform.SetParent(city.transform);

            WireIdentity wireIdentity = wire.GetComponent<WireIdentity>();
            wireIdentity.from = city.transform.GetChild(entry.Key).gameObject;
            wireIdentity.to = city.transform.GetChild(entry.Value).gameObject;
            
            GameObject spark = MonoBehaviour.Instantiate(sparkPrefab, Vector3.zero, sparkPrefab.transform.rotation ) as GameObject;
            spark.transform.SetParent(city.transform);
            TranslationAnimator animator = spark.GetComponent<TranslationAnimator>();
            animator.from = city.transform.GetChild(entry.Key).position;
            animator.to = city.transform.GetChild(entry.Value).position;
            animator.addFlow(1);
            animator.animate();

            wireIdentity.spark = spark;
            
            wireGOs.Add(wire);
        }

        return wireGOs;
    }
}
