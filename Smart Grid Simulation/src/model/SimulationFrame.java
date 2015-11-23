package model;

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
    public Assignment assignment;
	
    public SimulationFrame( int step, City city ) 
    {
        this.step = step;
        
        Solver radPro = new RadProSolver();
        
        problem = city.getProblem( step );
        
        Options o = new Options();
        Options results = new Options();
        
        results = radPro.solve(problem, o);

        assignment = (Assignment) results.get(Solver.solution);
        
        city.processResults( assignment );
        
        city.writePlotData( "output/frame"+step+".txt" );
        
    }
}
