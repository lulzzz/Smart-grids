﻿using UnityEngine;
using System.Collections;

public class BuildNewSimulation : MonoBehaviour
{

    public Material standardMaterial;
    public Material transparentMaterial;

    void OnMouseDown ()
    {
        string path = DialogManager.showObjDialog();
        if (path != null)
        {
            float size = Manager.Instance.loadCity(path);

            Manager.Instance.cityImported(size);
        }
    }
}
