using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;

public class Appliance : MonoBehaviour
{
    public List<float> progresses;
    private Image image;
    private FrameAnimator animator;

    public void Awake() { progresses = new List<float>(); image = GetComponent<Image>(); }

    public void addProgress( float progress ) { print(name); progresses.Add(progress); }

    public void ready()
    {
        animator = gameObject.AddComponent<FrameAnimator>();
        animator.setData(progresses);
        InvokeRepeating("animate", 0, 1 / FrameAnimator.animRate);
    }

    public void animate()
    {
        image.fillAmount += animator.getDiff();
    }
}
