
package Control;

import Model.City;
import Model.Moment;
import View.*;
import com.google.gson.JsonObject;

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
            City city = FileParser.parseOptimalSteinerFile(arguments.getGraphFile());

            // Create the simulation
            Simulation simulation = new Simulation( city, arguments.getStartingHour(), arguments.getStartingMinute(), arguments.getTimeStep() );
        
            // output folder
            String outputFolder = arguments.getOutputFolder();
            
            JsonObject json = simulation.run( arguments.getFrames(), outputFolder + "\\plot images");
            
            FileSaver.saveJson(json, outputFolder + "\\simulation.json");
            
            Plotter.plotBids(outputFolder + "\\plot images", arguments.getFrames(), new Moment(arguments.getStartingHour(), arguments.getStartingMinute()), arguments.getTimeStep());
        }
        catch( Exception ex ){ ex.printStackTrace(); System.err.println("Error"); }
    }
}