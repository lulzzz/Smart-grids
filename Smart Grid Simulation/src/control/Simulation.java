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
            Problem problem = city.buildProblem();
            
            Solver radPro = new RadProSolver();
        
            Options results = radPro.solve(problem, new Options());

            Assignment assignment = (Assignment) results.get(Solver.solution);
            
            frames.add(assignment);
            
            city.applyTrades( assignment );
            city.createPlotScript(outputFolder, step);
            moment.advance(timeStep);
            System.out.println("Frame: " + moment.toString());
            city.develop( step );
        }  
    }
}
