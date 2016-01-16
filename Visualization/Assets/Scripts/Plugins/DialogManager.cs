using UnityEngine;
using System.Windows.Forms;
using System.Runtime.InteropServices;

public class DialogManager : MonoBehaviour
{
    private static OpenFileDialog openDialog;

    [DllImport("user32.dll")]
    private static extern void OpenFileDialog();

    public static string showObjDialog()
    {
        openDialog = new OpenFileDialog();
        openDialog.InitialDirectory = UnityEngine.Application.dataPath;
        openDialog.Filter = "obj files|*.txt";
        openDialog.Title = "Select the city model";
        openDialog.Multiselect = false;
        string[] result = null;
        if (openDialog.ShowDialog() == DialogResult.OK)
        {
            result = openDialog.FileNames;
        }
        if (openDialog.FileName == string.Empty)
        {
            return null;
        }
        openDialog = null;
        return result[0];
    }

    public static string showJsonDialog()
    {
        openDialog = new OpenFileDialog();
        openDialog.InitialDirectory = UnityEngine.Application.dataPath;
        openDialog.Filter = "json files|*.json";
        openDialog.Title = "Select the simulation json file";
        openDialog.Multiselect = false;
        string[] result = null;
        if (openDialog.ShowDialog() == DialogResult.OK)
        {
            result = openDialog.FileNames;
        }
        if (openDialog.FileName == string.Empty)
        {
            return null;
        }
        openDialog = null;
        return result[0];
    }
}
