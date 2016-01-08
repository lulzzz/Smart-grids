using UnityEngine;
using System.Collections.Generic;
using System.Collections;
using System;

public class FrameAnimator : MonoBehaviour
{
    public static float animRate = 20;
    private static float pauseTime = 2;

    private int current = 0;
    private int next = 1;
    private int frames = 0;
    private float diff;

    private List<float> data;
    private int amount;

    private bool animateWithinValues;

    public void Start()
    {
        InvokeRepeating("animate", 0, 1 / FrameAnimator.animRate);
    }
    
    public void animate()
    {
        if (frames == animRate + 1)
        {
            frames = 0;
            if( animateWithinValues )
                diff = (data[next] - data[current]) / animRate;
            if (Manager.Instance.repeatAnimation())
            {
                CancelInvoke("animate");
                InvokeRepeating("animate", pauseTime, 1 / animRate);
                StartCoroutine(pause(current));
            }
            else
            {
                if (current == amount - 1)
                {
                    CancelInvoke("animate");
                    InvokeRepeating("animate", pauseTime, 1 / animRate);
                    StartCoroutine(pause(current));
                    current = 0;
                    next = 1;
                }
                else
                {
                    current = next;
                    next = (next + 1) % amount;
                }
            }
        }
        frames++;
    }

    public void setData(int amount)
    {
        this.amount = amount;
        animateWithinValues = false;
    }

    public void setData(List<float> data)
    {
        this.data = data;
        this.amount = data.Count;
        animateWithinValues = true;
    }

    private IEnumerator pause(int returnIndex)
    {
        yield return new WaitForSeconds(2);
        current = returnIndex;
    }

    public int getCurrentIndex()
    {
        return current;
    }

    public float getDiff()
    {
        return diff;
    }
}
