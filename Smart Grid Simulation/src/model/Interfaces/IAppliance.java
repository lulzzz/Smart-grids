
package Model.Interfaces;

import Model.Moment;

public interface IAppliance 
{
    public double getConsum( Moment since, Moment until );
    public ApplianceState getState();
    
    public enum ApplianceState 
    {
        Waiting, inExecution, Ended
    }

    public enum ApplianceType
    {
        TV,Cooking, WashingMachine
    }
}
