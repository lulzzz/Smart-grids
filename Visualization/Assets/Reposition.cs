using UnityEngine;
using System.Collections;

public class Reposition : MonoBehaviour {

    public GameObject from, to;
    
    
	void Start ()
    {
        from = GetComponent<WireIdentity>().from;
        to = GetComponent<WireIdentity>().to;

        from.GetComponent<Supply>().wires.Add(gameObject);
        to.GetComponent<Supply>().wires.Add(gameObject);
        Vector3 vector = to.transform.position - from.transform.position;
        float desiredSize = vector.magnitude;
        transform.localScale = new Vector3(transform.localScale.x, desiredSize*transform.localScale.y, transform.localScale.z);
        float angle = Vector3.Angle(Vector3.forward, vector);
        transform.localEulerAngles += Vector3.up * angle;
        transform.position = from.transform.position + vector / 2;
	}

    
}
