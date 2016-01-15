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
 
    void Start ()
    {
        assigned = 2;
        rend = GetComponent<Renderer>();
    }

    void OnMouseEnter ()
    {
        if(!alwaysShown)
            rend.material.color = colors[assigned];
        over = true;
	}
	
	void OnMouseExit ()
    {
        if(!alwaysShown)
            rend.material.color = colors[0];
        over = false;
    }

    void OnMouseOver()
    {
        for (int i = 0; i < acceptedKeyCodes.Length; i++)
        {
            if (Input.GetKeyDown(acceptedKeyCodes[i]))
            {
                assigned = i + 1;
                rend.material.color = colors[assigned];
            }
        }
    }

    public void toggleAlwaysShown()
    {
        alwaysShown = !alwaysShown;
        if (alwaysShown)
            rend.material.color = colors[assigned];
        else if( !over )
            rend.material.color = colors[0];

    }
}
