
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
    
    
    public House(int id)
    {
        super(id);
        
        setTestParameters();
        
        develop(0);
    }
    
    public void setTestParameters()
    {
        necessity = new Random().nextInt(10) + 1;
        baseConsum = -2;
        battery = new Battery(10);
        distributorRate = 1;
    }
    
    @Override
    public void develop( int frame )
    {
        bid = new Bid( baseConsum - battery.getLevel(), baseConsum + battery.getRemainingSpace(), buildCurve(), 10);
    }
    
    private DoubleUnaryOperator buildCurve()
    {
        return x -> Math.signum(x) * distributorRate * ( bid.contactX + necessity * Math.log( (Math.abs(x) - bid.minX) / necessity + 1));
    }
    
    @Override
    public void writePlotData(PrintWriter writer, int frame)
    {
        String outputFileName = String.format("frame%did%d.png", frame, id);
        
        // Header
        writer.print(String.format("set output '%s' %n unset arrow %n", outputFileName));

        // Arrows
        for( double trade : bid.trades )
        {
            writer.print(String.format(Locale.US, 
                 "set arrow from %f, %f to %f,%f front %n",

                trade, 0.0, trade, bid.curve.applyAsDouble(trade)
            ));
        }

        // Function plots
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
