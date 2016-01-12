using UnityEngine;
using System.Collections.Generic;
using System;
using UnityEngine.UI;

public class InfoPanel : MonoBehaviour
{
    public FrameAnimator battery;
    public List<FrameAnimator> appliances;
    public List<FrameAnimator> generators;
    public ChangeImageAnimator bid;

    public ApplianceSpriteDictionary spriteOfAppliance;
    public GeneratorSpriteDictionary spriteOfGenerator;
    public GameObject appliancePrefab;
    public GameObject generatorPrefab;

    public GameObject applianceParent;
    public GameObject generatorParent;


    public void Awake()
    {
        appliances = new List<FrameAnimator>();
        generators = new List<FrameAnimator>();
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

        appliances.Add(applianceGO.GetComponentInChildren<FrameAnimator>());
    }

    public void addApplianceProgress( int id, float progress )
    {
        appliances[id].addFrame(progress);
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


        generators.Add(applianceGO.GetComponentInChildren<FrameAnimator>());
    }

    public void addGeneratorEfficiency(int id, float efficiency)
    {
        generators[id].addFrame(efficiency);
    }

    public void addBatteryPercent(float percent)
    {
        battery.addFrame(percent);
    }

    public void addBidPlot(string plotFile)
    {
        bid.addPlot(plotFile.Replace("\"",""));
    }
}
