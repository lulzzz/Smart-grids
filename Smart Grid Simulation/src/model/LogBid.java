
package Model;

import Model.Interfaces.IBattery;
import Model.Interfaces.IAppliance;
import Model.Interfaces.IBid;
import Model.Interfaces.IDistributor;
import com.google.gson.annotations.*;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.util.function.*;
import seas3.core.*;

public class LogBid implements IBid 
{
    private double minX, maxX, contactX;
    
    private double linearFactor, tightness;
    
    public DoubleUnaryOperator curve; // funcion que va cambiando con el tiempo
    
    private static final int resolution = 10;
    
    public ArrayList<Double> trades;
    
    public LogBid(Moment moment, double baseConsum, IBattery battery, IDistributor distributor, ArrayList<IAppliance> appliances)
    {
        trades = new ArrayList<>();
        develop( moment, baseConsum, battery, distributor, appliances );
    }
    
    public PiecewiseLinearValuation toPLV()
    {
        PiecewiseLinearValuation plv = new PiecewiseLinearValuation();
        
        
        // Build plv by concatenating ilv's
        // The ilv's are built linearizing the curve function
        
        double x0,x1,y0,y1,slope,intercept;
        
        double step = (maxX - minX) / resolution;
        
        for( int i = 0; i < resolution; i++ )
        {
            x0 = minX + i*step;
            x1 = x0 + step;
            
            y0 = curve.applyAsDouble(x0 );
            y1 = curve.applyAsDouble(x1 );
            
            slope = (y1-y0)/(x1-x0);
            intercept = y0 - slope*x0;
            
            IntervalLinearValuation ilv = new IntervalLinearValuation(x0, x1, slope, intercept);
            
            plv.add(ilv);
        }
        
        return plv;
    }
    
    public void addTrade( double at )
    {
        trades.add(at);
    }

    @Override
    public void writePlotData(PrintWriter writer) 
    {
        
        
        // Define functions plots
        writer.print(String.format(Locale.US, 

            "f(x) = %.2f*x %n"+ // f is the linear function

            "g(x) = sgn(x)*%.2f*(%.2f+%.2f*log((abs(x)-%.2f)/%.2f + 1)) %n", // g is the approximation
            
            linearFactor,
            linearFactor, contactX, tightness, contactX, tightness
        ));

        // Define arrows
        for( double trade : trades )
        {
            writer.print(String.format(Locale.US, 
                 "set arrow from %f, 0 to %f,g(%f) front %n",

                trade, trade, trade
            ));
        }
        
        // Plot
        
        writer.println(String.format(Locale.US,
                "plot [%.2f:%.2f] f(x) with filledcurve y1=0, g(x) with filledcurve y1=0 %n%n",
                minX, maxX));
    }

    @Override
    public final void develop( Moment moment, double baseConsum, IBattery battery, IDistributor distributor, ArrayList<IAppliance> appliances) 
    {
        minX = baseConsum - battery.getLevel();
        maxX = baseConsum + battery.getCapacityLeft();
        
              if( minX > 0 && maxX > 0 ) contactX = minX;
        else if( minX < 0 && maxX < 0 ) contactX = maxX;
        else                                        contactX = 0;
        
        linearFactor = distributor.getPrice(moment);
        tightness = 5; // Will depend on appliances
        
        curve = x -> Math.signum(x) * linearFactor * ( contactX + tightness * Math.log( (Math.abs(x) - minX) / tightness + 1));
    }
}
