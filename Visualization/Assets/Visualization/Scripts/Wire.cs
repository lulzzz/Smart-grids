using UnityEngine;
using System.Collections.Generic;

public class Wire
{
    private List<double> flows;

    public void Start() { flows = new List<double>(); }

    public void addFlow( double flow) { flows.Add(flow); }
}
