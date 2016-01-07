
package Model.Interfaces;

public interface IBattery 
{
    public double getCapacity();
    public double getLevel();
    public void changeLevel( double amount );
}
