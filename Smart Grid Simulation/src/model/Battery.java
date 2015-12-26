
package Model;

import Model.Interfaces.IBattery;

public class Battery implements IBattery
{
    private double capacity;
    private double level;
    
    public Battery( double level, double capacity )
    {
        this.capacity = capacity;
        this.level = level;
    }
    
    @Override
    public void changeLevel( double amount )
    {
        level += amount;
    }
    
    @Override
    public double getCapacityLeft()
    {
        return capacity - level;
    }

    public double getLevel()
    {
        return level;
    }
}
