package model;

import java.util.Random;
import com.google.gson.annotations.Expose;
import java.util.Collection;
import java.util.Map;

import seas3.core.*;
import seas3.radpro.RadProSolver;

public class SimulationFrame 
{
    int step;
    public Problem problem;
    @Expose
    private Weather weather;
    @Expose
    public Assignment assignment;
	
    public SimulationFrame( int step, City city, Weather weather ) 
    {
        this.weather = weather;

        this.step = step;
        
        Solver radPro = new RadProSolver();
        
        problem = makeProblem();//city.getProblem( step );
        
        Options o = new Options();
        Options results = radPro.solve(problem, o);

        assignment = (Assignment) results.get(Solver.solution);
        
        city.processResults( assignment );
        
        city.writePlotData( "output/frame"+step+".txt" );
        
    }
    
    public Problem makeProblem() 
    {
        Random random = new Random();
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
		threeParticipants.addLink(0, 1, 10 + random.nextInt(10));
		threeParticipants.addLink(1, 2, 10 + random.nextInt(10));
		
		return threeParticipants;
	}
}
