using UnityEngine;
using System.Collections;
using System.Diagnostics;

public class RunBat : MonoBehaviour
{

    // Use this for initialization
    void Start()
    {
        Process foo = new Process();
        foo.StartInfo.FileName = "the_script.bat";
        foo.StartInfo.Arguments = "put your arguments here";
        foo.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
        foo.Start();
    }

    // Update is called once per frame
    void Update()
    {

    }
}