﻿using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;
using System.IO;
using System;

public class ChangeImageAnimator : MonoBehaviour
{
    public int current = 0;

    public List<Sprite> bidPlots;
    private Image image;

    public void Awake()
    {
        bidPlots = new List<Sprite>();
        enabled = false;
        image = GetComponent<Image>();
    }

    public void addPlot(string path)
    {
        byte[] data = File.ReadAllBytes(path);
        Texture2D texture = new Texture2D(128, 128, TextureFormat.ARGB32, false);
        texture.LoadImage(data);
        Sprite sprite = Sprite.Create(texture, new Rect(0.0f, 0.0f, texture.width, texture.height), new Vector2(0.5f, 0.5f), 1000);
        bidPlots.Add(sprite);
    }


    public void animate()
    {
        image.sprite = bidPlots[current];
        InvokeRepeating("frameEnded", 1, 1);
        this.enabled = true;
    }

    public void FixedUpdate()
    {
        image.sprite = bidPlots[current];
    }

    public void frameEnded()
    {
        if (!Manager.Instance.repeat)
        {
            current = (current + 1) % bidPlots.Count;
        }
    }

    public void stop()
    {
        this.enabled = false;
        CancelInvoke();
    }
    public void advance()
    {
        int next = (current + 1) % bidPlots.Count;
        current = next;
        next = (next + 1) % bidPlots.Count;

        image.sprite = bidPlots[current];
    }
}