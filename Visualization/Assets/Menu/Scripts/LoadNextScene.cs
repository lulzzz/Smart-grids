using UnityEngine;
using System.Collections;

public class LoadNextScene : MonoBehaviour
{
    executeSimulation simulation = null;

    void OnMouseDown()
    {
        simulation = new executeSimulation();
        simulation.dataPath = Application.dataPath;
        simulation.Start();
        GetComponent<Animator>().enabled = true;
    }

    void Update()
    {
        if (simulation != null && simulation.Update())
            Destroy(gameObject);
    }
}
