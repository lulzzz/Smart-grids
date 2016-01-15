using UnityEngine;
using System.Collections;

public class HouseIdentity : MonoBehaviour
{
    public int id;
    public SelectProfile profile;
    
    void Start()
    {
        profile = GetComponent<SelectProfile>();
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.AddField("id", id);
        json.AddField("profile", profile.assigned);
        return json;
    }
}
