using UnityEngine;

public class loadModelFromFile : MonoBehaviour
{
    [SerializeField]
    private GameObject platform;

    void Start()
    {
        DontDestroyOnLoad(gameObject);
    }

    public void OnMouseDown()
    {
        string path = DialogManager.showDialog();
        loadMesh(path);
        Manager.Instance.city = gameObject;
    }
    
    public void loadMesh( string path )
    {
        Mesh mesh = ObjImporter.ImportFile(path);
        // Resize to fit in platform
        GetComponent<MeshFilter>().mesh = mesh;
        float size = Mathf.Max(mesh.bounds.size.x, mesh.bounds.size.z);
        // Some math
        float ratio = .9f * platform.transform.localScale.x / (Mathf.Sqrt(2) * size);
        gameObject.transform.localScale = ratio * transform.localScale;
    }
}
