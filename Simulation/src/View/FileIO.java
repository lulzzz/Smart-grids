
package View;

import Model.Core.*;
import com.google.gson.*;
import java.io.*;
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
            Runtime.getRuntime().exec("gnuplot \"" + outputFolder + "\\frame"+startingMoment.toString()+".txt\"");
            // Delete the gnuplot script file
            //new File(outputFolder + "\\frame"+startingMoment.toString()+".txt").delete();
            
            startingMoment.advance(timeStep);
        }
    }
}
