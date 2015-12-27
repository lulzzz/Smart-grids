
package Model.Interfaces;

import Model.Moment;

public interface IGenerator 
{
    public double getGeneration( Moment since, Moment until );
}
