using UnityEngine;
using System.Collections;
using System.Runtime.InteropServices;
using System.Windows.Forms;

public class loadModelFromFile : MonoBehaviour
{
    [SerializeField]
    private string path;
    [SerializeField]
    private float platformRadius;
    private OpenFileDialog openDialog;
 
    [DllImport("user32.dll")]
    private static extern void OpenFileDialog(); //in your case : OpenFileDialog

    public void Start()
    {
        string path = open();
        loadMesh(path);
    }

    public string open()
    {
        openDialog = new OpenFileDialog();
        openDialog.InitialDirectory = UnityEngine.Application.dataPath;
        openDialog.Filter = "obj files|*.OBJ";
        openDialog.Title = "Select some music you want to listen to during this Game session.";
        openDialog.Multiselect = true;
        string[] result = null;
        if (openDialog.ShowDialog() == DialogResult.OK)
        {
            result = openDialog.FileNames;
        }
        if (openDialog.FileName == string.Empty)
        {
            result = null;
        }
        openDialog = null;
        return result[0];
    }

    public void loadMesh( string path )
    {
        Mesh mesh = ObjImporter.ImportFile(path);
        // Resize to fit in platform
        GetComponent<MeshFilter>().mesh = mesh;
        float size = Mathf.Max(mesh.bounds.size.x, mesh.bounds.size.z);
        // Some math
        float ratio = .9f * platformRadius / (Mathf.Sqrt(2) * size);
        gameObject.transform.localScale = new Vector3
            (
                transform.localScale.x * ratio,
                transform.localScale.y,
                transform.localScale.z * ratio
            );
    }
}
