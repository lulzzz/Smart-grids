using UnityEngine;
using System.Collections.Generic;

public class Weather : MonoBehaviour
{
    private List<float> clouds;

    public Weather() { clouds = new List<float>(); }

    public void addCloud( float cloud ) { clouds.Add(cloud); }
}
