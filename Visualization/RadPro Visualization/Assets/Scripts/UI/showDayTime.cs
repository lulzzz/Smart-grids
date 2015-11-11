using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class showDayTime : MonoBehaviour
{
    public void updatePosition( GameObject slider )
    {
        //Manager.Instance.stopSimulation();

        Slider sliderRect = slider.GetComponent<Slider>();
        RectTransform myRect = GetComponent<RectTransform>();

        myRect.anchorMin = new Vector2(.15f + sliderRect.value / sliderRect.maxValue * .6f, 0);

        myRect.anchorMax = new Vector2(.25f + sliderRect.value / sliderRect.maxValue * .6f, .1f);

        Text hourText = GetComponentInChildren<Text>();
        hourText.text = string.Format("{0}:00", (int)sliderRect.value);

        GetComponent<Image>().enabled = true;
        GetComponentInChildren<Text>().enabled = true;
        CancelInvoke("disappear");
        Invoke("disappear", 1);
    }

    private void disappear()
    {
        GetComponent<Image>().enabled = false;
        GetComponentInChildren<Text>().enabled = false;
    }
}
