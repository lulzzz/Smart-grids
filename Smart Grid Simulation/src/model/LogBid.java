
package Model;

import Model.Interfaces.*;
import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.*;
import seas3.core.*;

public class LogBid implements IBid 
{
    private double minX, maxX, contactX, minPlot, maxPlot;
    
    private double linearFactor, tightness;
    
    private DoubleUnaryOperator curve; // funcion que va cambiando con el tiempo
    
    private static final int resolution = 10;
    
    private ArrayList<Double> trades;
    
    @Expose
    private String plotFile; 
    
    public LogBid(Moment moment, double baseConsum, IBattery battery, IDistributor distributor, ArrayList<IAppliance> appliances)
    {
        minPlot = -battery.getCapacity();
        maxPlot = battery.getCapacity();
        
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
    
    @Override
    public void setTrades( ArrayList<Double> trades )
    {
        this.trades = trades;
    }

    @Override
    public void writePlotData(String plotFile, PrintWriter writer) 
    {
        this.plotFile = plotFile;
        // Header
        writer.print(String.format("set output '%s' %nunset arrow %n", plotFile));
        
        // Define functions plots
        writer.print(String.format(Locale.US, 

            "f(x) = x >= %.2f && x <= %.2f ? %.2f*x : 1/0 %n"+ // f is the linear function

            "g(x) = x >= %.2f && x <= %.2f ? sgn(x)*%.2f*(%.2f+%.2f*log((abs(x)-%.2f)/%.2f + 1)) : 1/0 %n", // g is the approximation
            
            minX, maxX, linearFactor,
            minX, maxX, linearFactor, contactX, tightness, contactX, tightness
        ));

        // Define arrows
        for( double trade : trades )
        {
            writer.print(String.format(Locale.US, 
                 "set arrow from %.2f, 0 to %.2f,g(%.2f) front %n",

                trade, trade, trade
            ));
        }
        
        // Set samples
        writer.println(String.format(Locale.US, "set samples 10000 %nset nokey %nset xzeroaxis %nset yzeroaxis"));
        
        // Plot
        
        writer.println(String.format(Locale.US,
                "plot [%.2f:%.2f][%.2f:%.2f] f(x) lt rgb \"#8E7DFA\" with filledcurve y1=0, g(x) lt rgb \"#ACC6f2\" with filledcurve y1=0 %n%n",
                minPlot, maxPlot, minPlot*linearFactor, maxPlot*linearFactor));
    }

    @Override
    public final void develop( Moment moment, double baseConsum, IBattery battery, IDistributor distributor, ArrayList<IAppliance> appliances) 
    {
        minX = baseConsum - battery.getLevel();
        maxX = baseConsum + battery.getCapacityLeft();
        
              if( minX > 0 && maxX > 0 ) contactX = minX;
        else if( minX < 0 && maxX < 0 ) contactX = maxX;
        else                                        contactX = 0;
        
        linearFactor = distributor.getRate();
        tightness = 5; // Will depend on appliances
        
        curve = x -> Math.signum(x) * linearFactor * ( contactX + tightness * Math.log( (Math.abs(x) - minX) / tightness + 1));
    }
}
