
package model;

public class Wire 
{
    private int originId;
    private int destinationId;
    private float capacity;
    
    public Wire( House origin, House destination, float capacity )
    {
        this.originId = origin.id;
        this.destinationId = destination.id;
        this.capacity = capacity;
    }
    
}
