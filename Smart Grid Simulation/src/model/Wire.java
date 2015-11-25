
package model;

import com.google.gson.annotations.Expose;

public class Wire 
{
    @Expose
    public int originId;
    @Expose
    public int destinationId;
    @Expose
    public float capacity;
    
    public Wire( Prosumer origin, Prosumer destination, float capacity )
    {
        this.originId = origin.id;
        this.destinationId = destination.id;
        this.capacity = capacity;
    }
    
}
