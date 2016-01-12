using UnityEngine;
using System.Collections.Generic;

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

        //create Wire go's
        wireGOs = treeBuilder.createWires(wirePrefab);
        
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
}
