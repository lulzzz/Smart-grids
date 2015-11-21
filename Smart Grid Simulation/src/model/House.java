
package model;

import com.google.gson.Gson;

/**
 *
 * @author Martin
 */
public class House
{
    public static int totalHouses = 0;
    public int id;
    public float[] position;
    public float minConsum;
    public float necessity;
    
    public House(float[] position)
    {
        totalHouses++;
        
        this.id = totalHouses;
        this.position = position;
    }
    
}
