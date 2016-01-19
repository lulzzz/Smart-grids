
package Model.TemporalDistributions;

import Model.Core.Moment;

public interface ITemporalDistribution 
{
    public Double getValue(Moment moment);
    public double getMeanBetween(Moment since, Moment until);
    public double getAddedValue(Moment since, Moment until);
    public double getProgress(Moment moment);
}
