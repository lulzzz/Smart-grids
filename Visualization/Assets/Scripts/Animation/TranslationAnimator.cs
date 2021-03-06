﻿using UnityEngine;
using System.Collections.Generic;

public class TranslationAnimator : MonoBehaviour
{
    public Vector3 from;
    public Vector3 to;

    public int current = 0;
    public List<float> flows;

    public void Awake()
    {
        flows = new List<float>();
        enabled = false;
    }

    public void addFlow(float flow)
    {
        flows.Add(flow);
    }

    public void reset()
    {
        flows = new List<float>();
        CancelInvoke("frameEnded");
        this.enabled = false;
    }

    public void animate()
    {
        transform.position = flows[current] > 0 ? from : to;
        InvokeRepeating("frameEnded", 1, 1);
        this.enabled = true;
    }

    public void stop()
    {
        this.enabled = false;
        CancelInvoke();
    }

    public void advance()
    {
        int next = (current + 1) % flows.Count;
        current = next;
        next = (next + 1) % flows.Count;

        transform.position = flows[current] > 0 ? from : to;
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
        print("pew");
        if (Manager.Instance.repeat)
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
