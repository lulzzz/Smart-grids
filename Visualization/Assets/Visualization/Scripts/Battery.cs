using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;

public class Battery : MonoBehaviour
{
    [SerializeField]
    private List<float> percents;
    private FrameAnimator animator;
    private Image image;

    public void Awake() { percents = new List<float>(); image = GetComponent<Image>(); }

    public void addPercent( float percent) { percents.Add(percent); }

    public void ready()
    {
        animator = gameObject.AddComponent<FrameAnimator>();
        animator.setData(percents);
        InvokeRepeating("animate", 0, 1 / FrameAnimator.animRate);
    }

    public void animate()
    {
        image.fillAmount += animator.getDiff();
    }

}
