
package Model.Core;

import Model.TemporalDistributions.DiscreteTemporalDistribution;
import com.google.gson.annotations.Expose;
import Model.TemporalDistributions.ITemporalDistribution;

public class Weather 
{
    @Expose
    private double cloudPercentage;
    @Expose
    private double windSpeed;
    
    private ITemporalDistribution clouds;
    private ITemporalDistribution winds;
    
    public Weather( DiscreteTemporalDistribution clouds, DiscreteTemporalDistribution winds )
    {
        this.clouds = clouds;
        this.winds = winds;
    }
    
    public void setStartingMoment( Moment moment )
    {
        cloudPercentage = clouds.getValue(moment);
        windSpeed = winds.getValue(moment);
    }

    public double getClouds(Moment moment) 
    {
        return clouds.getValue(moment);
    }

    public double getMeanCloudsBetween(Moment since, Moment until) 
    {
        return clouds.getMeanBetween(since, until);
    }

    void develop(Moment moment) 
    {
        cloudPercentage = clouds.getValue(moment);
        windSpeed = winds.getValue(moment);
    }

    public double getWindSpeed(Moment moment) 
    {
        return windSpeed;
    }

    public double getMeanWindBetween(Moment since, Moment until) 
    {
        return winds.getMeanBetween(since, until);
    }
}
