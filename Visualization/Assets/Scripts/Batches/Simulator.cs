using System.Diagnostics;

public class Simulator : ThreadedJob
{
    public string simulator;
    public string input;
    protected override void ThreadFunction()
    {
        
        var proc1 = new ProcessStartInfo();

        proc1.UseShellExecute = true;
        string command = string.Format("java -jar \"{0}\" \"{1}\"", simulator, input);

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
        Manager.Instance.simulationReady(null);
    }
}