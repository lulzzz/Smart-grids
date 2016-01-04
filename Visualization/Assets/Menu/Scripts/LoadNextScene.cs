using UnityEngine;
using System.Collections;

public class LoadNextScene : MonoBehaviour
{
    void OnMouseDown()
    {
        Application.LoadLevel(Application.loadedLevel + 1);
    }
}
