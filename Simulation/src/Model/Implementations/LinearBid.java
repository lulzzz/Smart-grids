
package Model.Implementations;

import Model.Core.Distributor;
import Model.Interfaces.*;
import Model.Core.Moment;
import Model.Core.Weather;
import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import seas3.core.*;

public final class LinearBid implements IBiddingStrategy 
{
    private double minPlot, maxPlot;
    
    private double linearFactor;
    
    public LinearBid(Double rate, Distributor distributor) 
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

    public void setBid(Moment moment, double minX, double maxX, Distributor distributor, ArrayList<IAppliance> appliances) 
    {
        linearFactor = distributor.getRate(moment);
    }

    @Override
    public void writePlotData(String plotFile, PrintWriter writer) 
    {
        
    }

    @Override
    public void processResults(HashMap<Double, TraderType> trades) 
    {
        
    }

    @Override
    public void setBid(Moment moment, Distributor distributor, Weather weather, IBattery battery, ArrayList<IGenerator> generators, ArrayList<IAppliance> appliances) 
    {
        linearFactor = distributor.getRate(moment);
    }
    
}
