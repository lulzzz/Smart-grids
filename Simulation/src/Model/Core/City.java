
package Model.Core;

import Model.DataSets.Data;
import Model.Implementations.Distributor;
import Model.Interfaces.*;
import com.google.gson.annotations.*;
import java.io.*;
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
        distributor = new Distributor(Data.testRate);
        weather = new Weather(Data.cloudMap, Data.cloudMap); 
    }
    
    public void addHouse( House house )
    {
        houses.add(house);
    }
    
    public void addWire(Wire wire) 
    {
        wires.add( wire );
    }
    
    public void setStartingMoment( Moment moment )
    {
        this.moment = moment;
        this.weather.setStartingMoment(moment);
        this.distributor.setStartingMoment(moment);
        
        for( House house : houses )
            house.setStartingMoment(moment, distributor, weather );
    }
    
       

   public Problem toProblem() 
    {
        Problem problem = new Problem();
        
        for( int i = 0; i < houses.size(); i++ )
        {
            House house = houses.get(i);
            Participant houseParticipant = house.toParticipant();
            Participant distributorParticipant = distributor.toParticipant( houses.size() + i );
            
            problem.addParticipant( houseParticipant );
            problem.addParticipant(distributorParticipant);
            
            problem.addLink(houseParticipant.getId(), distributorParticipant.getId(), Data.defaultCapacity);
            
        }
        
        for( Wire wire : wires )
        {
            problem.addLink( wire.originId, wire.destinationId, wire.capacity );
        }
        
        return problem;
    }
    
    public void processAssignment(Assignment assignment ) throws IOException 
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
    }
    
    public void savePlots( String outputFolder ) throws FileNotFoundException
    {
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
        this.weather.develop(until);
        
        for( House house : houses )
        {
            house.develop(since, until,weather);
        }
    }

    public void refreshBids() 
    {
        for( House house : houses )
            house.refreshBid(moment, distributor);
    }
}
