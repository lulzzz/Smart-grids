
package model;

import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import seas3.core.Assignment;
import seas3.core.Participant;

public abstract class Prosumer
{    
    @Expose
    public int id;
    
    public Bid bid;
    public Participant participant;
    
    public abstract void updateFrame( int frame );
    public abstract void writePlotData( PrintWriter writer );

    
    public Prosumer( int id )
    {
        this.id = id;
    }

    public abstract void processResults(Assignment assignment);
}
