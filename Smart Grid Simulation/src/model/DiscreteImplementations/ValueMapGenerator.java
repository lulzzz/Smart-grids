
package Model.DiscreteImplementations;

import Model.Interfaces.IGenerator;
import Model.Moment;
import com.google.gson.annotations.Expose;

public class ValueMapGenerator implements IGenerator 
{
    @Expose
    private GeneratorType type;
    @Expose
    private double productionPerHour = 5;
    @Expose
    private double efficiency;
    
    private TimeValueMap clouds;
    
    public ValueMapGenerator( GeneratorType type, TimeValueMap clouds )
    {
        this.type = type;
        this.clouds = clouds;
    }
    
    @Override
    public double getGeneration(Moment since, Moment until) 
    {
        double cloudMean = clouds.getMeanBetween(since, until);
        
        efficiency = (100 - cloudMean) / 100.0;
        
        int elapsedMinutes = until.minutesSince(since);
        
        double productionPerMinute = productionPerHour / 60.0;
        
        double production = elapsedMinutes * productionPerMinute * efficiency;
        
        return production;
    }
}
