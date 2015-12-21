
package Model;

public class Battery 
{
    private double capacity;
    private double level;
    
    public Battery( double level, double capacity )
    {
        this.capacity = capacity;
        this.level = level;
    }
    
    public void changeLevel( double amount )
    {
        level += amount;
    }
    
    public double getRemainingSpace()
    {
        return capacity - level;
    }

    double getLevel()
    {
        return level;
    }
}
