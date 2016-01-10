using UnityEngine;
using System.Collections;

public class ShowHide : MonoBehaviour
{
    public GameObject target;

    void OnMouseDown()
    {
        target.SetActive(!target.activeSelf);
    }
}
