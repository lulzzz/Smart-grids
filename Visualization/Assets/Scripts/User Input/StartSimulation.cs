using UnityEngine;
using System.Collections;

public class StartAnimation : MonoBehaviour
{
	public void OnMouseDown()
    {
        GetComponent<Animator>().enabled = true;
        Manager.Instance.startSimulation();
    }

    public void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            #if UNITY_EDITOR
                UnityEditor.EditorApplication.isPlaying = false;
            #else
                     Application.Quit();
            #endif
        }
    }
}
