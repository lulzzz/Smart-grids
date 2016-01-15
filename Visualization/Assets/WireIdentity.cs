using UnityEngine;

public class WireIdentity : MonoBehaviour
{
    public GameObject from, to;
    
    private ChangeCapacity capacityChanger;
    
    
    void Start()
    {
        capacityChanger = GetComponent<ChangeCapacity>();
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.AddField("origin", from.GetComponent<HouseIdentity>().id);
        json.AddField("destination", to.GetComponent<HouseIdentity>().id);
        json.AddField("capacity", capacityChanger.capacity);
        return json;
    }
}
