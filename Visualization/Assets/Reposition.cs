using UnityEngine;
using System.Collections;

public class Reposition : MonoBehaviour {

    public GameObject from, to;
    
    
	void Start ()
    {
        from = GetComponent<WireIdentity>().from;
        to = GetComponent<WireIdentity>().to;
        
        Vector3 vector = to.transform.position - from.transform.position;
        float desiredSize = vector.magnitude;
        transform.localScale = new Vector3(transform.localScale.x, desiredSize*transform.localScale.y, transform.localScale.z);
        float angle = Vector3.Angle(Vector3.forward, vector);
        int sign = Vector3.Cross(Vector3.forward, transform.position).z < 0 ? 1 : -1;
        transform.localEulerAngles += Vector3.up * sign * angle;
        Vector3 midPosition = from.transform.position + vector / 2;
        transform.position = new Vector3(midPosition.x, .25f, midPosition.z);
	}

    
}
