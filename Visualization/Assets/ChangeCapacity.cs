using UnityEngine;
using System.Collections;

public class ChangeCapacity : MonoBehaviour
{
    public GameObject spark;

    public static float scaleStep = 0.01f;
    public static float minScale = .1f;
    public static float maxScale = .25f;

    private bool over;

    public float capacity;

    void Start ()
    {
        capacity = 10 + ((transform.localScale.x - minScale) / (maxScale - minScale)) * 15;
        spark = GetComponent<WireIdentity>().spark;
    }
    
    void OnMouseEnter()
    {
        over = true;
    }

    void OnMouseExit()
    {
        over = false;
    }
    void Update()
    {
        if (Manager.Instance.getState() == ManagerState.BuildingCity)
        {
            if (transform.localScale.x <= maxScale - scaleStep && over)
            {
                if (Input.GetAxis("Mouse ScrollWheel") > 0) // forward
                {
                    //spark.transform.localScale += Vector3.one * scaleStep;
                    transform.localScale = new Vector3(transform.localScale.x + scaleStep, transform.localScale.y, transform.localScale.z + scaleStep);
                    capacity = 10 + ((transform.localScale.x - minScale) / (maxScale - minScale)) * 15;
                }
            }
            if (transform.localScale.x >= minScale + scaleStep && over)
            {
                if (Input.GetAxis("Mouse ScrollWheel") < 0) // back
                {
                    //spark.transform.localScale -= Vector3.one * scaleStep;
                    transform.localScale = new Vector3(transform.localScale.x - scaleStep, transform.localScale.y, transform.localScale.z - scaleStep);
                    capacity = 10 + ((transform.localScale.x - minScale) / (maxScale - minScale)) * 15;
                }
            }
        }
    }
}
