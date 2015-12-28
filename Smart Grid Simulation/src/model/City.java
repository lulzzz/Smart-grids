
package Model;

import Model.Interfaces.IDistributor;
import com.google.gson.annotations.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import seas3.core.*;

public class City 
{
    @Expose
    private Moment moment;
    @Expose
    private Weather weather;
    @Expose
    private ArrayList<Prosumer> prosumers;
    @Expose
    private ArrayList<Wire> wires;
    
    public City()
    {
        prosumers = new ArrayList<>();
        wires = new ArrayList<>();
        weather = new Weather();
    }
    
    public void addWire(int startId, int endId) 
    {
        // Add prosumers if they dont exist
        boolean startFound = false, endFound = false;
        for(Prosumer prosumer : prosumers)
        {
            if( prosumer.id == startId ) startFound = true;
            if( prosumer.id == endId ) endFound = true;
        }
        
        if( !startFound ) 
        {
            HourlyDistributor distributor = new HourlyDistributor( -Prosumer.totalDistributors -1 , Data.testRate );
            House house = new House(startId, distributor);
            prosumers.add( house );
            //prosumers.add( distributor );
            Wire wire = new Wire( distributor.id, house.id, 10);
            //wires.add(wire);
        }
        if( !endFound )
        {
            HourlyDistributor distributor = new HourlyDistributor( -Prosumer.totalDistributors -1 , Data.testRate );
            House house = new House(endId, distributor);
            prosumers.add( house );
            //prosumers.add( distributor );
            Wire wire = new Wire( distributor.id, house.id, 10);
            //wires.add(wire);
        }
        
        // Add wire
        wires.add( new Wire( startId, endId, 10 ));
    }   

    public Problem toProblem() 
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
    
    public void processAssignment(Assignment assignment, String outputFolder ) throws IOException 
    {
        // Set wire flows
        for(Link link : assignment.keySet())
        {
            for( Wire wire : wires )
            {
                if( wire.originId == link.source.getId() && wire.destinationId == link.dest.getId() )
                    wire.setFlow(assignment.get(link));
            }
        }
        
        for( Prosumer prosumer : prosumers )
            prosumer.applyTrades( assignment );
        
        PrintWriter writer = new PrintWriter(outputFolder + "\\frame" + moment.toString() + ".txt");
        
        writer.println("set terminal png");
        for( Prosumer prosumer : prosumers )
        {
            prosumer.writePlotData(writer, outputFolder, moment);
        }
        
        writer.close();
    }

    public void develop( Moment since, Moment until ) 
    {
        this.moment = until;
        
        for( Prosumer prosumer : prosumers )
        {
            prosumer.develop(since, until);
        }
    }
    
    public void setStartingMoment( Moment moment )
    {
        this.moment = moment;
        this.weather.setStartingMoment(moment);
        
        for( Prosumer prosumer : prosumers )
            prosumer.setStartingMoment(moment);
    }
}
