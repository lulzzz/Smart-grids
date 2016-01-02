
package Model;

import Model.Interfaces.*;
import com.google.gson.annotations.*;
import java.io.*;
import java.util.*;
import seas3.core.*;


public class HourlyDistributor implements IDistributor
{
    private double[] rate;
    @Expose
    private double currentRate;
    @Expose
    private IBid bid;
    
    public HourlyDistributor( double[] rate )
    {
        this.rate = rate;
    }
    
    public void setStartingMoment(Moment moment) 
    {
        this.currentRate = rate[moment.getHour()];
        this.bid = new LinearBid(moment, this);
    }

    public void develop( Moment since, Moment until ) 
    {
        currentRate = rate[until.getHour()];
    }

    public void applyTrades(Assignment assignment) 
    {
    }

    public void writePlotData(PrintWriter writer, String outputFolder, Moment moment) 
    {
        String plotFile = String.format("%s\\distributor moment %s.png", outputFolder, moment.toString());
        
        bid.writePlotData(plotFile, writer);
    }

    @Override
    public double getRate(Moment since, Moment until) 
    {
        return currentRate;
    }
}
