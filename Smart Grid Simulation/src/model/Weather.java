
package model;

import com.google.gson.annotations.Expose;
import java.util.Random;

public class Weather 
{
    @Expose
    private double clouds;
    @Expose
    private double wind;
    @Expose
    private double water;
    
    public Weather()
    {
        this.clouds = 5;
        this.wind = 10;
        this.water = 2;
    }
    
    public double getSunEnergy()
    {
        return 1/clouds;
    }
    
    public double getWindEnergy()
    {
        return wind;
    }
    
    public double getWaterEnergy()
    {
        return water;
    }
    
    public void develop()
    {
        Random random = new Random();
        clouds += random.nextGaussian();
        wind += random.nextGaussian();
        water += random.nextGaussian();
    }
}
