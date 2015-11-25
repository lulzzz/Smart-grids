
package model;

public class SolarPanels extends Generator
{
    @Override
    public double getGenerated(Weather weather) 
    {
        return weather.getSunEnergy();
    }
    
}
