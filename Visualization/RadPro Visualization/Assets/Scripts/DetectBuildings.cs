using UnityEngine;
using System.Collections.Generic;
using System.IO;
using System;

public class DetectBuildings : MonoBehaviour
{
    public Transform city;
    public string outputPath;
    
    public GameObject linkPrefab;
    public Transform linksParent;
    public string steinerPath;

    public static int dimensions = 2;

    
	// Use this for initialization
	void Start ()
    {
        detectAllBuildings();
        createLinksFromSteinerGraph(steinerPath);
	}

    private void detectAllBuildings()
    {
        int numHouses = city.childCount;

        List<string> positions = new List<string>();

        positions.Add(string.Format("{0} {1}", numHouses, dimensions));

        foreach (Transform house in city)
        {
            positions.Add(string.Format("{0} {1}", house.position.x, house.position.z));
        }

        File.WriteAllLines(outputPath, positions.ToArray());
    }

    private void createLinksFromSteinerGraph( string filePath )
    {
        string[] lines = File.ReadAllLines(filePath);

        for( int i = 0; i < lines.Length; i++ )
        {
            if(lines[i].StartsWith("Steiner points"))
            {
                createSteinerPoints(lines, i);
            }
            if(lines[i].StartsWith("Edges"))
            {
                createLinks(lines, i);
            }
        }
    }

    private void createSteinerPoints(string[] lines, int i)
    {
        int j = i + 1;
        while( lines[j] != "" )
        {
            GameObject go = new GameObject();
            string[] pos = lines[j].Split();
            go.transform.position = new Vector3( float.Parse(pos[1]), 1, float.Parse(pos[2]));
            go.transform.SetParent(city);
            j++;
        }
    }

    private void createLinks(string[] lines, int i)
    {
        int j = i + 1;
        while( lines[j] != "" )
        {
            GameObject link = Instantiate(linkPrefab) as GameObject;
            LineRenderer lineRenderer = link.GetComponent<LineRenderer>();

            string[] linkNodes = lines[j].Split();
            
            lineRenderer.SetWidth(.1f, .1f);
            lineRenderer.SetPosition(0, city.GetChild(Int32.Parse(linkNodes[0])-1).position);
            lineRenderer.SetPosition(1, city.GetChild(Int32.Parse(linkNodes[1])-1).position);
            j++;

            link.transform.SetParent(linksParent);
        }
    }
}
