using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;

public class Appliance : MonoBehaviour
{
    private static float animRate = 10;
    private int current = 0;
    private int next = 1;
    private int frames = 0;
    private List<float> progresses;
    private Image image;

    public void Awake() { progresses = new List<float>(); image = GetComponent<Image>(); }

    public void addProgress( float progress ) { progresses.Add(progress); }

    public void ready()
    {
        InvokeRepeating("animate", 0, 1 / animRate);
    }

    public void animate()
    {
        image.fillAmount += (progresses[next] - progresses[current]) / animRate;
        
    }
}
