
package View;

import java.io.BufferedReader;
import java.io.FileReader;
import Model.City;

public class FileParser 
{
    public static City parseOptimalSteinerFile( String path )
    {
        City city = new City();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) 
        {
            String line = br.readLine();

            while (!line.startsWith("Edges")) 
                line = br.readLine();
            
            while(!line.equals(""))
            {
                System.out.println(line);
                line = br.readLine();
                String[] lineSegments = line.split("\t");
                
                int startId = Integer.parseInt(lineSegments[0]);
                int endId = Integer.parseInt(lineSegments[1]);
                
                city.newWire(startId,endId);
            }
        }       
        catch (Exception ex) {}
        
        return city;
    }
}
