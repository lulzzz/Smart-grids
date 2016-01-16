using System.Diagnostics;

public class Simulator : ThreadedJob
{
    public string simulator;
    public string input;
    protected override void ThreadFunction()
    {
        
        var proc1 = new ProcessStartInfo();

        proc1.UseShellExecute = true;
        /*
        string java = input.folder + "/simulator.jar";
        string param = string.Format(
            "-i \"{0}\" -f  {1} -o \"{2}\" -h {3} -m {4} -t {5}",
            input.folder + "/edges.txt",
            frames,
            input.folder,
            input.startingHour,
            input.startingMinute,
            input.timeStep);
            */
        string command = string.Format("java -jar \"{0}\" -i \"{1}\"", simulator, input);

        proc1.FileName = @"C:\Windows\System32\cmd.exe";
        proc1.Verb = "runas";
        proc1.Arguments = "/K " + command;

        Process p = new Process();
        p.StartInfo = proc1;
        p.Start();
        p.WaitForExit();
    }

    protected override void OnFinished()
    {
        Manager.Instance.simulationReady();
    }
}