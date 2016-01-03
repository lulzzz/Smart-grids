
package Model.Interfaces;

import Model.Core.Moment;

public interface IAppliance 
{
    public void setStartingMoment( Moment moment );
    public double getConsum( Moment since, Moment until );
    
    public ApplianceState getState();

    public Moment getStartingTime();
    
    public enum ApplianceState 
    {
        Waiting, inExecution, Ended
    }

    public enum ApplianceType
    {
        TV,Cooking, WashingMachine
    }
}
