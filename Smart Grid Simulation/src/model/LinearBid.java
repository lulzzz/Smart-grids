
package Model;

import Model.Interfaces.*;
import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.DoubleUnaryOperator;
import seas3.core.*;

public final class LinearBid implements IBid 
{
    private double minPlot, maxPlot;
    
    private double linearFactor;
    
    @Expose
    private String plotFile;
    
    private ArrayList<Double> trades;
    
    public LinearBid(Moment moment, IDistributor distributor) 
    {
        minPlot = -100;
        maxPlot = 100;
        trades = new ArrayList<>();
        
        develop(moment, moment,0,null,distributor,null);
    }

    @Override
    public PiecewiseLinearValuation toPLV() 
    {
        PiecewiseLinearValuation plv = new PiecewiseLinearValuation();
        
        IntervalLinearValuation ilv = new IntervalLinearValuation(minPlot, maxPlot, linearFactor, 0);
        
        plv.add(ilv);
        
        return plv;
    }

    @Override
    public void develop(Moment since, Moment until, double baseConsum, IBattery battery, IDistributor distributor, ArrayList<IAppliance> appliances) 
    {
        linearFactor = distributor.getRate(since, until);
    }

    @Override
    public void writePlotData(String plotFile, PrintWriter writer) 
    {
        this.plotFile = plotFile;
        // Header
        writer.print(String.format("set output '%s' %nunset arrow %n", plotFile));
        
        // Define functions plots
        writer.print(String.format(Locale.US, 

            "f(x) = %.2f*x%n",
            
            linearFactor
        ));

        // Define arrows
        for( double trade : trades )
        {
            writer.print(String.format(Locale.US, 
                 "set arrow from %.2f, 0 to %.2f,f(%.2f) front %n",

                trade, trade, trade
            ));
        }
        
        // Set style
        writer.println(String.format(Locale.US, "set samples 10000 %nset nokey %nset xzeroaxis %nset yzeroaxis"));
        
        // Plot
        writer.println(String.format(Locale.US,
                "plot [%.2f:%.2f] f(x) lt rgb \"#8E7DFA\" with filledcurve y1=0%n%n",
                minPlot, maxPlot));
    }

    @Override
    public void setTrades(ArrayList<Double> trades) 
    {
        
    }
    
}
