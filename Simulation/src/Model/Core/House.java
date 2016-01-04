
package Model.Core;

import Model.ContinuousImplementations.*;
import Model.DiscreteImplementations.*;
import Model.Interfaces.*;
import Model.Interfaces.IBid.TraderType;
import com.google.gson.annotations.*;
import java.io.*;
import java.util.*;
import seas3.core.*;

public class House
{
    @Expose
    private int id;
    
    private double consumPerMinute;
    
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
    @Expose
    private double totalGenerated;
    @Expose
    private double totalApplied;
    @Expose
    private double baseConsum;
    @Expose
    private double balance;
    @Expose
    private double toPay;
    
    
    
    public House(int id)
    {
        this.id = id;
        
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
    
    public void setStartingMoment(Moment moment, IDistributor distributor, Weather weather) 
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
        baseConsum = - elapsedTime * consumPerMinute;
        
        // Generated
        totalGenerated = 0;
        for( IGenerator generator : generators )
        {
            totalGenerated += generator.getGeneration(since, until, weather);
        }
        
        // Appliances
        totalApplied = 0;
        for( IAppliance appliance : appliances )
        {
            totalApplied -= appliance.getConsum(since, until);
        }
        
        // total change
        balance = totalTraded + totalGenerated + totalApplied + baseConsum;
        
        toPay = Math.min(balance+battery.getLevel(), 0);
        battery.changeLevel( balance );
    }
    
    public void refreshBid( Moment moment, IDistributor distributor )
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
        HashMap<Double, TraderType > trades = new HashMap<>();
        
        for(Link link : assignment.keySet())
        {
            if(link.source.getId() == id && link.dest.getId() != id)
            {
                double value = assignment.get(link);
                TraderType type = link.dest.getId() > City.maxId / 2 ? TraderType.Distributor : TraderType.House;
                trades.put(+value, type);
                totalTraded += value;
            }
            else if(link.dest.getId() == id && link.source.getId() != id)
            {
                double value = assignment.get(link);
                TraderType type = link.source.getId() > City.maxId / 2 ? TraderType.Distributor : TraderType.House;
                trades.put(-value, type);
                totalTraded -= value;
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