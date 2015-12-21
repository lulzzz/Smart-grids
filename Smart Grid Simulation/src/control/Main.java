
package Control;

import Model.*;
import View.*;

public class Main
{
    public static void main(String[] args)
    {
        // Parse command lines
        CommandLineArguments arguments = new CommandLineArguments( args );
        
        // Read input file
        City city = FileParser.parseOptimalSteinerFile(arguments.getGraphFile());
        
        // Create the simulation
        Simulation simulation = new Simulation( city );
        
        // Run the simulation
        try
        {
            simulation.run( arguments.getFrames(), arguments.getOutputFolder());
            
            // Save output
            String ouputFolder = arguments.getOutputFolder();

            JSONBuilder.saveCity(city, ouputFolder + "\\city.json");
            JSONBuilder.saveSimulation(simulation, ouputFolder + "\\simulation.json");
        }
        catch( Exception ex ){ ex.printStackTrace(); }
    }
}