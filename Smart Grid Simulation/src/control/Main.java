
package Control;

import Model.*;
import View.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            String outputFolder = arguments.getOutputFolder();

            JSONBuilder.saveCity(city, outputFolder + "\\city.json");
            JSONBuilder.saveSimulation(simulation, outputFolder + "\\simulation.json");
            
            Plotter.plotBids(outputFolder, arguments.getFrames());
        }
        catch( Exception ex ){ ex.printStackTrace(); System.err.println("Error"); }
        
        
    }
}