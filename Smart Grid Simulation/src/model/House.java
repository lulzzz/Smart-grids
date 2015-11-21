
package model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.function.DoubleUnaryOperator;
import seas3.core.IntervalLinearValuation;
import seas3.core.Participant;
import seas3.core.PiecewiseLinearValuation;

/**
 *
 * @author Martin
 */
public class House extends Prosumer
{
    public static final int plotResolution = 10;
    
    
    public double necessity;
    public Distributor distributor;
    public double baseConsum;
    public double batteryCapacity;
    
    public House(float[] position)
    {
        super(position);
        
        necessity = 5;
        baseConsum = 1;
        batteryCapacity = 10;
        
        updateBid(0);
    }
    
    public void updateBid( int frame )
    {
        bid = new Bid( baseConsum, baseConsum + batteryCapacity, buildCurve(frame)); 
    }

    @Override
    public Participant getParticipant(int frame) 
    {
        PiecewiseLinearValuation plv = bid.toPLV();
        
        return new Participant( id, plv );
    }
    
    public DoubleUnaryOperator buildCurve(int time)
    {
        return x -> Math.signum(x) * distributor.rate[time] * ( bid.contactX + necessity * Math.log( Math.abs(x) - bid.minX) / necessity + 1);
    }   
    
    
    
    @Override
    public void writePlotData( PrintWriter writer, String plotName, int time ) 
    {
        try 
        {
            writer.print(String.format(Locale.US,
                    
                "set output '%s.png' %n" +
                        
                "f(x) = x > %f ? %f*x : 1/0 %n"+
                        
                "g(x) = x > %f ? %f*(%f+%f*log((x-%f)/%f + 1)) : 1/0 %n"+
                
                "h(x) = (x > %f && x < %f) ? g(x) : 1/0 %n"+
                
                       
                "plot [%f:%f] f(x) with filledcurve y1=0, g(x) with filledcurve y1=0, h(x) with filledcurve y1=0",
               
                plotName, 
                bid.minX, distributor.rate[time],
                bid.minX, distributor.rate[time], bid.minX, time, bid.minX, necessity,
                bid.minX, bid.tradeX,
                bid.minX, bid.maxX
            ));
            writer.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	
    }
    
    
}
