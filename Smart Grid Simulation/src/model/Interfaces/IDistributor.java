
package Model.Interfaces;

import Model.Moment;

public interface IDistributor 
{
    public double getRate(Moment since, Moment until);
}
