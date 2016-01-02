
package Model.DiscreteImplementations;

import Model.Interfaces.*;
import Model.LinearBid;
import Model.Moment;
import com.google.gson.annotations.*;
import java.io.*;


public class ValueMapDistributor implements IDistributor
{
    @Expose
    private TimeValueMap rate;
    @Expose
    private IBid bid;
    
    public ValueMapDistributor( TimeValueMap rate )
    {
        this.rate = rate;
    }
    
    public void setStartingMoment(Moment moment) 
    {
        rate.getLowerNearest(moment);
        this.bid = new LinearBid(moment, this);
    }

    public void develop( Moment since, Moment until ) 
    {
        bid.develop(since, until, 0, null, this, null);
    }

    public void writePlotData(PrintWriter writer, String outputFolder, Moment moment) 
    {
        String plotFile = String.format("%s\\distributor moment %s.png", outputFolder, moment.toString());
        
        bid.writePlotData(plotFile, writer);
    }

    @Override
    public double getRate(Moment since, Moment until) 
    {
        return 1;
    }
}
