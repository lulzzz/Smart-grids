using UnityEngine;
using System.Collections;

public class MoveOverTorus : MonoBehaviour
{
    [SerializeField]
    private float torusRadius = 4;

    void OnMouseDrag()
    {
        Vector2 midScreen = new Vector2(Screen.width / 2, Screen.height / 2);
        Vector2 mousePosition = new Vector2(Input.mousePosition.x, Input.mousePosition.y);

        Vector2 overTorus = (mousePosition - midScreen).normalized * torusRadius;

        transform.position = new Vector3(overTorus.x, overTorus.y, 0);
        
        int sign = Vector3.Cross(new Vector3(0, -1, 0), overTorus).z < 0 ? 1 : -1;
        float degrees = (sign * Vector3.Angle(new Vector3(0, -1, 0), overTorus) + 360) % 360;
        Debug.Log((sign * Vector3.Angle(new Vector3(0, -1, 0), overTorus) + 360)%360);
        int hour = (int) (degrees / 360 * 24);
        int minutes = (int)(((degrees / 360 * 24) % 1)*60);
        print(hour+":"+minutes);
    }
}
