using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using System;

public class chooseHour : MonoBehaviour, IPointerUpHandler
{
    void Start()
    {
        GetComponent<Slider>().onValueChanged.AddListener(Manager.Instance.drawLinks);
    }

    public void OnPointerUp(PointerEventData eventData)
    {
        Manager.Instance.setHour(GetComponent<Slider>().value);
    }
}
