using UnityEngine;
using System.Collections.Generic;

public class Building : MonoBehaviour
{
    public Material originalMaterial;
    public Material glow;
    public GameObject offerTable;
    public Dictionary<string, string> testOffers = new Dictionary<string, string>()
    {
        {"3", "4" },
        {"0", "0" },
        {"-2", "-2" }
    };

    public void Start()
    {
        originalMaterial = GetComponent<Renderer>().material;
    }

    public void OnMouseEnter()
    {
        GetComponent<Renderer>().material = glow;
    }

    public void OnMouseExit()
    {
        GetComponent<Renderer>().material = originalMaterial;
    }

    public void OnMouseDown()
    {
        offerTable.SetActive(!offerTable.activeSelf);
        //offerTable.GetComponent<PaintOffers>().paint(testOffers);
    }
}