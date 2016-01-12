using UnityEngine;
using System.Collections.Generic;

public class TranslationAnimator : MonoBehaviour
{
    public Vector3 from;
    public Vector3 to;

    public int current = 0;
    public List<float> flows;

    public void Start()
    {
        flows = new List<float>();
        enabled = false;
    }

    public void addFlow(float flow)
    {
        flows.Add(flow);
    }

    public void animate()
    {
        InvokeRepeating("frameEnded", 1, 1);
        this.enabled = true;
    }

    public void FixedUpdate()
    {
        if (flows[current] != 0)
        {
            Vector3 direction = flows[current] > 0 ? to - from : from - to;
            transform.Translate(direction * Time.deltaTime);
        }
    }

    public void frameEnded()
    {
        if (Manager.Instance.repeatAnimation())
        {
            transform.position = flows[current] > 0 ? from : to;
        }
        else
        {
            int next = (current + 1) % flows.Count;
            current = next;
            next = (next + 1) % flows.Count;
            transform.position = flows[current] > 0 ? from : to;
        }
    }
}
