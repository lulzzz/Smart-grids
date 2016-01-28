
package Model.Interfaces;

import Model.Core.*;

public interface IGenerator 
{    
    // Devuelve la energía producida entre los momentos preveídos teniendo en cuenta la meteorología
    public double getGeneration( Moment since, Moment until, Weather weather );
    
    
}
