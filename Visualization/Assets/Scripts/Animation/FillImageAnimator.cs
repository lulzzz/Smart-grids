using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;

public class FrameAnimator : MonoBehaviour
{
    public int current = 0;

    public List<float> data;
    private Image image;

    public void Start()
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
        if( Manager.Instance.repeatAnimation() )
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
}
