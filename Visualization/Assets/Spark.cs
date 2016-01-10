using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEditor;

public class Spark : MonoBehaviour
{
    public Vector3 from;
    public Vector3 to;
    public Vector3 start;
    public Vector3 end;
    public bool repeat;
    private Vector3 direction;
    public float flow = 0;
    public List<float> flows = new List<float>();

    void Start ()
    {
        direction = end - start;
	}

    void Update()
    {
        if (flow != 0)
        {
            transform.Translate(direction * Time.deltaTime);
            if (Vector3.Distance(transform.position, to) < Vector3.Distance(from, to)/10)
            {
                if (repeat) transform.position = flow > 0 ? start : end;
                else Destroy(gameObject);
            }
        }
    }

    public void setFlow(float value)
    {

        if (value > 0)
        {
            transform.position = start;
            direction = end - start;
            from = start;
            to = end;
        }
        else
        {
            transform.position = end;
            direction = start - end;
            from = end;
            to = start;
        }
        flow = value;
    }

    public void addFlow( float flow )
    {
        flows.Add(flow);
    }

    public void ready()
    {
        setFlow(flows[1]);
        repeat = true;
    }
}
