
package model;

import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
import seas3.core.Assignment;
import seas3.core.Participant;

/**
 *
 * @author Martin
 */
public abstract class Prosumer
{
    public static int totalProsumers = 0;
    
    @Expose
    public int id;
    @Expose
    public float[] position;
    
    public Bid bid;
    public Participant participant;
    
    public abstract void updateFrame( int frame );
    public abstract void writePlotData( PrintWriter writer );

    
    public Prosumer( float[] position )
    {
        this.id = totalProsumers;
        totalProsumers++;
        this.position = position;
    }

    public abstract void processResults(Assignment assignment);
}
