using UnityEngine;
using UnityEditor;
using System.IO;
using System.Collections.Generic;

public class PointToCenter : MonoBehaviour
{

    // Use this for initialization
    [MenuItem("Custom/Move transform point to center")]
    public static void MoveTransformToCenter()
    {
        foreach (GameObject shape in Selection.gameObjects)
        {
            Vector3 center;
            var mesh = shape.GetComponent<MeshFilter>();

            Vector3[] vertices = mesh.sharedMesh.vertices;

            center = Vector3.zero;

            foreach (Vector3 vertex in vertices)
            {
                center += vertex;
            }

            center /= vertices.Length;

            Vector3 moveVector = center - shape.transform.position;

            shape.transform.position = center; //move the center


            Vector3[] desiredVertices = new Vector3[vertices.Length];

            for (var i = 0; i < vertices.Length; i++)
            {
                desiredVertices[i] = vertices[i] - moveVector;// you'd like the mesh to stay at the same position!
            }

            mesh.sharedMesh.vertices = desiredVertices;
        }
    }

    [MenuItem("Custom/Export shapoe points as .txt")]
    public static void exportAsTxT()
    {
        List<string> lines = new List<string>();
        lines.Add(Selection.gameObjects.Length + " 2");

        foreach (GameObject shape in Selection.gameObjects)
        {
            lines.Add( string.Format("{0} {1}", shape.transform.position.x, shape.transform.position.z));
        }

        File.WriteAllLines("Output/graph points.txt", lines.ToArray());
    }
}
