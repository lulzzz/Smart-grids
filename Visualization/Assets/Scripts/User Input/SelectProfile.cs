using UnityEngine;
using System.Collections.Generic;
using System;

public class SelectProfile : MonoBehaviour
{
    public List<Color> colors;

    public int assigned;

    public bool alwaysShown;

    private KeyCode[] acceptedKeyCodes = 
    {
         KeyCode.Alpha1,
         KeyCode.Alpha2,
         KeyCode.Alpha3,
     };

    private Renderer rend;

    private bool over;
 
    void Awake ()
    {
        assigned = 2;
        rend = GetComponent<Renderer>();
    }

    void OnMouseEnter ()
    {
        if (!alwaysShown)
            changeColor(assigned);
        over = true;
	}
	
	void OnMouseExit ()
    {
        if (!alwaysShown)
            changeColor(0);
        over = false;
    }

    void OnMouseOver()
    {
        for (int i = 0; i < acceptedKeyCodes.Length; i++)
        {
            if (Input.GetKeyDown(acceptedKeyCodes[i]))
            {
                assigned = i + 1;
                changeColor(assigned);
            }
        }
    }

    public void toggleAlwaysShown()
    {
        alwaysShown = !alwaysShown;
        if (alwaysShown)
            changeColor(assigned);
        else if (!over)
            changeColor(0);

    }

    private void changeColor(int index)
    {

        rend.material.color = new Color(colors[index].r, colors[index].g, colors[index].b, rend.material.color.a);
    }

}
