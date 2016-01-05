
package Model.DataSets;

import Model.Core.Moment;
import java.util.function.DoubleUnaryOperator;

public class ContinuousDataSet implements IDataSet
{
    private DoubleUnaryOperator function;
    private DoubleUnaryOperator primitive;
    private Moment startingMoment;
    private Moment endingMoment;
    
    public ContinuousDataSet( DoubleUnaryOperator function, DoubleUnaryOperator primitive, Moment startingMoment, Moment endingMoment )
    {
        this.function = function;
        this.primitive = primitive;
        this.startingMoment = startingMoment;
        this.endingMoment = endingMoment;
    }

    @Override
    public Double getValue(Moment moment) 
    {
        return function.applyAsDouble(moment.minutesSince(startingMoment));
    }

    @Override
    public double getMeanBetween(Moment since, Moment until) 
    {
        int minutesSince = since.minutesSince(startingMoment);
        int minutesUntil = until.minutesSince(startingMoment);
        
        double added = primitive.applyAsDouble(minutesUntil) - primitive.applyAsDouble(minutesSince);
        
        return added / ( minutesUntil - minutesSince );
    }

    @Override
    public double getAddedValue(Moment since, Moment until) 
    {
        int minutesSince = since.minutesSince(startingMoment);
        int minutesUntil = until.minutesSince(startingMoment);
        
        double added = primitive.applyAsDouble(minutesUntil) - primitive.applyAsDouble(minutesSince);
        
        return added;
    }

    @Override
    public double getProgress(Moment moment) 
    {
        int partialMinutes = moment.minutesSince(startingMoment);
        int totalMinutes = endingMoment.minutesSince(startingMoment);
        
        return partialMinutes / (double) totalMinutes;
    }
}
