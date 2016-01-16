using UnityEngine;
using System.Collections;

public class LoadSimulation : MonoBehaviour {

	void OnMouseDown ()
    {
        string path = DialogManager.showJsonDialog();
        Manager.Instance.loadJson(path);
    }
}
