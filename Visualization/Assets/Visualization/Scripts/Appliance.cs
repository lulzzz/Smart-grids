using UnityEngine;
using System.Collections.Generic;

public class Appliance
{
    private Sprite sprite;
    private List<float> progresses;

    public Appliance() { progresses = new List<float>(); }

    public void addProgress( float progress ) { progresses.Add(progress); }
}
