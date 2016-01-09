using UnityEngine;
using System.Collections;

public class LoadNextScene : MonoBehaviour
{
    executeSimulation simulation = null;

    void OnMouseDown()
    {
        
        executeSimulation.SimulationInput input = new executeSimulation.SimulationInput();
        input.folder = Application.dataPath + "/Simulator";
        input.hour = 10;
        input.minute = 0;
        input.timeStep = 5;
        input.frames = 12;

        simulation.input = input;
    }

    void Update()
    {
        if (simulation != null && simulation.Update())
            Destroy(gameObject);
    }
}
