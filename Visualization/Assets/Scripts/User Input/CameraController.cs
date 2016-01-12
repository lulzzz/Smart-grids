using UnityEngine;

public class CameraController : MonoBehaviour
{
    [SerializeField] private float speed;
    [SerializeField] private float rotateSpeed;

    private Vector2 currentPosition, lastPosition;

    private float originalSpeed;

    public void Start()
    {
        originalSpeed = speed;
    }

    public void Update()
    {

        if (Input.GetKey(KeyCode.W))
            transform.position = transform.position + transform.forward * speed * Time.deltaTime;

        if (Input.GetKey(KeyCode.A))
            transform.position = transform.position - transform.right * speed * Time.deltaTime;

        if (Input.GetKey(KeyCode.S))
            transform.position = transform.position - transform.forward * speed * Time.deltaTime;

        if (Input.GetKey(KeyCode.D))
            transform.position = transform.position + transform.right * speed * Time.deltaTime;

        if (Input.GetMouseButtonDown(1))
            lastPosition = Input.mousePosition;

        if (Input.GetMouseButton(1))
        {
            currentPosition = Input.mousePosition;
            var deltaPositon = currentPosition - lastPosition;

            Vector3 deltaRotation = new Vector3(-deltaPositon.y, deltaPositon.x, 0) * rotateSpeed * Time.deltaTime;

            transform.localEulerAngles = transform.localEulerAngles + deltaRotation;
            lastPosition = currentPosition;
        }
        if (Input.GetKey(KeyCode.LeftShift))
            speed = originalSpeed * 4;
        else
            speed = originalSpeed;
    }
}