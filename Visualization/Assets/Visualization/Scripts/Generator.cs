using UnityEngine;
using System.Collections.Generic;

public class Generator : MonoBehaviour
{
    private Sprite sprite;
    private List<float> efficiencies;

    public void Awake() { efficiencies = new List<float>(); }

    public void addEfficiency( float efficiency ) { efficiencies.Add(efficiency); }
}
