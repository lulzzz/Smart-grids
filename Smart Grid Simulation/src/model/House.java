
package Model;

import Model.Interfaces.IAppliance;
import Model.Interfaces.IBattery;
import Model.Interfaces.IDistributor;
import java.io.*;
import java.util.*;
import java.util.function.*;
import seas3.core.*;

public class House extends Prosumer
{
    public double baseConsum;
    public double batteryDelta;
    
    public IBattery battery;
    private IDistributor distributor;
    private ArrayList<IAppliance> appliances;
    
    
    
    
    public House(int id)
    {
        super(id);
        bid = new LogBid();
        distributor = new Distributor(Prosumer.maxId+1, Distributor.testRate);
        appliances = new ArrayList<>();
        battery = new Battery(3,10);
        
        baseConsum = 1;
        batteryDelta = 0;
    }
    
    @Override
    public void setMoment( Moment moment )
    {
        battery.changeLevel(batteryDelta - baseConsum);
        bid.develop(moment, baseConsum, battery, distributor, appliances);
    }
    
    @Override
    public void writePlotData(PrintWriter writer, String outputFolder, Moment moment)
    {
        String outputFileName = String.format("%s\\id %d frame %s.png", outputFolder, id, moment.toString());
        
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