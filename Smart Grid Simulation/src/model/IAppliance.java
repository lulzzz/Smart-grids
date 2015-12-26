
package Model;

public interface IAppliance 
{
    public double getConsum( Moment since, Moment until );
    public ApplianceState getState();
}
