
package Model.Core;

import Model.DiscreteImplementations.TimeValueMap;
import com.google.gson.annotations.Expose;

public class Weather 
{
    @Expose
    private double cloudPercentage;
    @Expose
    private double windSpeed;
    
    private TimeValueMap clouds;
    private TimeValueMap winds;
    
    public Weather( TimeValueMap clouds, TimeValueMap winds )
    {
        this.clouds = clouds;
        this.winds = winds;
    }
    
    public void setStartingMoment( Moment moment )
    {
        cloudPercentage = clouds.getLowerNearestValue(moment);
        windSpeed = winds.getLowerNearestValue(moment);
    }

    public double getClouds(Moment moment) 
    {
        return clouds.getLowerNearestValue(moment);
    }

    public double getMeanCloudsBetween(Moment since, Moment until) 
    {
        return clouds.getMeanBetween(since, until);
    }

    void develop(Moment moment) 
    {
        cloudPercentage = clouds.getLowerNearestValue(moment);
        windSpeed = winds.getLowerNearestValue(moment);
    }
}
