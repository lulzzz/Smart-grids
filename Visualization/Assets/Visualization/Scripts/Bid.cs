using UnityEngine;
using System.Collections.Generic;
using UnityEngine.UI;
using System.Collections;
using System.IO;

public class Bid : MonoBehaviour
{
    public List<Sprite> bidPlots;
    private Image image;
    private FrameAnimator animator;

    public void Awake()
    {
        image = GetComponent<Image>();
        bidPlots = new List<Sprite>();
    }

    public void addPlot( string path )
    {
        byte[] data = File.ReadAllBytes(path);
        Texture2D texture = new Texture2D(128, 128, TextureFormat.ARGB32, false);
        texture.LoadImage(data);
        Sprite sprite = Sprite.Create(texture, new Rect(0.0f, 0.0f, texture.width, texture.height), new Vector2(0.5f, 0.5f), 1000);
        bidPlots.Add(sprite);
    }

    public void ready()
    {
        animator = gameObject.AddComponent<FrameAnimator>();
        animator.setData(bidPlots.Count);
        InvokeRepeating("animate", 0, 1 / FrameAnimator.animRate);
    }

    public void animate()
    {
        image.sprite = bidPlots[animator.getCurrentIndex()];
    }
}