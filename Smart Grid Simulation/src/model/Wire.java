
package model;

public class Wire 
{
    public int originId;
    public int destinationId;
    public float capacity;
    
    public Wire( House origin, House destination, float capacity )
    {
        this.originId = origin.id;
        this.destinationId = destination.id;
        this.capacity = capacity;
    }
    
}
