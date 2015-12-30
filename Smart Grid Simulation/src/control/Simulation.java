package Control;

import Model.Moment;
import Model.City;
import View.FileSaver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.*;
import java.io.*;
import java.util.ArrayList;
import seas3.core.*;
import seas3.radpro.*;

public class Simulation 
{
    private City city;
    private Moment from;
    private Moment to;
    private int timeStep;

    public Simulation( City city, int startingHour, int startingMinute, int timeStep )
    {
        this.city = city;
        
        this.from = new Moment(startingHour,startingMinute);
        this.to = new Moment(startingHour, startingMinute);
        
        this.timeStep = timeStep;
    }   

    public JsonObject run( int steps, String outputFolder ) throws IOException, CloneNotSupportedException
    {
        // The output json
        JsonObject json = new JsonObject();
        // Array of frames
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonArray array = new JsonArray();
        
        // Set city at moment from
        city.setStartingMoment(from);
        
        for( int step = 0; step <= steps; step++ )
        {
            // Print the moment
            System.out.println(String.format("Simulating from: %s to %s", from.toString(), to.toString()));

            // Solve the problem
            Problem problem = city.toProblem();
            
            Solver radPro = new RadProSolver();
            Options results = radPro.solve(problem, new Options());
            
            // Save the assignment
            Assignment assignment = (Assignment) results.get(Solver.solution);

            // Process results
            city.processAssignment( assignment, outputFolder );
            
            // Develop the city in this timeframe
            city.develop( from, to );
            
            JsonObject entry = new JsonParser().parse( gson.toJson(city) ).getAsJsonObject();
            array.add(entry);
            
            // Advance timeframe
            if( to.minutesSince(from) != 0 )
                from.advance(timeStep);
            to.advance(timeStep);
        }  
        
        json.add("frames", array);
        
        return json;
    }
}
