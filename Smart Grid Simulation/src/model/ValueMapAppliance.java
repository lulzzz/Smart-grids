
package Model;

import Model.Interfaces.IAppliance;
import com.google.gson.annotations.Expose;
import java.util.HashMap;

public class ValueMapAppliance implements IAppliance 
{
    @Expose
    private ApplianceState state;
    @Expose
    private double progress;
    
    private int totalTime;
    
    private HashMap<Moment, Double> consumAt = new HashMap<Moment,Double>()
    {{
      put(new Moment(10,0), 1.0);
      put(new Moment(10,5), 2.0);
      put(new Moment(10,7), 2.0);
      put(new Moment(10,10), 1.0);
    }};
    
    public ValueMapAppliance( int totalTime )
    {
        state = ApplianceState.Waiting;
        progress = 0;
        this.totalTime = totalTime;        
    }
    @Override
    public double getConsum(Moment since, Moment until) 
    {
        double consum = 0;
        for( Moment moment : consumAt.keySet())
        {
            if( moment.isBetween(since, until))
            {
                consum += consumAt.get(moment);
            }
        }
        
        advanceProgress(until.minutesSince(since));
                
        return consum;
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
}
