using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;

public class Battery : MonoBehaviour
{
    [SerializeField]
    private List<float> percents;

    public Battery() { percents = new List<float>(); }

    public void addPercent( float percent) { percents.Add(percent); Debug.Log(percents.Count); }

    void Start()
    {
        AnimationClip clip = new AnimationClip();
        clip.legacy = true;
        List<Keyframe> keyframes = new List<Keyframe>();
        for (int i = 0; i < percents.Count; i++)
            keyframes.Add(new Keyframe(10*i, percents[i]));
        AnimationCurve _curve = new AnimationCurve(keyframes.ToArray());
        clip.SetCurve("TestAnimation2", typeof(RectTransform), "color.r", _curve);
        clip.wrapMode = WrapMode.Loop;
        Animation animation = gameObject.AddComponent<Animation>();
        animation.AddClip(clip, "anim");
        print(animation.Play("anim"));
    }
}
