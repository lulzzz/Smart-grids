
package Model;

import Model.Interfaces.IAppliance;
import Model.Interfaces.IAppliance.ApplianceState;
import java.util.function.DoubleUnaryOperator;

public class FunctionalAppliance implements IAppliance 
{
    private DoubleUnaryOperator primitive;
    private ApplianceState state; 
    public Moment startingTime;
    public Moment endingTime;
    
    public FunctionalAppliance( DoubleUnaryOperator primitive, Moment start, Moment end )
    {
        this.primitive = primitive;
        this.state = ApplianceState.Waiting;
        this.startingTime = start;
        this.endingTime = end;
    }
    
    @Override
    public double getConsum(Moment since, Moment until) 
    {
        if( since.compareTo(startingTime) < 0 ) return 0;
        
        if( since.compareTo(endingTime) > 0 ) return 0;
        
        double elapsedTime = since.minutesSince(startingTime);
        double secondTime = elapsedTime + until.minutesSince(since);
        
        return primitive.applyAsDouble(secondTime) - primitive.applyAsDouble(elapsedTime);
    }

    @Override
    public ApplianceState getState() 
    {
        return state;
    }

    @Override
    public Moment getStartingTime() 
    {
        return new Moment(0,0);
    }
}
