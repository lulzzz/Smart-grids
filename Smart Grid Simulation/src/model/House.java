
package Model;

import Model.Interfaces.IBattery;
import java.io.*;
import java.util.*;
import java.util.function.*;
import seas3.core.*;

public class House extends Prosumer
{    
    public double necessity;
    public double baseConsum;
    
    public IBattery battery;
    
    public double distributorRate;
    public double batteryDelta;
    
    
    public House(int id)
    {
        super(id);
        bid = new LogBid();
        
        setTestParameters();
        
        develop(0);
    }
    
    public void setTestParameters()
    {
        necessity = new Random().nextInt(10) + 1;
        baseConsum = 1;
        battery = new Battery(3,10);
        batteryDelta = 0;
        distributorRate = 1;
    }
    
    @Override
    public void develop( int frame )
    {
        battery.changeLevel(batteryDelta - baseConsum);
        
    }
    
    @Override
    public void writePlotData(PrintWriter writer, String outputFolder, int frame)
    {
        String outputFileName = String.format("%s\\id %d frame %d.png", outputFolder, id, frame);
        
        // Header
        writer.print(String.format("set output '%s' %n unset arrow %n", outputFileName));
        
        bid.writePlotData(writer);
    }    

    @Override
    public void applyTrades(Assignment assignment) 
    {
        batteryDelta = 0;
        for(Link link : assignment.keySet())
        {
            if(link.source.getId() == id && link.dest.getId() != id)
            {
                double value = assignment.get(link);
                bid.addTrade(value);
                batteryDelta += value;
            }
            else if(link.dest.getId() == id && link.source.getId() != id)
            {
                double value = assignment.get(link);
                bid.addTrade(-value);
                batteryDelta -= value;
            }            
        }
    }
}