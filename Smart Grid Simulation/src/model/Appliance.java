
package Model;

import java.util.function.DoubleUnaryOperator;

public class Appliance 
{
    public DoubleUnaryOperator curve;
    public DoubleUnaryOperator primitive;
    public Moment startingTime;
    public Moment endingTime;
    
    public double getCost(Moment moment)
    {
        if( moment.compareTo(startingTime) < 0 ) return 0;
        
        if( moment.compareTo(endingTime) > 0 ) return 0;
        
        double elapsedTime = moment.minutesSince(startingTime);
        
        //return primitive.applyAsDouble(elapsedTime);
        
        return 0;
    }
}
