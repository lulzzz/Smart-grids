
package Model.Implementations;

import Model.Interfaces.IGenerator;
import Model.Core.*;
import com.google.gson.annotations.Expose;

public class SolarGenerator implements IGenerator 
{
    @Expose
    private GeneratorType type;
    @Expose
    private double productionPerHour = 500;
    @Expose
    private double efficiency;
    
    public SolarGenerator( GeneratorType type )
    {
        this.type = type;
    }
    
    @Override
    public void setStartingMoment( Moment moment, Weather weather )
    {
        double c = weather.getClouds(moment);
        
        efficiency = (100 - c) / 100.0;
    }
    
    @Override
    public double getGeneration(Moment since, Moment until, Weather weather) 
    {
        double cloudMean = weather.getMeanCloudsBetween(since, until);
        
        efficiency = (100 - cloudMean) / 100.0;
        
        int elapsedMinutes = until.minutesSince(since);
        
        double productionPerMinute = productionPerHour / 60.0;
        
        double production = elapsedMinutes * productionPerMinute * efficiency;
        
        return production;
    }
}
