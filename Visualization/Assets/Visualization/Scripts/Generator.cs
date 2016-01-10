using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;

public class Generator : MonoBehaviour
{
    private Image image;
    public List<float> efficiencies;
    private FrameAnimator animator;

    public void Awake() { efficiencies = new List<float>(); image = GetComponent<Image>(); }

    public void addEfficiency( float efficiency ) { efficiencies.Add(efficiency); }

    public void ready()
    {
        animator = gameObject.AddComponent<FrameAnimator>();
        animator.setData(efficiencies);
        InvokeRepeating("animate", 0, 1 / FrameAnimator.animRate);
    }

    public void animate()
    {
        image.fillAmount += animator.getDiff();
    }
}
