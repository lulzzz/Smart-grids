using UnityEngine;
using System.Collections.Generic;

public class Bid
{
    private List<Sprite> bidPlots;

    public Bid() { bidPlots = new List<Sprite>(); }

    public void addPlot( Sprite plot) { bidPlots.Add(plot); }
}