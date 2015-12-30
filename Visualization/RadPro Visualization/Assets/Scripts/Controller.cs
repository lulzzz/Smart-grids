﻿using UnityEngine;
using System.Collections;

public class Controller : MonoBehaviour
{
    [SerializeField]
    private GameObject cityModel;
    [SerializeField]
    private string simulatorPath;
    [SerializeField]
    private string outputFolder;

    private GameObject city;

	void Start ()
    {
        city = Instantiate(cityModel, Vector3.zero, Quaternion.identity) as GameObject;
        AnimationClip a;
	}

    public void MoveTransformToCenter( GameObject gameObject )
    {
        foreach (Transform shape in gameObject.transform)
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
}
