package Control;

import com.google.gson.*;
import com.google.gson.annotations.*;
import java.io.*;
import java.util.*;
import Model.*;
import seas3.core.*;
import seas3.radpro.*;

public class Simulation 
{
    private City city;
    
    @Expose
    private ArrayList<Assignment> frames;
    

    public Simulation( City city )
    {
        this.city = city;
        frames = new ArrayList<>();
    }   

    public void run( int steps, String outputFolder ) throws IOException
    {
        for( int step = 0; step < steps; step++ )
        {
            Problem problem = testProblem();//city.buildProblem();
            
            Solver radPro = new RadProSolver();
        
            Options results = radPro.solve(problem, new Options());

            Assignment assignment = (Assignment) results.get(Solver.solution);
            
            city.applyTrades( assignment );
            city.createPlotScript(outputFolder, step);
            city.develop( step );
        }  
    }
    
    public Problem testProblem() 
    {
        Problem threeParticipants;

        // Create network graph. We will use a simple network with three participants.
        threeParticipants = new Problem();		

        // Now we create the participants with incremental unique ids and the zero plv
        PiecewiseLinearValuation zeroPlv = PiecewiseLinearValuation.discrete(0,0);

        Participant Jesus = new Participant(0,zeroPlv);
        Participant Juan = new Participant(1,zeroPlv);
        Participant Martin = new Participant(2,zeroPlv);

        threeParticipants.addParticipant(Jesus);
        threeParticipants.addParticipant(Juan);
        threeParticipants.addParticipant(Martin);

        // We define the links (lower id first) with random capacity
        threeParticipants.addLink(0, 1, 10);
        threeParticipants.addLink(1, 2, 10);

        return threeParticipants;
    }
}
