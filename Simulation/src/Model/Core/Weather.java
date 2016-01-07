
package Model.Core;

import Model.DataSets.IDataSet;
import Model.DataSets.DiscreteDataSet;
import com.google.gson.annotations.Expose;

public class Weather 
{
    @Expose
    private double cloudPercentage;
    @Expose
    private double windSpeed;
    
    private IDataSet clouds;
    private IDataSet winds;
    
    public Weather( DiscreteDataSet clouds, DiscreteDataSet winds )
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
