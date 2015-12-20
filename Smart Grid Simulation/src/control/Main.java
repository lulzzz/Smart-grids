
package control;

import com.beust.jcommander.JCommander;
import java.io.*;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

public class Main
{
    public static void main(String[] args)
    {
        // Parse command lines
        CommandLineArguments arguments = new CommandLineArguments();
        new JCommander(arguments, args);
        
        // Read input file
        City city = readInput(arguments.getGraphFile());
        
        // Create the simulation
        Simulation simulation = new Simulation( city, arguments.getFrames() );
        
        // Run the simulation
        //simulation.run();
        
        // Save results in output folder
        //simulation.saveResults( arguments.getOutputFolder() );
    }
    
    private static City readInput(String path)
    {
        City city = new City();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) 
        {
            String line = br.readLine();

            while (!line.startsWith("Edges")) 
                line = br.readLine();
            
            while(line != null)
            {
                line = br.readLine();
                String[] lineSegments = line.split("\t");
                
                int startId = Integer.parseInt(lineSegments[0]);
                int endId = Integer.parseInt(lineSegments[1]);
                
                city.addWire(startId,endId);
            }
        }       
        catch (IOException ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return city;
    }
}