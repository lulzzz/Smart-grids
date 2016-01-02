using UnityEngine;
using System.Collections.Generic;
using System;
using System.Collections;
using System.IO;
using UnityEngine.UI;

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
    public int i = 0;

    public List<Sprite> plotFiles;

    public void Start()
    {
        originalMaterial = GetComponent<Renderer>().material;
        //GetComponentInChildren<Image>().sprite = plotFiles[0];
    }

    void Update()
    {
        if(Input.GetKeyDown(KeyCode.Space))
        {
            i = (i + 1) % 3;
            if( plotFiles.Count >= i+1)
            GetComponentInChildren<Image>().sprite = plotFiles[i];
        }
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
    }

    internal void addPlotFile(JSONObject jSONObject)
    {
        // load it 
        plotFiles.Add(LoadImage(jSONObject.ToString()));
    }

    private Sprite LoadImage( string path )
    {
        print(path.IndexOf(@"\\"));
        path = path.Replace(@"\\", @"\");
        path = path.Replace("\"", "");
        Texture2D tex = null;
        byte[] fileData;

        if (File.Exists(path))
        {
            fileData = File.ReadAllBytes(path);
            tex = new Texture2D(2, 2);
            tex.LoadImage(fileData); //..this will auto-resize the texture dimensions.
        }
        else
        {
            print(path);
            print("inexistent");
        }
        return Sprite.Create(tex, new Rect(0,0,tex.width,tex.height), new Vector2(.5f,.5f));
    }

}