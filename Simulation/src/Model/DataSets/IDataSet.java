
package Model.DataSets;

import Model.Core.Moment;

public interface IDataSet 
{
    public Double getValue(Moment moment);
    public double getMeanBetween(Moment since, Moment until);
    public double getAddedValue(Moment since, Moment until);
    public double getProgress(Moment moment);
}
