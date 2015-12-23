
package Model;

import java.io.*;
import java.util.*;
import java.util.function.*;
import seas3.core.*;

public class House extends Prosumer
{    
    public double necessity;
    public double baseConsum;
    
    public Battery battery;
    public double distributorRate;
    public double batteryDelta;
    
    
    public House(int id)
    {
        super(id);
        
        setTestParameters();
        
        develop(0);
    }
    
    public void setTestParameters()
    {
        necessity = new Random().nextInt(10) + 1;
        baseConsum = 1;
        battery = new Battery(3,10);
        distributorRate = 1;
    }
    
    @Override
    public void develop( int frame )
    {
        battery.changeLevel(batteryDelta);
        bid = new Bid( baseConsum - battery.getLevel(), baseConsum + battery.getRemainingSpace(), buildCurve(), 10);
    }
    
    private DoubleUnaryOperator buildCurve()
    {
        return x -> Math.signum(x) * distributorRate * ( bid.contactX + necessity * Math.log( (Math.abs(x) - bid.minX) / necessity + 1));
    }
    
    @Override
    public void writePlotData(PrintWriter writer, String outputFolder, int frame)
    {
        String outputFileName = String.format("%s\\id %d frame %d.png", outputFolder, id, frame);
        
        // Header
        writer.print(String.format("set output '%s' %n unset arrow %n", outputFileName));
        
        // Define functions plots
        writer.print(String.format(Locale.US, 

            "f(x) = %.2f*x %n"+ // f is the linear function

            "g(x) = sgn(x)*%.2f*(%.2f+%.2f*log((abs(x)-%.2f)/%.2f + 1)) %n", // g is the approximation
            
            distributorRate,
            distributorRate, bid.contactX, necessity, bid.contactX, necessity
        ));

        // Define arrows
        for( double trade : bid.trades )
        {
            writer.print(String.format(Locale.US, 
                 "set arrow from %f, 0 to %f,g(%f) front %n",

                trade, trade, trade
            ));
        }
        
        // Plot
        
        writer.println(String.format(Locale.US,
                "plot [%.2f:%.2f] f(x) with filledcurve y1=0, g(x) with filledcurve y1=0 %n%n",
                bid.minX, bid.maxX));
    }    

    @Override
    public void applyTrades(Assignment assignment) 
    {
        batteryDelta = 0;
        for(Link link : assignment.keySet())
        {
            if(link.source.getId() == id && link.dest.getId() != id)
            {
                double value = assignment.get(link);
                bid.addTrade(value);
                batteryDelta += value;
            }
            else if(link.dest.getId() == id && link.source.getId() != id)
            {
                double value = assignment.get(link);
                bid.addTrade(-value);
                batteryDelta -= value;
            }            
        }
    }
}