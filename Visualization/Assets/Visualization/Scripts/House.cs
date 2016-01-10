using UnityEngine;
using System.Collections.Generic;
using System;
using UnityEngine.UI;

public class House : MonoBehaviour
{
    public Battery battery;
    public List<Appliance> appliances;
    public List<Generator> generators;
    public Bid bid;

    public ApplianceSpriteDictionary spriteOfAppliance;
    public GeneratorSpriteDictionary spriteOfGenerator;
    public GameObject appliancePrefab;
    public GameObject generatorPrefab;

    public GameObject applianceParent;
    public GameObject generatorParent;


    public void Awake()
    {
        battery = GetComponentInChildren<Battery>();
        appliances = new List<Appliance>();
        generators = new List<Generator>();
        bid = GetComponentInChildren<Bid>();
    }

    public void ready()
    {
        bid.ready();
        battery.ready();
        foreach( Appliance appliance in appliances )
        {
            appliance.ready();
        }
    }

    public void addAppliance( ApplianceType type )
    {
        GameObject applianceGO = Instantiate(appliancePrefab) as GameObject;
        applianceGO.transform.GetChild(0).GetComponent<Image>().sprite = spriteOfAppliance[type];
        applianceGO.transform.SetParent(applianceParent.transform);
        
        applianceGO.GetComponent<RectTransform>().localScale = Vector3.one;
        applianceGO.GetComponent<RectTransform>().offsetMin = Vector2.zero;
        applianceGO.GetComponent<RectTransform>().offsetMax = Vector2.zero;
        applianceGO.transform.localPosition = new Vector3(applianceGO.transform.localPosition.x, applianceGO.transform.localPosition.y, 0);

        appliances.Add(applianceGO.GetComponentInChildren<Appliance>());
    }

    public void addApplianceProgress( int id, float progress )
    {
        appliances[id].addProgress(progress);
    }

    public void addGenerator(GeneratorType type)
    {
        GameObject applianceGO = Instantiate(generatorPrefab) as GameObject;
        applianceGO.transform.GetChild(0).GetComponent<Image>().sprite = spriteOfGenerator[type];
        applianceGO.transform.SetParent(generatorParent.transform);

        applianceGO.GetComponent<RectTransform>().localScale = Vector3.one;
        applianceGO.GetComponent<RectTransform>().offsetMin = Vector2.zero;
        applianceGO.GetComponent<RectTransform>().offsetMax = Vector2.zero;
        applianceGO.transform.localPosition = new Vector3(applianceGO.transform.localPosition.x, applianceGO.transform.localPosition.y, 0);


        generators.Add(applianceGO.GetComponentInChildren<Generator>());
    }

    public void addGeneratorEfficiency(int id, float efficiency)
    {
        generators[id].addEfficiency(efficiency);
    }

    public void addBatteryPercent(float percent)
    {
        battery.addPercent(percent);
    }

    public void addBidPlot(string plotFile)
    {
        bid.addPlot(plotFile.Replace("\"",""));
    }
}
