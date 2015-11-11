using UnityEngine;
using System.Collections.Generic;

public class PaintOffers : MonoBehaviour
{
    public GameObject offerTextPrefab;

    public void paint(List<string> offers)
    {
        foreach (Transform child in transform) Destroy(child.gameObject);
        int i = 0;
        foreach( string offer in offers )
        {
            GameObject offerGO = Instantiate(offerTextPrefab);
            offerGO.transform.SetParent(transform);
            offerGO.transform.localPosition = new Vector3(-.4f, 0, .4f - .1f * i);
            offerGO.transform.localEulerAngles = new Vector3(90, 0, 0);
            offerGO.transform.localScale = new Vector3(.03f, .05f, .05f);
            offerGO.GetComponent<TextMesh>().text = offer;
            i++;
        }
    }
}