
package Control;

import View.*;

public class Main
{
    public static void main(String[] args)
    {
        // Parse command line arguments
        if( args.length != 1 )
        {
            System.out.println("Wrong number of arguments");
            System.out.println("Usage: java -jar simulator.jar input.json");
            System.exit(1);
        }
        
        // Run the simulation
        try
        {
            String inputJson = args[0];
            // Read input file
            Simulation simulation = FileIO.parseInputJson(inputJson);
            
            simulation.run();
        }
        catch( Exception ex ){ ex.printStackTrace(); System.err.println("Error"); }
    }
}