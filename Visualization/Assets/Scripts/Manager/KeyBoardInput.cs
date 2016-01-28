using UnityEngine;

public class KeyBoardInput : MonoBehaviour
{
    private GameObject city;

    public void setCity (GameObject city)
    {
        this.city = city;
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

        if (city != null)
        {



            if (Input.GetKeyDown(KeyCode.M))
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
            if(Input.GetKeyDown(KeyCode.C))
            {
                foreach(Canvas c in FindObjectsOfType<Canvas>())
                {
                    if( c.name.StartsWith("Can"))
                        c.enabled = !c.enabled;
                }
            }
        }
    }
}
