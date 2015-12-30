using UnityEngine;
using System.Collections.Generic;

[RequireComponent(typeof(Animation))]
public class PaintOffers : MonoBehaviour
{
    public Animation anim;
    void Start()
    {
        anim = GetComponent<Animation>();
        AnimationCurve curve = AnimationCurve.Linear(0.5f, 1, 2, 3);
        AnimationCurve c = AnimationCurve.Linear(2, 3, 4, 0);
        AnimationClip clip = new AnimationClip();
        clip.legacy = true;
        clip.SetCurve("", typeof(Transform), "localPosition.x", curve);
        anim.AddClip(clip, "test");
        anim.Play("test");

        Animator ats;
    }
}