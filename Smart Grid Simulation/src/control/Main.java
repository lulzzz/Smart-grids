
package Control;

import Model.Core.*;
import View.*;
import com.google.gson.*;
import java.util.HashMap;

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
            HashMap<Integer,Integer> map = FileIO.parseEdges(arguments.getGraphFile());
            City city = new City(map);
            
            // Create the simulation
            Simulation simulation = new Simulation( city, arguments.getStartingHour(), arguments.getStartingMinute(), arguments.getTimeStep() );
        
            // output folder
            String outputFolder = arguments.getOutputFolder();
            
            JsonObject json = simulation.run( arguments.getFrames(), outputFolder + "\\plot images");
            
            FileIO.saveJson(json, outputFolder + "\\simulation.json");
            
            FileIO.plotBids(outputFolder + "\\plot images", arguments.getFrames(), new Moment(arguments.getStartingHour(), arguments.getStartingMinute()), arguments.getTimeStep());
        }
        catch( Exception ex ){ ex.printStackTrace(); System.err.println("Error"); }
    }
}