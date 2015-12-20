
package model;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.DoubleUnaryOperator;
import seas3.core.*;

public class House extends Prosumer
{
    public Distributor distributor;
    
    public double necessity;
    public double baseConsum;
    public double batteryCapacity;
    public double distributorRate;
    
    
    public House(int id)
    {
        super(id);
        
        necessity = new Random().nextInt(10) + 1;
        baseConsum = 1;
        batteryCapacity = 10;
        
        
        //Participant participant = new Participant( id, plv);
        
        System.out.println(participant);
        
        //return participant;
        
    }
    
    @Override
    public void updateFrame( int frame )
    {
        bid = new Bid( baseConsum, baseConsum + batteryCapacity, buildCurve(frame), 10); 
        distributorRate = distributor.rate[frame];
        participant = new Participant(id, bid.toPLV());
    }
    
    private DoubleUnaryOperator buildCurve(int time)
    {
        return x -> Math.signum(x) * distributor.rate[time] * ( bid.contactX + necessity * Math.log( (Math.abs(x) - bid.minX) / necessity + 1));
    }
    
    @Override
    public void writePlotData( PrintWriter writer ) 
    {
        writer.print(String.format("set output '%d.png' %n unset arrow %n", id));
        
        for( double trade : bid.trades )
        {
            writer.print(String.format(Locale.US, 
                 "set arrow from %f, %f to %f,%f front %n",
                 
                trade, 0.0, trade, bid.curve.applyAsDouble(trade)
            ));
        }
        
        
        
        writer.print(String.format(Locale.US, 

            "f(x) = x > %f ? %f*x : 1/0 %n"+

            "g(x) = x > %f ? sgn(x)*%f*(%f+%f*log((abs(x)-%f)/%f + 1)) : 1/0 %n"+
                    
            "plot [%f:%f][0:] f(x) with filledcurve y1=0, g(x) with filledcurve y1=0 %n%n",

            bid.minX, distributorRate,
            bid.minX, distributorRate, bid.contactX, necessity, bid.contactX, necessity,
            bid.minX, bid.maxX
        ));
    } 

    @Override
    public void processResults(Assignment assignment) 
    {
        for(Link l : participant.getInLinks())
        {
            double trade = Math.abs(assignment.get(l));
            bid.addTrade(trade);
        }
        
        for(Link l : participant.getOutLinks())
        {
            double trade = Math.abs(assignment.get(l));
            bid.addTrade(trade);
        }
        
        
    }
}
