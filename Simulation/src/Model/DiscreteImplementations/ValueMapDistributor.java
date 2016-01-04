
package Model.DiscreteImplementations;

import Model.Interfaces.*;
import Model.ContinuousImplementations.*;
import Model.Core.*;
import com.google.gson.annotations.*;
import seas3.core.Participant;


public class ValueMapDistributor implements IDistributor
{
    private TimeValueMap rate;
    private IBid bid;
    @Expose
    private double currentRate;
    
    public ValueMapDistributor( TimeValueMap rate )
    {
        this.rate = rate;
    }
    
    @Override
    public void setStartingMoment(Moment moment) 
    {
        Moment m = rate.getLowerNearest(moment);
        currentRate = rate.get(m);
        bid = new LinearBid(currentRate, this);
    }

    public void develop( Moment since, Moment until ) 
    {
        bid.develop(until, 0, null, this, null);
    }

    @Override
    public double getRate( Moment moment ) 
    {
        return rate.getLowerNearestValue(moment);
    }

    @Override
    public Participant toParticipant(int id) 
    {
        return new Participant(id, bid.toPLV());
    }
}
