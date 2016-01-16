
package View;

import Control.Simulation;
import Model.Core.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileIO 
{
    public static HashMap<Integer,Integer> parseEdges( String path ) throws IOException
    {
        HashMap<Integer, Integer> map = new HashMap<>();
        
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = br.readLine();

        while (!line.startsWith("Edges")) 
            line = br.readLine();
        
        while((line = br.readLine()) != null)
        {
            System.out.println(line);
            String[] lineSegments = line.split("\t");

            int startId = Integer.parseInt(lineSegments[0]);
            int endId = Integer.parseInt(lineSegments[1]);

            map.put(startId,endId);
        }
        
        return map;
    }
    
    public static void saveJson( JsonObject json, String path ) throws IOException 
    {
        String sjson = json.toString();
        
        FileWriter writer = new FileWriter( path );
        writer.write(sjson);
        writer.close();
    }
    
    public static void plotBids( String outputFolder, int frames, Moment startingMoment, int timeStep ) throws IOException
    {
        for( int frame = 0; frame <= frames; frame++ )
        {
            // Execute gnuplot file
            Runtime.getRuntime().exec("gnuplot \"" + outputFolder + "/frame"+startingMoment.toString()+".txt\"");
            System.out.println("Plotting: " + outputFolder + "/frame"+startingMoment.toString()+".txt");
            // Delete the gnuplot script file
            new File(outputFolder + "\\frame"+startingMoment.toString()+".txt").delete();
            
            startingMoment.advance(timeStep);
        }
    }

    public static Simulation parseInputJson(String inputJson) throws IOException 
    {
        City city = new City();
        
        // Read file and create json object
        String sjson = new String(Files.readAllBytes(Paths.get(inputJson)), StandardCharsets.UTF_8);
        JsonObject json = new Gson().fromJson(sjson, JsonElement.class).getAsJsonObject();
        
        String cityModel = json.get("cityModel").getAsString();
        String outputFolder = json.get("outputFolder").getAsString();
        
        int hour = json.get("hour").getAsInt();
        int minute = json.get("minute").getAsInt();
        int frames = json.get("frames").getAsInt();
        int timeStep = json.get("timeStep").getAsInt();
        
        JsonArray houseArray = json.getAsJsonArray("houses");
        JsonArray wireArray = json.getAsJsonArray("wires");
        
        for( JsonElement elem : houseArray)
        {
            JsonObject object = elem.getAsJsonObject();
            int id = object.get("id").getAsInt();
            int profile = object.get("profile").getAsInt();
            House house = new House(id, profile);
            city.addHouse( house );
        }
        
        for( JsonElement elem : wireArray )
        {
            JsonObject object = elem.getAsJsonObject();
            int origin = object.get("origin").getAsInt();
            int destination = object.get("destination").getAsInt();
            float capacity = object.get("capacity").getAsFloat();
            Wire wire = new Wire(origin, destination, capacity);
            city.addWire( wire );
        }
        
        Simulation simulation = new Simulation(city, hour, minute, timeStep, frames, outputFolder);
        
        return simulation;
    }
}
