using UnityEngine;

public class FadeMaterial : MonoBehaviour
{
    [SerializeField]
    private Material original;
    [SerializeField]
    private Material fade;
    [SerializeField]
    private float duration;
    [SerializeField]
    private bool destroyAfter;

    public bool house;

    private bool faded = false;

    private Renderer rend;
    private float lerp;

    void Start()
    {
        rend = GetComponent<Renderer>();
        rend.material = original;
        faded = false;
        lerp = 0;
    }

    public void toggleFade()
    {
        if (faded)
            InvokeRepeating("unfadeStep", 0, .05f);
        else
            InvokeRepeating("fadeStep", 0, .05f);
    }

    private void fadeStep()
    {
        lerp += duration / 20;
        rend.material.Lerp(original, fade, lerp);
        if (lerp > .99f)
        {
            faded = true;
            lerp = 0;
            if (destroyAfter)
                Destroy(gameObject);
            else
                CancelInvoke("fadeStep");
        }
    }

    private void unfadeStep()
    {
        lerp += duration / 20;
        rend.material.Lerp(fade, original, lerp);
        if (lerp > .99f)
        {
            faded = false;
            lerp = 0;
            if (destroyAfter)
                Destroy(gameObject);
            else
                CancelInvoke("unfadeStep");
        }
    }
}