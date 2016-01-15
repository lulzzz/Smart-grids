using UnityEngine;
using System.Collections.Generic;
using System.IO;

public class Supply : MonoBehaviour
{
    public GameObject supply;
    public List<GameObject> wires = new List<GameObject>();
    
    void Update ()
    {
        float max = 0;
        foreach( GameObject wire in wires )
        {
            if (wire.transform.localScale.x > max)
                max = wire.transform.localScale.x;
        }

        supply.transform.localScale = max * 2.2f * Vector3.one;
    }
}
