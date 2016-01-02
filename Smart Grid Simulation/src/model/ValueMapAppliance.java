
package Model;

import Model.Interfaces.IAppliance;
import Model.Interfaces.IAppliance.ApplianceState;
import com.google.gson.annotations.Expose;
import java.util.HashMap;

public class ValueMapAppliance implements IAppliance 
{
    @Expose
    private ApplianceState state;
    @Expose
    private ApplianceType type;
    @Expose
    private double progress;
    
    private HashMap<Moment, Double> consum;
    
    private int totalTime;
    
    public ValueMapAppliance( ApplianceType type, HashMap<Moment,Double> consum )
    {
        this.type = type;
        this.consum = consum;
        this.state = ApplianceState.Waiting;
        this.progress = 0;
        this.totalTime = 10;
    }
    @Override
    public double getConsum(Moment since, Moment until) 
    {
        double total = 0;
        for( Moment moment : consum.keySet())
        {
            if( moment.isBetween(since, until))
            {
                total += consum.get(moment);
            }
        }
        
        advanceProgress(until.minutesSince(since));
                
        return total;
    }

    @Override
    public ApplianceState getState() 
    {
        return state;
    }
    
    private void advanceProgress( int minutes )
    {
        progress = ( progress * totalTime + minutes ) / totalTime;
        if( progress <= 0) state = ApplianceState.Waiting;
        else if( progress < .99 ) state = ApplianceState.inExecution;
        else state = ApplianceState.Ended;
    }

    @Override
    public Moment getStartingTime() 
    {
        return new Moment(10,20);
    }
}
