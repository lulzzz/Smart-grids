
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    
    public void buildExampleCity()
    {
        House MartinHouse = new House( new float[]{0,0} );
        House JuanHouse = new House( new float[]{10,0} );
        House JesusHouse = new House( new float[]{0,10} );
        
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
    }

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

    public Problem getProblem( int step ) 
    {
        Problem problem = new Problem();
        
        for( Prosumer prosumer : prosumers )
        {
            problem.addParticipant( prosumer.getParticipant( step ) );
        }
        
        for( Wire wire : wires )
        {
            problem.addLink( wire.originId, wire.destinationId, wire.capacity);
        }
        
        return problem;
    }
}
