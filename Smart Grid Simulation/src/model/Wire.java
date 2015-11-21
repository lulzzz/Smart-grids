
package model;

public class Wire 
{
    private House origin;
    private House destination;
    private float capacity;
    
    public Wire( House origin, House destination, float capacity )
    {
        this.origin = origin;
        this.destination = destination;
        this.capacity = capacity;
    }
    
}
