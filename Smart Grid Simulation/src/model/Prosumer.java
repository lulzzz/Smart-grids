
package Model;

import Model.Interfaces.IBid;
import Model.Interfaces.IDistributor;
import com.google.gson.annotations.*;
import java.io.PrintWriter;
import seas3.core.*;

public abstract class Prosumer
{    
    @Expose
    public int id;
    
    public static int totalDistributors = 0;
    
    @Expose
    public IBid bid;
    
    public abstract void develop( Moment since, Moment until );
    
    public Prosumer( int id )
    {
        this.id = id;
        if( this instanceof IDistributor ) totalDistributors++;
    }
    
    public Participant toParticipant() 
    {
        return new Participant(id, bid.toPLV());
    }
    
    public abstract void setStartingMoment( Moment moment );

    public abstract void applyTrades(Assignment assignment);
    
    public abstract void writePlotData( PrintWriter writer, String outputFolder, Moment moment );
}