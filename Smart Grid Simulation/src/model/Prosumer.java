
package Model;

import Model.Interfaces.IBid;
import com.google.gson.annotations.*;
import java.io.PrintWriter;
import seas3.core.*;

public abstract class Prosumer
{    
    @Expose
    public int id;
    
    public static int maxId;
    
    public IBid bid;
    
    public abstract void setMoment( Moment moment );
    
    public Prosumer( int id )
    {
        this.id = id;
        maxId = id > maxId? id : maxId;
    }
    
    public Participant toParticipant() 
    {
        return new Participant(id, bid.toPLV());
    }

    public abstract void applyTrades(Assignment assignment);
    
    public abstract void writePlotData( PrintWriter writer, String outputFolder, Moment moment );
}