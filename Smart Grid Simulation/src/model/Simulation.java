package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import seas3.core.*;

public class Simulation 
{
    private City city;
    @Expose
    private ArrayList<SimulationFrame> frames;

    public Simulation( City city, int steps )
    {
        this.city = city;
        frames = new ArrayList<>(steps);
<<<<<<< HEAD
=======
    }   

    public void run( int steps ) 
    {
        
>>>>>>> origin/master
        
        for( int step = 0; step < steps; step++ )
        {
            Weather weather = new Weather();
            weather.develop();
            SimulationFrame frame = new SimulationFrame( step, city, weather );
            
            frames.add(frame);
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
}
