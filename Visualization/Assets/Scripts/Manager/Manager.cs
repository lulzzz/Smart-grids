using UnityEngine;
using System.Collections.Generic;

public class Manager : MonoBehaviour
{
    [SerializeField] private GameObject city;
    
    [SerializeField] private GameObject wirePrefab;
    [SerializeField] private GameObject infoPanelPrefab;
    [SerializeField] private ApplianceSpriteDictionary spriteOfAppliance;
    [SerializeField] private GeneratorSpriteDictionary spriteOfGenerator;

    [SerializeField] private GameObject appliancePrefab;
    [SerializeField] private GameObject generatorPrefab;
    [SerializeField] private bool repeat = false;
    [SerializeField] private List<FadeMaterial> fadeAfterMenu;
    [SerializeField] private List<FadeMaterial> fadeAfterSimulation;

    private List<GameObject> wireGOs = new List<GameObject>();

    private Simulator simulation;
    public Simulator.SimulationInput simulationInput;

    public static Manager Instance { get; private set; }

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
    }

    public void startSimulation()
    {
        // Fade menu
        foreach (FadeMaterial toFade in fadeAfterMenu)
            toFade.toggleFade();

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
            #else
                     Application.Quit();
            #endif
        }

        if(Input.GetKeyDown(KeyCode.H))
        {
            foreach (Transform t in city.transform)
            {
                FadeMaterial fader = t.GetComponent<FadeMaterial>();
                if (fader.house)
                    fader.toggleFade();
            }
        }
        if (Input.GetKeyDown(KeyCode.G))
        {
            foreach (Transform t in city.transform)
            {
                FadeMaterial fader = t.GetComponent<FadeMaterial>();
                if (!fader.house)
                    fader.toggleFade();
            }
        }

        if (Input.GetKeyDown(KeyCode.P))
        {
            foreach (Transform t in city.transform)
            {
                SelectProfile profile = t.GetComponent<SelectProfile>();
                if (profile != null)
                    profile.toggleAlwaysShown();
            }
            Color c = new Color(RenderSettings.ambientLight.r, RenderSettings.ambientLight.g, RenderSettings.ambientLight.b + .1f);
            RenderSettings.ambientLight = c;
        }

        if ( simulation != null && simulation.Update() )
        
            simulation = null;
        
        
    }

    public void simulationReady( string jsonPath )
    {
        foreach (FadeMaterial toFade in fadeAfterSimulation)
            toFade.toggleFade();

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

    public bool getRepeat()
    {
        return repeat;
    }
}
