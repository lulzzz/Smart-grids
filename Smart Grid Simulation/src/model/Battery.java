
package model;

public class Battery 
{
    private double capacity;
    private double level;
    
    public Battery( double capacity )
    {
        this.capacity = capacity;
        this.level = 0;
    }
    
    public void changeLevel( double amount )
    {
        level += amount;
    }
    
    public double getRemainingSpace()
    {
        return capacity - level;
    }
}
