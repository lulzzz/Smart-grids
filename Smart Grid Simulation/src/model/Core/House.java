
package Model.Core;

import Model.ContinuousImplementations.LogBid;
import Model.DiscreteImplementations.Battery;
import Model.DiscreteImplementations.ValueMapAppliance;
import Model.DiscreteImplementations.ValueMapGenerator;
import Model.Interfaces.*;
import com.google.gson.annotations.*;
import java.io.*;
import java.util.*;
import seas3.core.*;

public class House
{
    @Expose
    private int id;
    
    private double consumPerMinute;
    
    private IDistributor distributor;
    @Expose
    private IBattery battery;
    @Expose
    private ArrayList<IAppliance> appliances;
    @Expose
    private ArrayList<IGenerator> generators;
    
    @Expose
    public IBid bid;
    @Expose
    private double totalTraded;
    
    
    
    public House(int id, IDistributor distributor)
    {
        this.id = id;
        
        this.distributor = distributor;
        appliances = new ArrayList<>();
        generators = new ArrayList<>();
        
        generators.add(new ValueMapGenerator(IGenerator.GeneratorType.Solar));
        appliances.add(new ValueMapAppliance(IAppliance.ApplianceType.TV, Data.consumTV));
        battery = new Battery(3,10);
        
        consumPerMinute = .2;
        totalTraded = 0;
    }
    
    public Participant toParticipant() 
    {
        return new Participant(id, bid.toPLV());
    }
    
    public void setStartingMoment(Moment moment, Weather weather) 
    {
        for( IAppliance appliance : appliances )
            appliance.setStartingMoment(moment);
        
        for( IGenerator generator : generators )
            generator.setStartingMoment(moment, weather);
        
        bid = new LogBid(moment, 0,battery, distributor, appliances);
    }
    
    public void develop( Moment since, Moment until, Weather weather )
    {
        // Base consum
        int elapsedTime = until.minutesSince(since);
        double baseConsum = elapsedTime * consumPerMinute;
        
        // Generated
        double totalGenerated = 0;
        for( IGenerator generator : generators )
        {
            totalGenerated += generator.getGeneration(since, until, weather);
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
        
    }
    
    public void refreshBid( Moment moment )
    {
        // Regenerate bid
        bid.develop( moment, 1, battery, distributor, appliances);
    }
    
    public void writePlotData(PrintWriter writer, String outputFolder, Moment moment)
    {
        String plotFile = String.format("%s\\id %d moment %s.png", outputFolder, id, moment.toString());
        
        bid.writePlotData(plotFile, writer);
    }    

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
    
    @Override
    public boolean equals(Object object)
    {
        boolean same = false;

        if (object != null && object instanceof House)
        {
            same = this.id == ((House) object).id;
        }
        
        return same;
    }
}