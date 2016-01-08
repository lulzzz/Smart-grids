using UnityEngine;
using System.Collections;
using System.Diagnostics;

public class RunBat : MonoBehaviour
{

    // Use this for initialization
    public static void runSimulator()
    {
        var proc1 = new ProcessStartInfo();
        
        proc1.UseShellExecute = true;

        string java = Application.dataPath + @"\Simulator\simulator.jar";
        string param = "-i \"C:/Users/Martin/Documents/GitHub/Smart grids/Artifacts/edges.txt\" -f  12 -o \"C:/Users/Martin/Documents/GitHub/Smart grids/Artifacts\" -h 10 -m 0 -t 5";

        string anyCommand = "java -jar \"" + java+"\" " + param;
        
        proc1.FileName = @"C:\Windows\System32\cmd.exe";
        proc1.Verb = "runas";
        proc1.Arguments = "/K " + anyCommand;
        //proc1.WindowStyle = ProcessWindowStyle.Hidden;
        Process p = new Process();
        p.StartInfo = proc1;
        p.Start();
        p.WaitForExit();/*











        string simulatorPath = Application.dataPath + "/Simulator/simulator.jar";
        print("java -jar \"" + simulatorPath + "\"");
        Process process = new Process();
        ProcessStartInfo startInfo = new ProcessStartInfo();
        startInfo.FileName = "java.exe";
        startInfo.Arguments = "-jar \"C:/Users/Martin/Documents/GitHub/Smart grids/Visualization/Assets/Simulator/simulator.jar\""+
            "-i \"C:/Users/Martin/Documents/GitHub/Smart grids/Artifacts/edges.txt\" -f  12 -o \"C:/Users/Martin/Documents/GitHub/Smart grids/Artifacts\" -h 10 -m 0 -t 5";
        process.StartInfo = startInfo;
        process.Start();
        while (!process.HasExited) { }
        print(process.ExitCode);*/
    }
}