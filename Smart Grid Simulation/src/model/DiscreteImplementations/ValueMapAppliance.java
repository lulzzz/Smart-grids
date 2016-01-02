
package Model.DiscreteImplementations;

import Model.Interfaces.IAppliance;
import Model.Interfaces.IAppliance.ApplianceState;
import Model.Moment;
import com.google.gson.annotations.Expose;

public class ValueMapAppliance implements IAppliance 
{
    @Expose
    private ApplianceType type;
    @Expose
    private ApplianceState state;
    @Expose
    private double progress;
    
    private TimeValueMap consum;
    
    public ValueMapAppliance( ApplianceType type, TimeValueMap consum )
    {
        this.type = type;
        this.consum = consum;
        this.state = ApplianceState.Waiting;
        this.progress = 0;
    }
    @Override
    public double getConsum(Moment since, Moment until) 
    {
        progress = consum.getProgress(until);
        
        return consum.getAddedValue(since, until);
    }

    @Override
    public ApplianceState getState() 
    {
        return state;
    }

    @Override
    public Moment getStartingTime() 
    {
        return consum.getLowestMoment();
    }
}
