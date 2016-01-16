using UnityEngine;
using System.Collections.Generic;
using System.IO;
using System;

public class Manager : MonoBehaviour
{
    [SerializeField] public GameObject city;
    
    [SerializeField] private GameObject wirePrefab;
    [SerializeField] private GameObject sparkPrefab;
    [SerializeField] private GameObject infoPanelPrefab;
    [SerializeField] private ApplianceSpriteDictionary spriteOfAppliance;
    [SerializeField] private GeneratorSpriteDictionary spriteOfGenerator;

    [SerializeField] private GameObject appliancePrefab;
    [SerializeField] private GameObject generatorPrefab;
    [SerializeField] public bool repeat;
    [SerializeField] private List<FadeMaterial> fadeAfterMenu;
    [SerializeField] private List<FadeMaterial> fadeAfterSimulation;

    [SerializeField] private MoveOverTorus startingHourBall;
    [SerializeField] private MoveOverTorus endingHourBall;

    private List<GameObject> wireGOs = new List<GameObject>();

    private Simulator simulation;

    public static Manager Instance { get; private set; }

    void Start ()
    {
        Application.runInBackground = true;

        Instance = this;

        // Build tree
        TreeBuilder treeBuilder = new TreeBuilder(city);
        treeBuilder.buildNearestTree(Application.dataPath + "/Simulator/edges.txt");
        //create Wire go's
        wireGOs = treeBuilder.createWires(wirePrefab, sparkPrefab);
    }

    public void setCity(GameObject city)
    {
        this.city = city;
        // Build tree
        TreeBuilder treeBuilder = new TreeBuilder(city);
        treeBuilder.buildNearestTree(Application.dataPath + "/Simulator/edges.txt");
        //create Wire go's
        wireGOs = treeBuilder.createWires(wirePrefab, sparkPrefab);
    }

    public void startSimulation()
    {
        foreach (FadeMaterial toFade in fadeAfterMenu)
            toFade.toggleFade();

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
        json.AddField("cityModel", "path");
        json.AddField("outputFolder", "path");
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
        foreach (FadeMaterial toFade in fadeAfterSimulation)
            toFade.toggleFade();

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
}
