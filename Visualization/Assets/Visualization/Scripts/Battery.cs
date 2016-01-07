using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;

public class Battery : MonoBehaviour
{
    [SerializeField]
    private List<float> percents;

    public Battery() { percents = new List<float>(); }

    public void addPercent( float percent) { percents.Add(percent); Debug.Log(percents.Count); }
    
}
