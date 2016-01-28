
package Model.Implementations;

import Model.Core.Distributor;
import Model.Interfaces.*;
import Model.Core.Moment;
import Model.Core.Weather;
import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.*;
import seas3.core.*;

public class LogBid implements IBiddingStrategy 
{
    private double minX, maxX, contactX, minPlot, maxPlot;
    
    private double linearFactor, tightness;
    
    private DoubleUnaryOperator curve; // funcion que va cambiando con el tiempo
    
    private static final int resolution = 10;
    
    private HashMap<Double, TraderType> trades;
    
    @Expose
    private String plotFile; 
    
    public LogBid(Moment moment, double minX, double maxX, Distributor distributor, ArrayList<IAppliance> appliances)
    {
        minPlot = -10;
        maxPlot = 20;
        
        trades = new HashMap<>();
        setBid( moment, minX, maxX, distributor, appliances );
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
    public void processResults( HashMap<Double, TraderType> trades )
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
        for( Map.Entry<Double,TraderType> trade : trades.entrySet() )
        {
            double traded = trade.getKey();
            // To remove after big error solved
            if( traded < minX || traded > maxX ) continue;
            
            if( trade.getValue() == TraderType.Distributor)
            {
                writer.print(String.format(Locale.US, 
                 "set arrow from %.2f, 0 to %.2f,f(%.2f) front %n",
                traded, traded, traded
                ));
            }
            else if( trade.getValue() == TraderType.House )
            {
                writer.print(String.format(Locale.US, 
                 "set arrow from %.2f, 0 to %.2f,g(%.2f) front %n",
                traded, traded, traded
                ));
            }
            
        }
        
        // Set samples
        writer.println(String.format(Locale.US, "set samples 10000 %nset nokey %nset xzeroaxis %nset yzeroaxis"));
        
        // Plot
        
        writer.println(String.format(Locale.US,
                "plot [%.2f:%.2f][%.2f:%.2f] f(x) lt rgb \"#8E7DFA\" with filledcurve y1=0, g(x) lt rgb \"#ACC6f2\" with filledcurve y1=0 %n%n",
                minPlot, maxPlot, minPlot*linearFactor, maxPlot*linearFactor));
    }

    @Override
    public final void setBid( Moment moment, double minX, double maxX, Distributor distributor, ArrayList<IAppliance> appliances) 
    {
        this.minX = minX;
        this.maxX = maxX;
        
              if( minX > 0 && maxX > 0 ) contactX = minX;
        else if( minX < 0 && maxX < 0 ) contactX = maxX;
        else                                        contactX = 0;
        
        linearFactor = distributor.getRate(moment);
        // set tightness
        tightness = 1;
        int minutes;
        for( IAppliance appliance : appliances )
        {
            if( appliance.getState() == IAppliance.ApplianceState.Waiting )
            {
                minutes = appliance.getStartingTime().minutesSince(moment);
                if( minutes != 0)
                    tightness += linearFactor*10/(minutes*1.0);
            }
        }
        
        // Update the curve
        curve = x -> Math.signum(x) * linearFactor * ( contactX + tightness * Math.log( (Math.abs(x) - minX) / tightness + 1));
    }

    @Override
    public void setBid(Moment moment, Distributor distributor, Weather weather, IBattery battery, ArrayList<IGenerator> generators, ArrayList<IAppliance> appliances) 
    {
        // Predict from now to 5 seconds later 
        Moment next = new Moment(moment.getHour(), moment.getMinute());
        next.advance(5);
        
        // Generation
        double expectedGeneration = 0;
        for( IGenerator generator : generators )
            expectedGeneration += generator.getGeneration(moment, next, weather);
        
        // Consum
        double expectedConsum = 0;
        for(IAppliance appliance : appliances )
            expectedConsum += appliance.getConsum(moment, next);
        
        // Expected
        double expected = expectedGeneration - expectedConsum;
        
        // Domain
        double lower = battery.getLevel() - expected;
        double upper = lower + battery.getCapacity();
        
        // Rate
        double rate = distributor.getRate(moment);
        
        // Necessity
        double necessity = 0;
        for(IAppliance appliance : appliances)
        {
            if( appliance.getState() == IAppliance.ApplianceState.Waiting )
            {
                int minutes = appliance.getStartingTime().minutesSince(moment);
                if( minutes != 0 )
                    necessity += rate / minutes;
            }
        }
    }
}
