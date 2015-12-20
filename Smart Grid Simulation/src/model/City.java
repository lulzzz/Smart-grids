
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import seas3.core.Assignment;
import seas3.core.Problem;

public class City 
{
    @Expose
    private ArrayList<Prosumer> prosumers;
    @Expose
    private ArrayList<Wire> wires;
    
    public City()
    {
        prosumers = new ArrayList<>();
        wires = new ArrayList<>();
    }
    /*
    public void buildExampleCity()
    {
        House MartinHouse = new House( new float[]{0,0}, null );
        House JuanHouse = new House( new float[]{10,0}, null );
        House JesusHouse = new House( new float[]{0,10}, null );
        
        prosumers.add(MartinHouse);
        prosumers.add(JuanHouse);
        prosumers.add(JesusHouse);
        
        Wire MartinToJuan = new Wire( MartinHouse, JuanHouse, 10 );
        Wire MartinToJesus = new Wire( MartinHouse, JesusHouse, 10 );
        
        wires.add(MartinToJuan);
        wires.add(MartinToJesus);
    }
    
    public void addDistributorToEachHouse()
    {
        for( int i = prosumers.size()-1; i >= 0; i-- )
        {
            House house = (House) prosumers.get(i);
            
            // Create a distributor near it and link it
            Distributor distributor = new Distributor(new float[]{house.position[0] + 1, house.position[1]}, Distributor.testRate);
            Wire wire = new Wire(distributor, house, 10);
            
            house.distributor = distributor;
            
            prosumers.add(distributor);
            wires.add(wire);
        }
    }*/

    public void saveJSON( String path ) 
    {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        
        String json = gson.toJson(this);
        
        try 
        {
            FileWriter writer = new FileWriter( path );
            writer.write(json);
            writer.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public Problem getProblem( int frame ) 
    {
        Problem problem = new Problem();
        
        for( Prosumer prosumer : prosumers )
        {
            prosumer.updateFrame(frame);
            problem.addParticipant( prosumer.participant );
        }
        
        for( Wire wire : wires )
        {
            problem.addLink( wire.originId, wire.destinationId, wire.capacity);
        }
        
        return problem;
    }

    void writePlotData(String path) 
    {
        try
        {
            PrintWriter writer = new PrintWriter(path);
            
            writer.print(String.format("set terminal png%n%n"));
            
            for(Prosumer p : prosumers)
            {
                p.writePlotData(writer);
            }
            
            writer.close();
        }
        catch( IOException ex )
        {
            ex.printStackTrace();
        }
    }

    public void processResults(Assignment assignment) 
    {
        for( Prosumer prosumer : prosumers )
        {
            prosumer.processResults(assignment);
        }
    }
    
    public boolean hasProsumer(int id)
    {
        for(Prosumer prosumer : prosumers)
        {
            if( prosumer.id == id ) return true;
        }
        return false;
    }

    public void addHouseIfNecessary(int id) 
    {
        if( ! this.hasProsumer(id) )
        {
            prosumers.add(new House(id));
        }
    }

    public void addWire(int startId, int endId) 
    {
        addHouseIfNecessary(startId);
        addHouseIfNecessary(endId);
        
        Wire wire = new Wire(startId, endId, 10);
    }
}
