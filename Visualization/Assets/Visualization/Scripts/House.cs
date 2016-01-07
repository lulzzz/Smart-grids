using UnityEngine;
using System.Collections.Generic;
using System;

public class House : MonoBehaviour, IEquatable<House>
{
    public Battery battery;
    public int id;
    public List<Appliance> appliances;
    public List<Generator> generators;
    public Bid bid;
    public GameObject applianceParent;

    public House(int id)
    {
        battery = new Battery();
        this.id = id;
        appliances = new List<Appliance>();
        generators = new List<Generator>();
        bid = new Bid();
    }

    public bool Equals(House other)
    {
        if (other == null) return false;
        return this.id.Equals(other.id);
    }
}
