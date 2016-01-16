using UnityEngine;
using System.Collections;

public class BuildNewSimulation : MonoBehaviour
{
	void OnMouseDown ()
    {
        string path = DialogManager.showObjDialog();
        GameObject city = ObjImporter.Import(path);

        Manager.Instance.cityImported(path, city);
    }
}
