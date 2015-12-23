
package View;

import Control.*;
import Model.*;
import com.google.gson.*;
import java.io.*;

public class JSONBuilder 
{
    public static void saveCity( City city, String path ) throws IOException
    {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        
        String json = gson.toJson( city );
        
        FileWriter writer = new FileWriter( path );
        writer.write(json);
        writer.close();
    }
    
    
    public static void saveSimulation( Simulation simulation, String path ) throws IOException 
    {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        
        String json = gson.toJson( simulation );
        
        FileWriter writer = new FileWriter( path );
        writer.write(json);
        writer.close();
    }
}
