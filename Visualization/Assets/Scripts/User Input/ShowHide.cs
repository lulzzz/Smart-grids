using UnityEngine;

public class ShowHide : MonoBehaviour
{
    public GameObject target;

    void OnMouseDown()
    {
        target.SetActive(!target.activeSelf);
    }
}
