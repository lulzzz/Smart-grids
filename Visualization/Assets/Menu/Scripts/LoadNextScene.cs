using UnityEngine;
using System.Collections;

public class LoadNextScene : MonoBehaviour
{
    void OnMouseDown()
    {
        RunBat.runSimulator();
        //Application.LoadLevel(Application.loadedLevel + 1);
    }
}
