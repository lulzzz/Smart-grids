
package Model;

import com.google.gson.annotations.*;
import java.io.PrintWriter;
import seas3.core.*;

public abstract class Prosumer
{    
    @Expose
    public int id;
    
    public static int maxId;
    
    public Bid bid;
    
    public abstract void develop( int frame );
    
    public Prosumer( int id )
    {
        this.id = id;
        maxId = id > maxId? id : maxId;
    }
    
    public Participant toParticipant() 
    {
        return new Participant(id, bid.toPLV());
    }

    void applyTrades(Assignment assignment) 
    {
        /*
        for(Link l : participant.getInLinks())
        {
            double trade = Math.abs(assignment.get(l));
            bid.addTrade(trade);
        }
        
        for(Link l : participant.getOutLinks())
        {
            double trade = Math.abs(assignment.get(l));
            bid.addTrade(trade);
        }
        */
    }
    
    public abstract void writePlotData( PrintWriter writer, int frame );
}
