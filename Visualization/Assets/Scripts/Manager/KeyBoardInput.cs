using UnityEngine;

public class KeyBoardInput : MonoBehaviour
{
    private GameObject city;

    void Start ()
    {
        city = Manager.Instance.city;
    }
    void Update ()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            #if UNITY_EDITOR
                UnityEditor.EditorApplication.isPlaying = false;
            #else
                Application.Quit();
            #endif
        }

        if (Input.GetKeyDown(KeyCode.H))
        {
            foreach (Transform t in city.transform)
            {
                FadeMaterial fader = t.GetComponent<FadeMaterial>();
                if (fader.house)
                    fader.toggleFade();
            }
        }
        if (Input.GetKeyDown(KeyCode.G))
        {
            foreach (Transform t in city.transform)
            {
                FadeMaterial fader = t.GetComponent<FadeMaterial>();
                if (!fader.house)
                    fader.toggleFade();
            }
        }

        if (Input.GetKeyDown(KeyCode.P))
        {
            foreach (Transform t in city.transform)
            {
                SelectProfile profile = t.GetComponent<SelectProfile>();
                if (profile != null)
                    profile.toggleAlwaysShown();
            }
            Color c = new Color(RenderSettings.ambientLight.r, RenderSettings.ambientLight.g, RenderSettings.ambientLight.b + .1f);
            RenderSettings.ambientLight = c;
        }
    }
}
