
package Model;

import com.google.gson.annotations.Expose;

class Weather 
{
    @Expose
    private int cloudPercentage;
    @Expose
    private double windSpeed;
    
    public void setStartingMoment( Moment moment )
    {
        cloudPercentage = 1;
        windSpeed = 5;
    }
}
