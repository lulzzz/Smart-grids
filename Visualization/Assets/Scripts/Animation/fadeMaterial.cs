using UnityEngine;

public class FadeMaterial : MonoBehaviour
{
    [SerializeField]
    private Material original;
    [SerializeField]
    private Material faded;
    [SerializeField]
    private float duration;

    private Renderer rend;

    void Start()
    {
        rend = GetComponent<Renderer>();
        rend.material = original;
    }

    public void fade()
    {
        InvokeRepeating("fadeStep", 0, .05f);
    }

    private void fadeStep()
    {
        float lerp = Mathf.PingPong(Time.time, duration) / duration;
        rend.material.Lerp(original, faded, lerp);
        if (lerp > .9f)
        {
            CancelInvoke("fadeStep");
            Destroy(gameObject);
        }
    }
}