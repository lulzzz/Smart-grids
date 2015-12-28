package Control;

import Model.Moment;
import Model.City;
import View.JSONBuilder;
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
    
    @Expose
    private ArrayList<Assignment> frames;
    @Expose
    private ArrayList<City> cities;

    public Simulation( City city, int startingHour, int startingMinute, int timeStep )
    {
        this.city = city;
        
        this.from = new Moment(startingHour,startingMinute);
        this.to = new Moment(startingHour, startingMinute);
        
        this.timeStep = timeStep;
        
        frames = new ArrayList<>();
        cities = new ArrayList<>();
    }   

    public void run( int steps, String outputFolder ) throws IOException, CloneNotSupportedException
    {
        city.setStartingMoment(from);
        
        for( int step = 0; step <= steps; step++ )
        {
            // Set the moment
            System.out.println(String.format("Simulating from: %s to %s", from.toString(), to.toString()));
            
            
            
            // Solve the problem
            Problem problem = city.toProblem();
            
            Solver radPro = new RadProSolver();
            Options results = radPro.solve(problem, new Options());
            
            // Save the assignment
            Assignment assignment = (Assignment) results.get(Solver.solution);
            frames.add(assignment);
            
            // Process results
            city.processAssignment( assignment, outputFolder );
            
            // Develop the city in this timeframe
            city.develop( from, to );
            
            JSONBuilder.saveCity(city, outputFolder+"\\city " + to.toString() + ".json");
            
            // Advance timeframe
            if( to.minutesSince(from) != 0 )
                from.advance(timeStep);
            to.advance(timeStep);
        }  
    }
}
