using UnityEngine;
using System.Collections;

public class Reposition : MonoBehaviour {

    public GameObject from, to;
    public GameObject spark;
    public float scaleStep;
    private bool over;
	// Use this for initialization
    void OnMouseEnter ()
    {
        over = true;
    }

    void OnMouseExit ()
    {
        over = false;
    }
	void Start ()
    {
        JSONObject json = new JSONObject();
        json.AddField("city model", "path");
        print("json"+json.ToString());

        from.GetComponent<Supply>().wires.Add(gameObject);
        to.GetComponent<Supply>().wires.Add(gameObject);
        Vector3 vector = to.transform.position - from.transform.position;
        float desiredSize = vector.magnitude;
        transform.localScale = new Vector3(transform.localScale.x, desiredSize*transform.localScale.y, transform.localScale.z);
        float angle = Vector3.Angle(Vector3.forward, vector);
        transform.localEulerAngles += Vector3.up * angle;
        transform.position = from.transform.position + vector / 2;
	}

    void Update()
    {
        if (transform.localScale.x <= .25f - scaleStep && over )
        {
            if (Input.GetAxis("Mouse ScrollWheel") > 0) // forward
            {
                spark.transform.localScale += Vector3.one * scaleStep;
                transform.localScale = new Vector3(transform.localScale.x + scaleStep, transform.localScale.y, transform.localScale.z + scaleStep);
            }
        }
        if( transform.localScale.x >= .1f + scaleStep && over )
        { 
            if (Input.GetAxis("Mouse ScrollWheel") < 0) // back
            {
                spark.transform.localScale -= Vector3.one * scaleStep;
                transform.localScale = new Vector3(transform.localScale.x - scaleStep, transform.localScale.y, transform.localScale.z - scaleStep);
            }
        }
    }
}
