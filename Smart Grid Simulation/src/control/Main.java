
package control;

import javax.swing.JFrame;
import model.*;

public class Main extends JFrame
{
    public static final int simulationFrames = 24;
    
    public static void main(String[] args)
    {
        City city = new City();
        city.saveJSON();
        
        Simulation simulation = new Simulation( city, simulationFrames );
        simulation.run();
        simulation.saveJSON();
    }

    
    
}

/*package control;

import model.SimulationFrame;
import model.Simulation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import seas3.core.*;
import seas3.radpro.RadProSolver;

public class InMemorySolving 
{
	public static Random random;
	public RadProSolver solver;
	private Simulation simulation;
	private SimulationFrame frame;
	
	private ArrayList<Double> australianMeanConsum = new ArrayList<Double>()
	{
		
	};

	public Problem makeProblem() {
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
	
	public Assignment solve(Problem problem) {
		
		Options o = new Options();
		Options results = new Options();
		results = solver.solve(problem, o);
		
		Assignment sol = (Assignment) results.get(Solver.solution);
		
		
		return sol;
		
	}
	
	public void run() 
	{
		// Run a 1 day simulation (24 steps) of the threeParticipants problem
		random = new Random();
		
		int timeSteps = 24;
		simulation = new Simulation(timeSteps);
		
		
		
		Problem p = makeProblem();
		solver = new RadProSolver();
		
		for (int i = 0 ; i < timeSteps ; i++) 
		{
			frame = new SimulationFrame();
			
			modifyBids(p, i);
			
			Assignment sol = solve(p);
			
			frame = new SimulationFrame( p.getParticipants(), sol);
			
			simulation.AddFrame(frame);
			
		}
		
		System.out.println(simulation.toString());
		saveJSON();
	}
	private void modifyBids(Problem p, int timeStep) {
		for (Participant part: p.getParticipants()) {
			PiecewiseLinearValuation plv = randomDiscreteValuation();
			//PiecewiseLinearValuation plv = australianDiscreteValuation(timeStep);
			part.setValuation(plv);
		}
	}

	private PiecewiseLinearValuation australianDiscreteValuation(int timeStep) {
		
		return null;
	}

	private PiecewiseLinearValuation randomDiscreteValuation() {
		
		int numOffers =random.nextInt(10) + 1;
		
		double quantities[], prices[];
		quantities = new double[numOffers];
		prices = new double[numOffers];
		
		// Add n-1 random offers
		for(int i = 0; i < numOffers - 1; i++)
		{
			quantities[i] = random.nextInt(20) - 10;

			prices[i] = quantities[i] * (random.nextGaussian()/100 + 1);
		}
		
		// Last offer is 0 - 0
		quantities[numOffers -1] = 0;
		prices[numOffers - 1 ] = 0;
		
		PiecewiseLinearValuation plv = PiecewiseLinearValuation.discrete(quantities, prices);
		System.out.println(plv);
		return plv;
	}

	public static void main(String args[]) 
	{
		new InMemorySolving().run();
		
	}
	
	private void saveJSON()
	{
		String json = simulation.toJSON();
		try {
			File file = new File("test/seas3/experiments/test.json");
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(json);
			output.close();
			System.out.println("json saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}*/
