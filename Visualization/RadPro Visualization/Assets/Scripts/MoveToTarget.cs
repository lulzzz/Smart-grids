using UnityEngine;
using System.Collections.Generic;

public class MoveToTarget : MonoBehaviour
{
    public Transform origin;
    public Transform target;
    public float speed;
    public bool repeat;
    
	void FixedUpdate ()
    {
        transform.LookAt(target.transform);
        transform.position = Vector3.MoveTowards(transform.position, target.position, speed * Time.deltaTime);
        if( Vector3.Distance( transform.position, target.position) < .1f)
        {
            if (repeat)
                transform.position = origin.position;
            else
                Destroy(gameObject);
        }
	}
}
