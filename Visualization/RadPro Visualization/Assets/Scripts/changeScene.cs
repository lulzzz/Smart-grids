using UnityEngine;
using System.Collections;

public class changeScene : MonoBehaviour
{
    public void loadSimulationScene()
    {
        Application.LoadLevel(Application.loadedLevel + 1);
    }
}
