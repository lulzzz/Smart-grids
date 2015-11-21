package model;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import seas3.core.*;

public class Simulation 
{
    private City city;

    private ArrayList<SimulationFrame> frames;

    public Simulation( City city, int steps )
    {
        this.city = city;
        frames = new ArrayList<>(steps);
        
        for( int step = 0; step < steps; step++ )
        {
            SimulationFrame frame = new SimulationFrame( step, city );
            
            frames.add(frame);
        }
    }   

    public void saveJSON( String path ) 
    {
        Gson gson = new Gson();
        
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
}
