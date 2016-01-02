
package Model;

import Model.DiscreteImplementations.TimeValueMap;
import Model.DiscreteImplementations.ValueMapDistributor;
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
    private ArrayList<House> houses;
    @Expose
    private IDistributor distributor;
    @Expose
    private ArrayList<Wire> wires;
    
    public City()
    {
        houses = new ArrayList<>();
        wires = new ArrayList<>();
        distributor = new ValueMapDistributor(new TimeValueMap(Data.testRate));
        weather = new Weather();        
    }
    
    public void setStartingMoment( Moment moment )
    {
        this.moment = moment;
        this.weather.setStartingMoment(moment);
        
        for( House house : houses )
            house.setStartingMoment(moment);
    }
    
    public void addWire(int sourceId, int destinationId) 
    {
        // Add houses if they arent added
        House source = new House(sourceId, distributor);
        House destination = new House(destinationId, distributor);
        
        if( !houses.contains(source) ) houses.add(source);
        if( !houses.contains(destination) ) houses.add(destination);
        
        // Add wire
        Wire wire = new Wire( sourceId, destinationId, Data.defaultCapacity );
        wires.add( wire );
    }   

    public Problem toProblem() 
    {
        Problem problem = new Problem();
        
        for( House house : houses )
        {
            problem.addParticipant( house.toParticipant() );
            // Add distributor participant to it
        }
        
        for( Wire wire : wires )
        {
            problem.addLink( wire.originId, wire.destinationId, wire.capacity );
        }
        
        return problem;
    }
    
    public void processAssignment(Assignment assignment, String outputFolder ) throws IOException 
    {
        if( assignment != null )
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
        
        for( House house : houses )
            house.applyTrades( assignment );
        }
        PrintWriter writer = new PrintWriter(outputFolder + "\\frame" + moment.toString() + ".txt");
        
        writer.println("set terminal png");
        for( House house : houses )
        {
            house.writePlotData(writer, outputFolder, moment);
        }
        
        writer.close();
    }

    public void develop( Moment since, Moment until ) 
    {
        this.moment = until;
        
        for( House house : houses )
        {
            house.develop(since, until);
        }
    }
}
