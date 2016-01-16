using UnityEngine;
using System.Collections.Generic;
using System.IO;
using System;

public enum ManagerState
{
    SelectingMode, BuildingCity, Visualizating
}

public class Manager : MonoBehaviour
{
    public GameObject exampleCity;
    [SerializeField] private ManagerState state;

    // Selecting mode objects
    [SerializeField] private GameObject title;
    [SerializeField] private GameObject optionPanels;
    
    [SerializeField] public GameObject city;
    [SerializeField] private string cityPath;

    public void cityImported(string cityPath, GameObject city)
    {
        Destroy(title);
        Destroy(optionPanels);
        this.cityPath = cityPath;
        this.city = city;

        TreeBuilder treeBuilder = new TreeBuilder(city);
        treeBuilder.buildNearestTree();
        wireGOs = treeBuilder.createWires(wirePrefab, sparkPrefab);

        state = ManagerState.BuildingCity;
        myTorus = Instantiate(torusPrefab, Vector3.zero, Quaternion.identity) as GameObject;
        startingHourBall = myTorus.transform.GetChild(0).GetComponent<MoveOverTorus>();
        endingHourBall = myTorus.transform.GetChild(1).GetComponent<MoveOverTorus>();
    }
    
    // Build city objects
    [SerializeField] private GameObject torusPrefab;
    [SerializeField] private GameObject myTorus;
    [SerializeField] private MoveOverTorus startingHourBall;
    [SerializeField] private MoveOverTorus endingHourBall;
    [SerializeField] private GameObject wirePrefab;
    [SerializeField] private GameObject sparkPrefab;

    // Visualization objects
    [SerializeField] private GameObject infoPanelPrefab;
    [SerializeField] private ApplianceSpriteDictionary spriteOfAppliance;
    [SerializeField] private GeneratorSpriteDictionary spriteOfGenerator;
    [SerializeField] private GameObject appliancePrefab;
    [SerializeField] private GameObject generatorPrefab;
    [SerializeField] private GameObject timeUIPrefab;

    [SerializeField] public bool repeat;

    private List<GameObject> wireGOs = new List<GameObject>();

    private Simulator simulation;

    public static Manager Instance { get; private set; }

    void Start ()
    {
        state = ManagerState.SelectingMode;

        Application.runInBackground = true;

        Instance = this;
    }

    public void startSimulation()
    {

        createSimulationInputJson(Application.dataPath + "/Simulator/input.json");

        foreach(TranslationAnimator a in FindObjectsOfType<TranslationAnimator>())
        {
            a.reset();
        }
        
        simulation = new Simulator();
        simulation.simulator = Application.dataPath + "/Simulator/simulator.jar";
        simulation.input = Application.dataPath+"/Simulator/input.json";
        simulation.Start();
        
    }

    private void createSimulationInputJson(string filePath)
    {
        int timeStep = 5;
        int startingHour = startingHourBall.hour;
        int startingMinute = startingHourBall.minute;
        int endingHour = endingHourBall.hour;
        int endingMinute = endingHourBall.minute;

        int m = (endingHour - startingHour) * 60 + endingMinute - startingMinute;
        int frames = m / timeStep;

        JSONObject houseArray = new JSONObject(JSONObject.Type.ARRAY);
        JSONObject wireArray = new JSONObject(JSONObject.Type.ARRAY);

        foreach (HouseIdentity house in FindObjectsOfType<HouseIdentity>())
            houseArray.Add(house.toJson());

        foreach (WireIdentity wire in FindObjectsOfType<WireIdentity>())
            wireArray.Add(wire.toJson());

        JSONObject json = new JSONObject();
        json.AddField("cityModel", cityPath);
        json.AddField("outputFolder", Application.dataPath + "/Simulator");
        json.AddField("hour", startingHour);
        json.AddField("minute", startingMinute);
        json.AddField("frames", frames);
        json.AddField("timeStep", 5);
        json.AddField("houses", houseArray);
        json.AddField("wires", wireArray);
        File.WriteAllText(filePath, json.ToString());
    }

    void Update()
    {
        if ( simulation != null && simulation.Update() )
        
            simulation = null;
    }

    public void simulationReady()
    {

        Destroy(myTorus);
        Instantiate(timeUIPrefab);

        state = ManagerState.Visualizating;

        string jsonPath = Application.dataPath + "/Simulator/simulation.json";

        JsonParser parser = new JsonParser(jsonPath, city, wireGOs);
        parser.createPanels(infoPanelPrefab,appliancePrefab, generatorPrefab, spriteOfAppliance, spriteOfGenerator);
        parser.parseJSON();

        // Enable all animators
        foreach(FillImageAnimator fillAnimator in FindObjectsOfType<FillImageAnimator>())
        {
            fillAnimator.animate();
        }
        foreach (ChangeImageAnimator changeAnimator in FindObjectsOfType<ChangeImageAnimator>())
            changeAnimator.animate();
        foreach (TranslationAnimator translation in FindObjectsOfType<TranslationAnimator>())
            translation.animate();
    }

    public void loadJson(string path)
    {
        Instantiate(timeUIPrefab);

        Destroy(title);
        Destroy(optionPanels);
        state = ManagerState.Visualizating;

        string sjson = File.ReadAllText(path);
        JSONObject jsonObject = new JSONObject(sjson);

        string cityPath = "test";// jsonObject.GetField("cityModel").ToString();
        GameObject city = ObjImporter.Import(cityPath);
        TreeBuilder treeBuilder = new TreeBuilder(city);
        treeBuilder.buildNearestTree();
        wireGOs = treeBuilder.createWires(wirePrefab, sparkPrefab);

        JsonParser parser = new JsonParser(path, city, wireGOs);
        parser.createPanels(infoPanelPrefab, appliancePrefab, generatorPrefab, spriteOfAppliance, spriteOfGenerator);
        parser.parseJSON();

        // Enable all animators
        foreach (FillImageAnimator fillAnimator in FindObjectsOfType<FillImageAnimator>())
        {
            fillAnimator.animate();
        }
        foreach (ChangeImageAnimator changeAnimator in FindObjectsOfType<ChangeImageAnimator>())
            changeAnimator.animate();
        foreach (TranslationAnimator translation in FindObjectsOfType<TranslationAnimator>())
            translation.animate();
    }

    public ManagerState getState() { return state; }
}
