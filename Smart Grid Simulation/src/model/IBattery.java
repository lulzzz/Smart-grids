
package Model;

public interface IBattery 
{
    public double getCapacityLeft();
    public double getLevel();
    public void changeLevel( double amount );
}