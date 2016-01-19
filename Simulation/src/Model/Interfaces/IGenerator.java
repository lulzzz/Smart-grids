
package Model.Interfaces;

import Model.Core.*;

public interface IGenerator 
{
    public void setStartingMoment( Moment moment, Weather weather );
    public double getGeneration( Moment since, Moment until, Weather weather );
    
    public enum GeneratorType
    {
        Solar, Eolic
    }
}
