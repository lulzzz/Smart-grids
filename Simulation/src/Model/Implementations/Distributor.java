
package Model.Implementations;

import Model.DataSets.DiscreteDataSet;
import Model.Interfaces.*;
import Model.Core.*;
import com.google.gson.annotations.*;
import seas3.core.Participant;


public class Distributor implements IDistributor
{
    private DiscreteDataSet rate;
    private IBid bid;
    @Expose
    private double currentRate;
    
    private static final double maxTrade = 100;
    
    public Distributor( DiscreteDataSet rate )
    {
        this.rate = rate;
    }
    
    @Override
    public void setStartingMoment(Moment moment) 
    {
        currentRate = rate.getValue(moment);
        bid = new LinearBid(currentRate, this);
    }

    public void develop( Moment since, Moment until ) 
    {
        bid.develop(until, -maxTrade, maxTrade, this, null);
    }

    @Override
    public double getRate( Moment moment ) 
    {
        return rate.getValue(moment);
    }

    @Override
    public Participant toParticipant(int id) 
    {
        return new Participant(id, bid.toPLV());
    }
}
