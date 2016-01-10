using UnityEngine;
using System.Diagnostics;
using System.Collections;

public class executeSimulation : ThreadedJob
{
    public struct SimulationInput
    {
        public string folder;
        public int startingHour;
        public int startingMinute;
        public int endingHour;
        public int endingMinute;
        public int timeStep;
    }

    public SimulationInput input;
    public string jsonPath;

    protected override void ThreadFunction()
    {
        int m = (input.endingHour - input.startingHour) * 60 + input.endingMinute - input.startingMinute;
        int frames = m / input.timeStep;

        var proc1 = new ProcessStartInfo();

        proc1.UseShellExecute = true;

        string java = input.folder + "/simulator.jar";
        string param = string.Format(
            "-i \"{0}\" -f  {1} -o \"{2}\" -h {3} -m {4} -t {5}",
            input.folder + "/edges.txt",
            frames,
            input.folder,
            input.startingHour,
            input.startingMinute,
            input.timeStep);

        string anyCommand = "java -jar \"" + java + "\" " + param;

        proc1.FileName = @"C:\Windows\System32\cmd.exe";
        proc1.Verb = "runas";
        proc1.Arguments = "/K " + anyCommand;

        Process p = new Process();
        p.StartInfo = proc1;
        p.Start();
        p.WaitForExit();
        jsonPath = input.folder + "/simulation.json";
    }

    protected override void OnFinished()
    {
        Manager.Instance.simulationReady( jsonPath );
    }
}