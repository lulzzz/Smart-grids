
package Model.Interfaces;

import Model.ApplianceState;
import Model.Moment;

public interface IAppliance 
{
    public double getConsum( Moment since, Moment until );
    public ApplianceState getState();
}
