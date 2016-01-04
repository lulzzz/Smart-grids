package Control;

import Model.Core.Moment;
import Model.Core.City;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import seas3.core.*;
import seas3.radpro.*;

public class Simulation 
{
    private City city;
    private Moment from, to;
    private int timeStep;

    public Simulation( City city, int startingHour, int startingMinute, int timeStep )
    {
        this.city = city;
        
        this.from = new Moment(startingHour,startingMinute);
        this.to = new Moment(startingHour, startingMinute);
        to.advance(timeStep);
        
        this.timeStep = timeStep;
    }   

    public JsonObject run( int steps, String outputFolder ) throws IOException
    {
        // The output json
        JsonObject json = new JsonObject();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonArray array = new JsonArray();
        
        // Save starting state
        city.setStartingMoment(from);
        city.savePlots( outputFolder );
        array.add( new JsonParser().parse( gson.toJson(city) ).getAsJsonObject());
        
        for( int step = 0; step < steps; step++ )
        {
            // Print the moment
            System.out.println(String.format("Simulating from: %s to %s", from.toString(), to.toString()));

            Assignment assignment = solve();

            // Process results
            city.processAssignment( assignment );
            // Develop the city in this timeframe mal nombre 3 en 1
            city.develop( from, to );
            city.savePlots(outputFolder);
            
            
            
            // Save city state
            array.add( new JsonParser().parse( gson.toJson(city) ).getAsJsonObject());
            
            // Refresh bids
            city.refreshBids();

            // Advance timeframe
            from.advance(timeStep);
            to.advance(timeStep);
        }  
        
        json.add("frames", array);
        
        return json;
    }
    
    private Assignment solve()
    {
         // Solve the problem
            Problem problem = city.toProblem();
            
            Solver radPro = new RadProSolver();
            Options results = radPro.solve(problem, new Options());
            
            // Save the assignment
            Assignment assignment = (Assignment) results.get(Solver.solution);
            
            return assignment;
    }
}
