
package Model;

import Model.Interfaces.IGenerator;
import com.google.gson.Gson;
import java.io.*;
import java.util.HashMap;

public class ValueMapGenerator implements IGenerator 
{
    private double productionPerHour = 5;
    
    private HashMap<Integer, Integer> cloudsAt = new HashMap<Integer,Integer>()
    {{
      put(0, 56);
      put(3, 76);
      put(6, 88);
      put(9, 88);
      put(12, 100);
      put(15, 88);
      put(18, 36);
      put(21, 92);  
    }};
    
    @Override
    public double getGeneration(Moment since, Moment until) 
    {
        int cloudTime = until.getHour() / 3 * 3;
        int clouds = cloudsAt.get(cloudTime);
        double productionPercent = (100 - clouds) / 100.0;
        
        int elapsedMinutes = until.minutesSince(since);
        
        double productionPerMinute = productionPerHour / 60.0;
        
        double production = elapsedMinutes * productionPerMinute * productionPercent;
        
        return production;
    }
}
