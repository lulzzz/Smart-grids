
package Model;

import com.google.gson.annotations.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import seas3.core.*;

public class City 
{
    @Expose
    private final ArrayList<Prosumer> prosumers;
    @Expose
    private final ArrayList<Wire> wires;
    
    public City()
    {
        prosumers = new ArrayList<>();
        wires = new ArrayList<>();
    }
    
    public void newWire(int startId, int endId) 
    {
        // Add prosumers if they dont exist
        boolean startFound = false, endFound = false;
        for(Prosumer prosumer : prosumers)
        {
            if( prosumer.id == startId ) startFound = true;
            if( prosumer.id == endId ) endFound = true;
        }
        
        if( !startFound ) prosumers.add(new House(startId) );
        if( !endFound )   prosumers.add(new House(endId) );
        
        // Add wire
        wires.add( new Wire( startId, endId, 10 ));
    }   

    public Problem buildProblem() 
    {
        Problem problem = new Problem();
        
        for( Prosumer prosumer : prosumers )
        {
            problem.addParticipant( prosumer.toParticipant() );
        }
        
        for( Wire wire : wires )
        {
            problem.addLink( wire.originId, wire.destinationId, wire.capacity );
        }
        
        return problem;
    }
    
    public void applyTrades(Assignment assignment) 
    {
        for( Prosumer prosumer : prosumers )
            prosumer.applyTrades( assignment );
    }
    
    public void createPlotScript( String outputFolder, int frame ) throws IOException
    {
        PrintWriter writer = new PrintWriter(outputFolder + "\\frame" + frame + ".txt");
        
        writer.println("set terminal png");
        for( Prosumer prosumer : prosumers )
        {
            prosumer.writePlotData(writer, frame);
        }
        writer.close();
    }

    public void develop( int frame ) 
    {
        for( Prosumer prosumer : prosumers )
        {
            prosumer.develop(frame);
        }
    }
}
