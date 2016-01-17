
package Model.Implementations;

import Model.Interfaces.IGenerator;
import Model.Core.*;
import com.google.gson.annotations.Expose;

public class WindGenerator implements IGenerator 
{
    @Expose
    private GeneratorType type;
    @Expose
    private double productionPerHour = 500;
    @Expose
    private double efficiency;
    
    public WindGenerator()
    {
        this.type = GeneratorType.Wind;
    }
    
    @Override
    public void setStartingMoment( Moment moment, Weather weather )
    {
        double c = weather.getWindSpeed(moment);
        
        efficiency = c/100.0;
    }
    
    @Override
    public double getGeneration(Moment since, Moment until, Weather weather) 
    {
        double windMean = weather.getMeanWindBetween(since, until);
        
        efficiency = windMean/100.0;
        
        int elapsedMinutes = until.minutesSince(since);
        
        double productionPerMinute = productionPerHour / 60.0;
        
        double production = elapsedMinutes * productionPerMinute * efficiency;
        
        return production;
    }
}
