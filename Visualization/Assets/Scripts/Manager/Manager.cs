﻿using UnityEngine;
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
    [SerializeField] private GameObject menuObjects;
    
    [SerializeField] public GameObject city;
    [SerializeField] private string cityPath;

    public void cityImported(float size)
    {
        Destroy(menuObjects, 2);
        
        state = ManagerState.BuildingCity;
        myTorus = Instantiate(torusPrefab, Vector3.zero, Quaternion.identity) as GameObject;
        myTorus.transform.localScale *= size * 1.2f;

        foreach (FadeMaterial fader in FindObjectsOfType<FadeMaterial>())
        {
            fader.toggleFade();
        }
    }
    
    // Build city objects
    [SerializeField] private Material material;
    [SerializeField] private Material transparent;
    [SerializeField] private GameObject torusPrefab;
    [SerializeField] private GameObject myTorus;

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
        myTorus.GetComponent<FadeMaterial>().toggleFade();
        foreach(Transform child in myTorus.transform)
        {
            child.GetComponent<FadeMaterial>().toggleFade();
        }

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
        MoveOverTorus startingHourBall = myTorus.transform.GetChild(0).GetComponent<MoveOverTorus>();
        MoveOverTorus endingHourBall = myTorus.transform.GetChild(1).GetComponent<MoveOverTorus>();

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

    public void simulationReady(string jsonPath)
    {
        if (jsonPath == null)
        {
            jsonPath = Application.dataPath + "/Simulator/simulation.json";
            Destroy(myTorus, 2);
        }
        Instantiate(timeUIPrefab);

        state = ManagerState.Visualizating;

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

        foreach (Transform child in menuObjects.transform)
        {
            child.GetComponent<FadeMaterial>().toggleFade();
        }
        Destroy(menuObjects, 2);

        state = ManagerState.Visualizating;

        string sjson = File.ReadAllText(path);
        JSONObject jsonObject = new JSONObject(sjson);

        string cityPath = Application.dataPath+ "/ObjReader/Sample Files/city_obj.txt";// jsonObject.GetField("cityModel").ToString();
        loadCity(cityPath);
        simulationReady(path);
    }

    public float loadCity(string path)
    {

        this.cityPath = path;
        GameObject city = new GameObject("City");
        this.city = city;
        GetComponent<KeyBoardInput>().setCity(city);

        // Create and set parent
        GameObject[] houses = ObjReader.use.ConvertFile(path, false, material, transparent);
        foreach (GameObject house in houses)
        {
            house.transform.SetParent(city.transform);
        }
        // Compute Scale
        float minX, maxX;
        minX = 0;
        maxX = 0;

        foreach(GameObject house in houses)
        {
            Mesh mesh = house.GetComponent<MeshFilter>().mesh;
            float min = (mesh.bounds.center - mesh.bounds.extents).x;
            float max = (mesh.bounds.center + mesh.bounds.extents).x;

            if (min < minX) minX = min;
            if (max > maxX) maxX = max;

        }
        
        // Move gravity point to center
        foreach (GameObject house in houses)
        {
            Mesh mesh = house.GetComponent<MeshFilter>().mesh;
            Vector3 center = mesh.bounds.center;
            
            // Move vertices accordingly
            List<Vector3> newVertices = new List<Vector3>();
            foreach(Vector3 vertex in mesh.vertices)
            {
                newVertices.Add(vertex -( center - house.transform.position ));
            }
            house.transform.position = center;
            mesh.vertices = newVertices.ToArray();

            mesh.RecalculateBounds();
            
        }

        // Add house components
        for(int i = 0; i < houses.Length; i++)
        {
            GameObject house = houses[i];
            
            house.AddComponent<SelectProfile>();

            BoxCollider collider = house.AddComponent<BoxCollider>();
            collider.center = Vector3.zero;

            HouseIdentity identity = house.AddComponent<HouseIdentity>();
            identity.id = i;

            

            FadeMaterial fader = house.AddComponent<FadeMaterial>();
            fader.original = transparent;
            fader.fade = material;
            fader.house = true;
        }
        TreeBuilder treeBuilder = new TreeBuilder(city);
        treeBuilder.buildNearestTree();
        wireGOs = treeBuilder.createWires(wirePrefab, sparkPrefab);

        return Math.Max(Math.Abs(minX), maxX);
    }

    public ManagerState getState() { return state; }
}
