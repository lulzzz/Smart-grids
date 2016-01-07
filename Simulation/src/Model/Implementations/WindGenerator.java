
package Model.Implementations;

import Model.Interfaces.IGenerator;
import Model.Core.*;
import com.google.gson.annotations.Expose;

public class WindGenerator implements IGenerator 
{
    @Expose
    private int id;
    @Expose
    private GeneratorType type;
    @Expose
    private double productionPerHour = 500;
    @Expose
    private double speed;
    
    public WindGenerator( int id, GeneratorType type )
    {
        this.id = id;
        this.type = type;
    }
    
    @Override
    public void setStartingMoment( Moment moment, Weather weather )
    {
        double c = weather.getWindSpeed(moment);
        
        speed = c;
    }
    
    @Override
    public double getGeneration(Moment since, Moment until, Weather weather) 
    {
        double windMean = weather.getMeanWindBetween(since, until);
        
        speed = windMean;
        
        int elapsedMinutes = until.minutesSince(since);
        
        double productionPerMinute = productionPerHour / 60.0;
        
        double production = elapsedMinutes * productionPerMinute * speed;
        
        return production;
    }
}
