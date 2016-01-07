
package Model.Implementations;

import Model.Interfaces.IBattery;
import com.google.gson.annotations.Expose;

public class Battery implements IBattery
{
    @Expose
    private double level;
    @Expose
    private double capacity;
    
    public Battery( double level, double capacity )
    {
        this.capacity = capacity;
        this.level = level;
    }
    
    @Override
    public void changeLevel( double amount )
    {
        level += amount;
        if( level < 0 ) level = 0;
        if( level > capacity ) level = capacity;
    }
    
    @Override
    public double getCapacity()
    {
        return capacity;
    }

    @Override
    public double getLevel()
    {
        return level;
    }
}
