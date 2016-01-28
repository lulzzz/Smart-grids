using UnityEngine;
using System.Collections;

public class LoadSimulation : MonoBehaviour {

	void OnMouseDown ()
    {
        string path = DialogManager.showJsonDialog().Replace("\\","/").Trim();
        print(path);
        if(path != null)
        Manager.Instance.loadJson(path);
    }
}
