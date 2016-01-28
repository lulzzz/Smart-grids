
package Model.Core;

import Model.TemporalDistributions.DiscreteTemporalDistribution;
import Model.Interfaces.*;
import Model.Core.*;
import Model.Implementations.LinearBid;
import com.google.gson.annotations.*;
import seas3.core.Participant;


public class Distributor
{
    private DiscreteTemporalDistribution rate;
    private IBiddingStrategy bid;
    @Expose
    private double currentRate;
    
    private static final double maxTrade = 100;
    
    public Distributor( DiscreteTemporalDistribution rate )
    {
        this.rate = rate;
    }
    
    public void setStartingMoment(Moment moment) 
    {
        currentRate = rate.getValue(moment);
        bid = new LinearBid(currentRate, this);
    }

    public void develop( Moment since, Moment until ) 
    {
        bid.setBid(until, -maxTrade, maxTrade, this, null);
    }

    public double getRate( Moment moment ) 
    {
        return rate.getValue(moment);
    }

    public Participant toParticipant(int id) 
    {
        return new Participant(id, bid.toPLV());
    }
}
