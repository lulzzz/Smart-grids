
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
    public double consumPerMinute;
    public double totalTraded;
    
    private IBattery battery;
    private IDistributor distributor;
    private ArrayList<IAppliance> appliances;
    
    
    
    
    public House(int id)
    {
        super(id);
        
        bid = new LogBid();
        distributor = new Distributor(Prosumer.maxId+1, Distributor.testRate);
        appliances = new ArrayList<>();
        battery = new Battery(3,10);
        
        consumPerMinute = .2;
        totalTraded = 0;
    }
    
    @Override
    public void develop( Moment since, Moment until )
    {
        int elapsedTime = until.minutesSince(since);
        
        double baseConsum = elapsedTime * consumPerMinute;
        
        battery.changeLevel(totalTraded - baseConsum);
        bid.develop(until, baseConsum, battery, distributor, appliances);
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
        totalTraded = 0;
        for(Link link : assignment.keySet())
        {
            if(link.source.getId() == id && link.dest.getId() != id)
            {
                double value = assignment.get(link);
                bid.addTrade(value);
                totalTraded += value;
            }
            else if(link.dest.getId() == id && link.source.getId() != id)
            {
                double value = assignment.get(link);
                bid.addTrade(-value);
                totalTraded -= value;
            }            
        }
    }
}