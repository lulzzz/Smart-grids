
package model;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class City 
{
    private ArrayList<House> houses;
    private ArrayList<Wire> wires;
    
    public City()
    {
        houses = new ArrayList<>();
        wires = new ArrayList<>();
    }
    
    public void buildExampleCity()
    {
        House MartinHouse = new House( new float[]{0,0} );
        House JuanHouse = new House( new float[]{10,0} );
        House JesusHouse = new House( new float[]{0,10} );
        
        houses.add(MartinHouse);
        houses.add(JuanHouse);
        houses.add(JesusHouse);
        
        Wire MartinToJuan = new Wire( MartinHouse, JuanHouse, 10 );
        Wire MartinToJesus = new Wire( MartinHouse, JesusHouse, 10 );
        
        wires.add(MartinToJuan);
        wires.add(MartinToJesus);
    }

    public void saveJSON( String path ) 
    {
        Gson gson = new Gson();
        
        String json = gson.toJson(this);
        
        try 
        {
            FileWriter writer = new FileWriter( path );
            writer.write(json);
            writer.close();
	} 
        catch (IOException e) 
        {
            e.printStackTrace();
	}
    }
}
