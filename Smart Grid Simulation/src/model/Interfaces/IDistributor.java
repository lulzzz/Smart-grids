
package Model.Interfaces;

import Model.Core.Moment;

public interface IDistributor 
{
    public void setStartingMoment(Moment moment);

    public double getRate(Moment moment);
}
