
package Model.Implementations;

import Model.TemporalDistributions.DiscreteTemporalDistribution;
import Model.Interfaces.IAppliance;
import Model.Interfaces.IAppliance.ApplianceState;
import Model.Core.Moment;
import com.google.gson.annotations.Expose;

public class Appliance implements IAppliance 
{
    public enum ApplianceType
    {
        TV,Cooking, WashingMachine
    }
    @Expose
    private ApplianceType type;
    @Expose
    private ApplianceState state;
    @Expose
    private double progress;
    
    private DiscreteTemporalDistribution consum;
    
    public Appliance( ApplianceType type, DiscreteTemporalDistribution consum )
    {
        this.type = type;
        this.consum = consum;
        this.state = ApplianceState.Waiting;
        this.progress = 0;
    }
    
    public void setStartingMoment(Moment moment) 
    {
        progress = consum.getProgress(moment);
        
        if( progress == 0 ) state = ApplianceState.Waiting;
        else if( progress > 0 && progress < 1 ) state = ApplianceState.inExecution;
        else state = ApplianceState.Ended;
    }
    
    @Override
    public double getConsum(Moment since, Moment until) 
    {
        progress = consum.getProgress(until);
        
        if( progress > 0 && progress < 1 ) state = ApplianceState.inExecution;
        else state = ApplianceState.Ended;
        
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
        return consum.firstKey();
    }
}
