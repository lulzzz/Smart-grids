using UnityEngine;
using System.IO;
using System;
using System.Collections.Generic;
using System.Linq;

public class Manager : MonoBehaviour
{
    public GameObject city;
    [SerializeField]
    private GameObject wirePrefab;
    [SerializeField]
    private GameObject infoPanelPrefab;
    [SerializeField]
    private bool repeat = false;

    private List<GameObject> wireGOs = new List<GameObject>();

    public Simulator.SimulationInput simulationInput;

    public static Manager Instance { get; private set; }

    private Simulator simulation;

    void Start ()
    {
        Application.runInBackground = true;

        simulationInput.folder = Application.dataPath + "/Simulator";
        simulationInput.startingHour = 6;
        simulationInput.startingMinute = 0;
        simulationInput.endingHour = 18;
        simulationInput.endingMinute = 0;
        simulationInput.timeStep = 5;

        Instance = this;
    }

    public void setCity(GameObject city)
    {
        this.city = city;
        GetComponent<MeshRenderer>().enabled = true;
        GetComponent<BoxCollider>().enabled = true;
    }

    public void startSimulation()
    {
        // Build tree
        TreeBuilder treeBuilder = new TreeBuilder(city);
        treeBuilder.buildNearestTree(Application.dataPath + "/Simulator/edges.txt");
        
        simulation = new Simulator();
        simulation.input = simulationInput;
        simulation.Start();
        
        //createWires(wires);
        
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            #if UNITY_EDITOR
                        UnityEditor.EditorApplication.isPlaying = false;
            #elif UNITY_WEBPLAYER
                     Application.OpenURL(webplayerQuitURL);
            #else
                     Application.Quit();
            #endif
        }

        if( simulation != null && simulation.Update() )
        {
            simulation = null;
        }
        
    }

    public void simulationReady( string jsonPath )
    {
        JsonParser parser = new JsonParser(jsonPath, city, wireGOs);
        parser.createPanels(infoPanelPrefab);
        parser.parseJSON();
    }

    public bool repeatAnimation()
    {
        return repeat;
    }

    

    public void createWires(Dictionary<int, int> wires)
    {
        foreach (KeyValuePair<int, int> entry in wires)
        {
            GameObject wire = MonoBehaviour.Instantiate(wirePrefab, Vector3.zero, Quaternion.identity) as GameObject;
            LineRenderer lineRenderer = wire.GetComponent<LineRenderer>();

            wire.transform.SetParent(city.transform);
            lineRenderer.SetPosition(0, city.transform.GetChild(entry.Key).position);
            lineRenderer.SetPosition(1, city.transform.GetChild(entry.Value).position);

            TranslationAnimator spark = wire.GetComponentInChildren<TranslationAnimator>();
            spark.from = city.transform.GetChild(entry.Key).position;
            spark.to = city.transform.GetChild(entry.Value).position;

            wireGOs.Add(wire);
        }
    }
}
