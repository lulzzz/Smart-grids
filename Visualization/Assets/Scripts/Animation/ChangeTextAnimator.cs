using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;
using System.IO;
using System;

public class ChangeTextAnimator : MonoBehaviour
{
    public int current = 0;

    public List<string> bidPlots;
    private Text image;

    public void Awake()
    {
        bidPlots = new List<string>();
        enabled = false;
        image = GetComponent<Text>();
    }

    public void addString(string s)
    {
        bidPlots.Add(s);
    }


    public void animate()
    {
        image.text = bidPlots[current];
        InvokeRepeating("frameEnded", 1, 1);
        this.enabled = true;
    }

    public void FixedUpdate()
    {
        image.text = bidPlots[current];
    }

    public void frameEnded()
    {
        if (!Manager.Instance.repeat)
        {
            current = (current + 1) % bidPlots.Count;
        }
    }

    public void stop()
    {
        this.enabled = false;
        CancelInvoke();
    }
    public void advance()
    {
        int next = (current + 1) % bidPlots.Count;
        current = next;
        next = (next + 1) % bidPlots.Count;

        image.text = bidPlots[current];
    }
}