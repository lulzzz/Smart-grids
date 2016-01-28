
package Model.Core;

import Model.TemporalDistributions.Data;
import Model.Interfaces.*;
import com.google.gson.annotations.*;
import java.io.*;
import java.util.*;
import seas3.core.*;

public class Market 
{
    @Expose
    private Moment moment;
    @Expose
    private Weather weather;
    @Expose
    private ArrayList<Prosumer> houses;
    @Expose
    private Distributor distributor;
    @Expose
    private ArrayList<Wire> wires;
    @Expose
    private String gnuplotScript;


    public Market() 
    {
        houses = new ArrayList<>();
        wires = new ArrayList<>();
        distributor = new Distributor(Data.testRate);
        weather = new Weather(Data.cloudMap, Data.cloudMap); 
    }
    
    public void addHouse( Prosumer house )
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
        
        for( Prosumer house : houses )
            house.setStartingMoment(moment, distributor, weather );
    }
    
       

   public Problem toProblem() 
    {
        Problem problem = new Problem();
        
        for( int i = 0; i < houses.size(); i++ )
        {
            Prosumer house = houses.get(i);
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
        
        for( Prosumer house : houses )
            house.applyTrades( assignment );
        }
    }
    
    public void savePlots( String outputFolder ) throws FileNotFoundException
    {
        gnuplotScript = outputFolder + "/frame" + moment.toString() + ".txt";
        PrintWriter writer = new PrintWriter(outputFolder + "/frame" + moment.toString() + ".txt");
        
        writer.println("set terminal png");
        for( Prosumer house : houses )
        {
            house.writePlotData(writer, outputFolder, moment);
        }
        
        writer.close();
    }

    public void develop( Moment since, Moment until ) 
    {
        this.moment = until;
        this.weather.develop(until);
        
        for( Prosumer house : houses )
        {
            house.develop(since, until,weather);
        }
    }

    public void refreshBids() 
    {
        for( Prosumer house : houses )
            house.refreshBid(moment, distributor);
    }

    public void advance(Moment from, Moment to, Assignment assignment) throws IOException 
    {
        processAssignment(assignment);
        develop(from, to);
    }
}
