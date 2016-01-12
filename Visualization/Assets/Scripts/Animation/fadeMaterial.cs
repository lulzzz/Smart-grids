using UnityEngine;

public class fadeMaterial : MonoBehaviour
{
    public Material material1;
    public Material material2;
    public float duration = 2.0F;
    public Renderer rend;
    public bool pingpong;
    public float lerp = 0;

    void Start()
    {
        rend = GetComponent<Renderer>();
        rend.material = material1;
    }
    void Update()
    {

        if (!pingpong && lerp > .9f) print("now");
        else
        {
            lerp = Mathf.PingPong(Time.time, duration) / duration;
            rend.material.Lerp(material1, material2, lerp);
        }
    }
}