
package Model.Implementations;

import Model.Interfaces.*;
import Model.Core.Moment;
import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import seas3.core.*;

public final class LinearBid implements IBid 
{
    private double minPlot, maxPlot;
    
    private double linearFactor;
    
    public LinearBid(Double rate, IDistributor distributor) 
    {
        minPlot = -100;
        maxPlot = 100;
        
        linearFactor = rate;
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
    public void develop(Moment moment, double baseConsum, IBattery battery, IDistributor distributor, ArrayList<IAppliance> appliances) 
    {
        linearFactor = distributor.getRate(moment);
    }

    @Override
    public void writePlotData(String plotFile, PrintWriter writer) 
    {
        
    }

    @Override
    public void setTrades(HashMap<Double, TraderType> trades) 
    {
        
    }
    
}
