using UnityEngine;
using System.Collections;

public class showQuantityOnClick : MonoBehaviour
{
    public GameObject quatityText;
    void OnMouseDown()
    {
        quatityText.GetComponent<MeshRenderer>().enabled = !quatityText.GetComponent<MeshRenderer>().enabled;
    }
}
