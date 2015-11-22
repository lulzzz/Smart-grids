
package model;

import com.google.gson.annotations.Expose;
import java.io.PrintWriter;
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
    
    public abstract void updateFrame( int frame );
    public abstract void writePlotData( PrintWriter writer );

    public Participant getParticipant()
    {
        return new Participant(id, bid.toPLV());
    }
    
    public Prosumer( float[] position )
    {
        this.id = totalProsumers;
        totalProsumers++;
        this.position = position;
    }
}
