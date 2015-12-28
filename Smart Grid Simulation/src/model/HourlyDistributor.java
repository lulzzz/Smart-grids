
package Model;

import Model.Interfaces.IDistributor;
import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import java.util.ArrayList;
import seas3.core.Assignment;
import seas3.core.Link;


public class HourlyDistributor extends Prosumer implements IDistributor
{
    public double[] rate;
    @Expose
    public double currentRate;
    
    public HourlyDistributor( int id, double[] rate )
    {
        super(id);
        
        this.rate = rate;
    }

    @Override
    public void develop( Moment since, Moment until ) 
    {
        currentRate = rate[until.getHour()];
    }

    @Override
    public void applyTrades(Assignment assignment) 
    {
        ArrayList<Double> trades = new ArrayList<>();
        
        for(Link link : assignment.keySet())
        {
            if(link.source.getId() == id && link.dest.getId() != id)
            {
                double value = assignment.get(link);
                trades.add(-value);
            }
            else if(link.dest.getId() == id && link.source.getId() != id)
            {
                double value = assignment.get(link);
                trades.add(value);
            }            
        }
        
        bid.setTrades(trades);
    }

    @Override
    public void writePlotData(PrintWriter writer, String outputFolder, Moment moment) 
    {
        String plotFile = String.format("%s\\id %d moment %s.png", outputFolder, id, moment.toString());
        
        bid.writePlotData(plotFile, writer);
    }

    @Override
    public double getRate() 
    {
        return currentRate;
    }

    @Override
    public void setStartingMoment(Moment moment) 
    {
        this.currentRate = rate[moment.getHour()];
        this.bid = new LinearBid(moment, this);
    }
}
