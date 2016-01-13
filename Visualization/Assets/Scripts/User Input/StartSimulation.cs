using UnityEngine;
using System.Collections;

public class StartSimulation : MonoBehaviour
{
	public void OnMouseDown()
    {
        GetComponent<Animator>().enabled = true;
        Manager.Instance.startSimulation();
    }
}
