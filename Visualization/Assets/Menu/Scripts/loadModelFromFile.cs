using UnityEngine;
using System.Collections;

public class loadModelFromFile : MonoBehaviour
{
    [SerializeField]
    private string path;
    [SerializeField]
    private float platformRadius;


    public void Start()
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
