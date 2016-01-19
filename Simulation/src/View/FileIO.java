
package View;

import Control.*;
import Model.Core.*;
import com.google.gson.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileIO 
{
    public static Simulation parseInputJson(String inputJson) throws IOException 
    {
        Market market = new Market();
        
        // Read file and create json object
        String sjson = new String(Files.readAllBytes(Paths.get(inputJson)), StandardCharsets.UTF_8);
        JsonObject json = new Gson().fromJson(sjson, JsonElement.class).getAsJsonObject();
        
        // Parse market mesh and output folder references
        JsonElement marketMeshElement = json.get("marketMesh");
        String marketMesh = marketMeshElement != null ? marketMeshElement.getAsString() : "No market mesh provided";
        String outputFolder = json.get("outputFolder").getAsString();
        
        // Parse starting moment
        int hour = json.get("hour").getAsInt();
        int minute = json.get("minute").getAsInt();
        
        // Parse  number of frames and minutesPerFrame
        int frames = json.get("frames").getAsInt();
        int minutesPerFrame = json.get("minutesPerFrame").getAsInt();
        
        // Parse prosumers and wires
        JsonArray prosumerArray = json.getAsJsonArray("prosumers");
        JsonArray wireArray = json.getAsJsonArray("wires");
        
        for( JsonElement elem : prosumerArray)
        {
            JsonObject object = elem.getAsJsonObject();
            int id = object.get("id").getAsInt();
            int profile = object.get("profile").getAsInt();
            Prosumer house = new Prosumer(id, profile);
            market.addHouse( house );
        }
        
        for( JsonElement elem : wireArray )
        {
            JsonObject object = elem.getAsJsonObject();
            int origin = object.get("origin").getAsInt();
            int destination = object.get("destination").getAsInt();
            float capacity = object.get("capacity").getAsFloat();
            Wire wire = new Wire(origin, destination, capacity);
            market.addWire( wire );
        }
        
        Simulation simulation = new Simulation(market, hour, minute, frames, minutesPerFrame, outputFolder, marketMesh);
        
        return simulation;
    }
    public static void saveJson( JsonObject json, String path ) throws IOException 
    {
        String sjson = json.toString();
        
        FileWriter writer = new FileWriter( path );
        writer.write(sjson);
        writer.close();
    }
    
    public static void plotBids( JsonObject info ) throws IOException
    {
        JsonArray frames = info.get("frames").getAsJsonArray();
        for( JsonElement frame : frames )
        {
            // Get gnuplot file 
            String gnuplotScript = frame.getAsJsonObject().get("gnuplotScript").getAsString();
            
            // Execute gnuplot script
            Runtime.getRuntime().exec("gnuplot \"" + gnuplotScript + "\"");
            System.out.println("Plotting: " + gnuplotScript);
            // Delete the gnuplot script file
            //new File(outputFolder + "\\frame"+startingMoment.toString()+".txt").delete();
        }
    }

    
}
