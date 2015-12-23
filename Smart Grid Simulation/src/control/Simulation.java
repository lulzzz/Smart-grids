package Control;

import com.google.gson.annotations.*;
import java.io.*;
import java.util.ArrayList;
import Model.City;
import model.Times;
import seas3.core.*;
import seas3.radpro.*;

public class Simulation 
{
    private City city;
    private Times time;
    
    @Expose
    private ArrayList<Assignment> frames;
    

    public Simulation( City city, int startingHour, int startingMinute, int frameMinutes )
    {
        this.city = city;
        this.time = new Times(startingHour,startingMinute,frameMinutes);
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
            time.nextFrame();
            System.out.println("Frame: " + time.toString());
            city.develop( step );
        }  
    }
}
