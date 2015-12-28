
package Model;

import Model.Interfaces.IAppliance;
import Model.Interfaces.IBattery;
import Model.Interfaces.IDistributor;
import Model.Interfaces.IGenerator;
import com.google.gson.annotations.Expose;
import java.io.*;
import java.util.*;
import java.util.function.*;
import seas3.core.*;

public class House extends Prosumer
{
    public double consumPerMinute;
    @Expose
    public double totalTraded;
    
    private IBattery battery;
    private IDistributor distributor;
    @Expose
    private ArrayList<IAppliance> appliances;
    private ArrayList<IGenerator> generators;
    
    
    
    
    public House(int id)
    {
        super(id);
        
        distributor = new Distributor(Prosumer.maxId+1, Distributor.testRate);
        appliances = new ArrayList<>();
        generators = new ArrayList<>();
        
        generators.add(new ValueMapGenerator());
        appliances.add(new ValueMapAppliance(10));
        battery = new Battery(3,10);
        
        consumPerMinute = .2;
        totalTraded = 0;
    }
    
    @Override
    public void setStartingMoment(Moment moment) 
    {
        bid = new LogBid(moment, 0,battery, distributor, appliances);
    }
    
    @Override
    public void develop( Moment since, Moment until )
    {
        // Base consum
        int elapsedTime = until.minutesSince(since);
        double baseConsum = elapsedTime * consumPerMinute;
        
        // Generated
        double totalGenerated = 0;
        for( IGenerator generator : generators )
        {
            totalGenerated += generator.getGeneration(since, until);
        }
        
        // Appliances
        double totalApplied = 0;
        for( IAppliance appliance : appliances )
        {
            totalApplied += appliance.getConsum(since, until);
        }
        
        // total change
        double total = totalTraded + totalGenerated - totalApplied - baseConsum;
        battery.changeLevel( total );
        
        // Regenerate bid
        bid.develop(until, baseConsum, battery, distributor, appliances);
    }
    
    @Override
    public void writePlotData(PrintWriter writer, String outputFolder, Moment moment)
    {
        String outputFileName = String.format("%s\\id %d moment %s.png", outputFolder, id, moment.toString());
        
        // Header
        writer.print(String.format("set output '%s' %nunset arrow %n", outputFileName));
        
        bid.writePlotData(writer);
    }    

    @Override
    public void applyTrades(Assignment assignment) 
    {
        totalTraded = 0;
        ArrayList<Double> trades = new ArrayList<>();
        
        for(Link link : assignment.keySet())
        {
            if(link.source.getId() == id && link.dest.getId() != id)
            {
                double value = assignment.get(link);
                trades.add(-value);
                totalTraded -= value;
            }
            else if(link.dest.getId() == id && link.source.getId() != id)
            {
                double value = assignment.get(link);
                trades.add(value);
                totalTraded += value;
            }            
        }
        
        bid.setTrades(trades);
    }

    
}