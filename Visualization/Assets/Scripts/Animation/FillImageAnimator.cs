using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;
using System;

public class FillImageAnimator : MonoBehaviour
{
    public int current = 0;

    public List<float> data;
    private Image image;

    public void Awake()
    {
        data = new List<float>();
        enabled = false;
        image = GetComponent<Image>();
    }

    public void addFrame( float frame )
    {
        data.Add(frame);
    }

    public void animate()
    {
        image.fillAmount = data[current];
        InvokeRepeating("frameEnded", 1, 1);
        this.enabled = true;
    }

    public void FixedUpdate()
    {
        int next = (current + 1) % data.Count;
        image.fillAmount += (data[next] - data[current]) * Time.deltaTime;
    }

    public void frameEnded()
    {
        if( Manager.Instance.repeat )
        {
            image.fillAmount = data[current];
        }
        else
        {
            int next = (current + 1) % data.Count;
            current = next;
            next = (next + 1) % data.Count;
        }
    }

    public void stop()
    {
        this.enabled = false;
        CancelInvoke();
    }

    public void advance()
    {
        int next = (current + 1) % data.Count;
        current = next;
        next = (next + 1) % data.Count;

        image.fillAmount = data[current];
    }
}
