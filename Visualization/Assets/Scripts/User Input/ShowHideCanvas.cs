using UnityEngine;

public class ShowHideCanvas : MonoBehaviour
{
    public GameObject target;

    void OnMouseDown()
    {
        target.GetComponent<Canvas>().enabled = !target.GetComponent<Canvas>().enabled;
    }
}
