
package Control;

import View.*;

public class Main
{
    public static void main(String[] args)
    {
        // Parse command lines
        CommandLineArguments arguments = new CommandLineArguments( args );
        
        // Run the simulation
        try
        {
            // Read input file
            Simulation simulation = FileIO.parseInputJson(arguments.getInputJson());
            
            simulation.run();
        }
        catch( Exception ex ){ ex.printStackTrace(); System.err.println("Error"); }
    }
}