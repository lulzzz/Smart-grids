
package Model.Interfaces;

public interface IBattery 
{
    
    // Revela la capacidad total de la batería
    public double getCapacity();
    // Revela el nivel actual de batería
    public double getLevel();
    // Intenta cambiar el nivel de batería una cantidad determinada
    public void changeLevel( double amount );
    
}
