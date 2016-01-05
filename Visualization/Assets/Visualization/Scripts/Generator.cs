using UnityEngine;
using System.Collections.Generic;

public class Generator
{
    private Sprite sprite;
    private List<float> efficiencies;

    public Generator() { efficiencies = new List<float>(); }

    public void addEfficiency( float efficiency ) { efficiencies.Add(efficiency); }
}
