using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class DaynightCycle : MonoBehaviour
{
    public float speed = 200f;
    public bool simulating;
    public Transform sun;
    public Transform moon;
    public Slider slider;
    public float currentRotation;
    
    void Start()
    {
        setHour(12);
        currentRotation = 180;
    }
    void Update()
    {
        if (simulating)
        {
            sun.transform.RotateAround(Vector3.zero, Vector3.forward, speed);
            moon.transform.RotateAround(Vector3.zero, Vector3.forward, speed);
        
            currentRotation = (currentRotation + speed) % 360;
            float hour = (currentRotation / 360f) * 24;
            slider.value = (int)hour;

            moon.transform.RotateAround(Vector3.zero, Vector3.forward, speed * Time.deltaTime);
        }

    }
    

    public void setHour(float value)
    {
        float rotation = 360f / 24 * value;
        print(rotation);

        sun.transform.eulerAngles = new Vector3(90, 0, 0);
        sun.transform.RotateAround(Vector3.zero, Vector3.forward, 180+rotation);
        moon.transform.eulerAngles = new Vector3(90, 0, 0);
        moon.transform.RotateAround(Vector3.zero, Vector3.forward, rotation);

        currentRotation = rotation;
    }
}