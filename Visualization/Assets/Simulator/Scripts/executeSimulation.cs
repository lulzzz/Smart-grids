using UnityEngine;
using System.Diagnostics;
using System.Collections;

public class executeSimulation : ThreadedJob
{
    public string dataPath;
    public string jsonPath;

    protected override void ThreadFunction()
    { 
        var proc1 = new ProcessStartInfo();

        proc1.UseShellExecute = true;

        string java = dataPath + @"\Simulator\simulator.jar";
        string param = string.Format(
            "-i \"{0}\" -f  {1} -o \"{2}\" -h {3} -m {4} -t {5}",
            dataPath + @"\Simulator\edges.txt",
            12,
            dataPath + @"\Simulator",
            10,
            0,
            5);

        string anyCommand = "java -jar \"" + java + "\" " + param;

        proc1.FileName = @"C:\Windows\System32\cmd.exe";
        proc1.Verb = "runas";
        proc1.Arguments = "/K " + anyCommand;

        Process p = new Process();
        p.StartInfo = proc1;
        p.Start();
        p.WaitForExit();
        jsonPath = dataPath + @"/Simulator/simulation.json";
    }

    protected override void OnFinished()
    {
        Manager.Instance.simulationReady();
    }
}