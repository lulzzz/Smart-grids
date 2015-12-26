package Control;

import Model.Moment;
import Model.City;
import com.google.gson.annotations.*;
import java.io.*;
import java.util.ArrayList;
import seas3.core.*;
import seas3.radpro.*;

public class Simulation 
{
    private City city;
    private Moment moment;
    private int timeStep;
    
    @Expose
    private ArrayList<Assignment> frames;
    @Expose
    private ArrayList<City> cities;

    public Simulation( City city, int startingHour, int startingMinute, int timeStep )
    {
        this.city = city;
        this.moment = new Moment(startingHour,startingMinute);
        this.timeStep = timeStep;
        frames = new ArrayList<>();
    }   

    public void run( int steps, String outputFolder ) throws IOException
    {
        for( int step = 0; step < steps; step++ )
        {
            // Set the moment
            System.out.println("Moment: " + moment.toString());
            city.setMoment( moment );
            
            // Solve the problem
            Problem problem = city.buildProblem();
            
            Solver radPro = new RadProSolver();
            Options results = radPro.solve(problem, new Options());
            
            // Save the assignment
            Assignment assignment = (Assignment) results.get(Solver.solution);
            frames.add(assignment);
            
            // Develop city
            city.processAssignment( assignment, outputFolder );
            cities.add(city);
            
            // Advance the moment
            moment.advance(timeStep);
        }  
    }
}
