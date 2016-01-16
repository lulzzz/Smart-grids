using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class MoveOverTorus : MonoBehaviour
{
    [SerializeField]
    private float torusRadius = 4;

    public int hour;
    public int minute;

    void Start ()
    {

        int sign = Vector3.Cross(new Vector3(0, -1, 0), transform.position).z < 0 ? 1 : -1;
        float degrees = (sign * Vector3.Angle(new Vector3(0, -1, 0), transform.position) + 360) % 360;

        hour = (int)(degrees / 360 * 24);
        minute = (int)(((degrees / 360 * 24) % 1) * 60);
        minute = (minute / 5) * 5;
    }


    void OnMouseDrag ()
    {
        Vector2 midScreen = new Vector2(Screen.width / 2, Screen.height / 2);
        Vector2 mousePosition = new Vector2(Input.mousePosition.x, Input.mousePosition.y);

        Vector2 overTorus = (mousePosition - midScreen).normalized * torusRadius;

        transform.position = new Vector3(overTorus.x, overTorus.y, 0);
        
        int sign = Vector3.Cross(new Vector3(0, -1, 0), overTorus).z < 0 ? 1 : -1;
        float degrees = (sign * Vector3.Angle(new Vector3(0, -1, 0), overTorus) + 360) % 360;

        hour = (int) (degrees / 360 * 24);
        minute = (int)(((degrees / 360 * 24) % 1)*60);
        minute = (minute / 5) * 5;

        // manage text
        GetComponentInChildren<Canvas>().enabled = true;
        CancelInvoke("hideText");
        Text text = GetComponentInChildren<Text>();
        text.text = string.Format("{0:D2}:{1:D2}", hour, minute);
        Invoke("hideText", 1);
    }
    public void hideText()
    {
        GetComponentInChildren<Canvas>().enabled = false;
    }
}
