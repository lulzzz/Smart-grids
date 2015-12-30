
package View;

import java.io.BufferedReader;
import java.io.FileReader;
import Model.City;
import java.io.IOException;

public class FileParser 
{
    public static City parseOptimalSteinerFile( String path ) throws IOException
    {
        City city = new City();
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

            city.addWire(startId,endId);
        }
        
        return city;
    }
}
