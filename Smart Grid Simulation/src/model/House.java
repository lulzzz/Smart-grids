
package model;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;
import seas3.core.Participant;
import seas3.core.PiecewiseLinearValuation;

public class House extends Prosumer
{
    public Distributor distributor;
    
    public double necessity;
    public double baseConsum;
    public double batteryCapacity;
    public double distributorRate;
    
    public House(float[] position, Distributor distributor)
    {
        super(position);
        
        this.distributor = distributor;
        
        necessity = new Random().nextInt(10) + 1;
        baseConsum = 1;
        batteryCapacity = 10;
    }
    
    @Override
    public void updateFrame( int frame )
    {
        bid = new Bid( baseConsum, baseConsum + batteryCapacity, buildCurve(frame), 10); 
        distributorRate = distributor.rate[frame];
    }
    
    private DoubleUnaryOperator buildCurve(int time)
    {
        return x -> Math.signum(x) * distributor.rate[time] * ( bid.contactX + necessity * Math.log( (Math.abs(x) - bid.minX) / necessity + 1));
    }
    
    @Override
    public void writePlotData( PrintWriter writer ) 
    {
        writer.print(String.format("set output '%d.png' %n", id));
        
        writer.print(String.format(Locale.US, 
                 "set arrow %d from %f, %f to %f,%f front %n",
                 
                1, bid.trades.get(0), 0.0, bid.trades.get(0), bid.curve.applyAsDouble(bid.trades.get(0))
        ));
        
        writer.print(String.format(Locale.US, 

            "f(x) = x > %f ? %f*x : 1/0 %n"+

            "g(x) = x > %f ? sgn(x)*%f*(%f+%f*log((abs(x)-%f)/%f + 1)) : 1/0 %n"+
                    
            "plot [%f:%f][0:] f(x) with filledcurve y1=0, g(x) with filledcurve y1=0 %n%n",

            bid.minX, distributorRate,
            bid.minX, distributorRate, bid.contactX, necessity, bid.contactX, necessity,
            bid.minX, bid.maxX
        ));
    } 
}
