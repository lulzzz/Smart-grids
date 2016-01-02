
package Model;

import Model.Interfaces.IGenerator;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.io.*;
import java.util.HashMap;

public class ValueMapGenerator implements IGenerator 
{
    private double productionPerHour = 5;
    
    private HashMap<Integer, Integer> generation;
    
    @Expose
    private double efficiency;
    
    public ValueMapGenerator( HashMap<Integer, Integer> generation )
    {
        this.generation = generation;
    }
    
    @Override
    public double getGeneration(Moment since, Moment until) 
    {
        int cloudTime = until.getHour() / 3 * 3;
        int clouds = generation.get(cloudTime);
        efficiency = (100 - clouds) / 100.0;
        
        int elapsedMinutes = until.minutesSince(since);
        
        double productionPerMinute = productionPerHour / 60.0;
        
        double production = elapsedMinutes * productionPerMinute * efficiency;
        
        return production;
    }
}
