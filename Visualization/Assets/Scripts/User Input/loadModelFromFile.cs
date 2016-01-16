using UnityEngine;

public class loadModelFromFile : MonoBehaviour
{
    [SerializeField]
    private GameObject platform;
    public string path = "no city selected";
    
    public void OnMouseDown()
    {
        path = DialogManager.showDialog();
        //loadMesh(path);
        
        path = path.Replace("\\", "/");
        
        //Manager.Instance.setCity( gameObject );
    }
    
    public void loadMesh( string path )
    {
        Mesh mesh = ObjImporter.ImportFile(path);
        // Resize to fit in platform
        float size = Mathf.Max(mesh.bounds.size.x, mesh.bounds.size.z);
        print(size);
        float desiredSize = 4;
        // Some math
        float ratio = desiredSize / size;

        GetComponent<MeshFilter>().mesh = mesh;
        gameObject.transform.localScale *= ratio;
    }
}
