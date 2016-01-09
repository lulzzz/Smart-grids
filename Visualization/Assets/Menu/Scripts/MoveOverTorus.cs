using UnityEngine;
using System.Collections;

public class MoveOverTorus : MonoBehaviour
{
    [SerializeField]
    private float torusRadius = 4;
    [SerializeField]
    private bool end;

    void OnMouseDrag()
    {
        Vector2 midScreen = new Vector2(Screen.width / 2, Screen.height / 2);
        Vector2 mousePosition = new Vector2(Input.mousePosition.x, Input.mousePosition.y);

        Vector2 overTorus = (mousePosition - midScreen).normalized * torusRadius;

        transform.position = new Vector3(overTorus.x, overTorus.y, 0);
        
        int sign = Vector3.Cross(new Vector3(0, -1, 0), overTorus).z < 0 ? 1 : -1;
        float degrees = (sign * Vector3.Angle(new Vector3(0, -1, 0), overTorus) + 360) % 360;

        int hour = (int) (degrees / 360 * 24);
        int minutes = (int)(((degrees / 360 * 24) % 1)*60);
        minutes = (minutes / 5) * 5;
        print(hour + ":" + minutes);

        if (!end)
        {
            Manager.Instance.simulationInput.hour = hour;
            Manager.Instance.simulationInput.minute = minutes;
        }
        if(end)
        {
            int m = (hour - Manager.Instance.simulationInput.hour) * 60 + minutes - Manager.Instance.simulationInput.minute;
            Manager.Instance.simulationInput.frames = m / Manager.Instance.simulationInput.timeStep;
            print(m);
        }
    }
}
