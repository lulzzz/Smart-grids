
package Model.Core;

import com.google.gson.annotations.Expose;

public class Wire 
{
    @Expose
    public int originId;
    @Expose
    public int destinationId;
    @Expose
    public float capacity;
    @Expose
    private double flow;
    
    public Wire( int startId, int endId, float capacity )
    {
        originId = startId;
        destinationId = endId;
        this.capacity = capacity;
    }
    
    public void setFlow( double flow )
    {
        this.flow = flow;
    }
}
