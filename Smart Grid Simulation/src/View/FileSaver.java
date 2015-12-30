
package View;

import Control.Simulation;
import Model.City;
import com.google.gson.*;
import java.io.*;

public class FileSaver 
{    
    public static void saveJson( JsonObject json, String path ) throws IOException 
    {
        String sjson = json.toString();
        
        FileWriter writer = new FileWriter( path );
        writer.write(sjson);
        writer.close();
    }
}
