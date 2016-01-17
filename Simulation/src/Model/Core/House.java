
package Model.Core;

import Model.DataSets.Data;
import Model.Implementations.*;
import Model.Interfaces.*;
import Model.Interfaces.IAppliance.ApplianceType;
import Model.Interfaces.IBid.*;
import com.google.gson.annotations.*;
import java.io.*;
import java.util.*;
import seas3.core.*;

public class House
{
    private static int maxId = 0;
    @Expose
    private int id;
    @Expose
    private int profile;
    
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
    private double mandatory;
    
    
    
    public House(int id, int profile)
    {
        this.id = id;
        if( id > maxId ) maxId = id;
        
        this.profile = profile;
        
        appliances = new ArrayList<>();
        generators = new ArrayList<>();
        
        addComponents();
    }
    
    private void addComponents()
    {
        switch(profile)
        {
            case 1:
                generators.add(new SolarGenerator());
                generators.add(new WindGenerator());
                
                appliances.add(new Appliance(ApplianceType.Cooking, Data.consumTV));
                break;
                
            case 2:
                generators.add(new SolarGenerator());
                
                appliances.add(new Appliance(ApplianceType.Cooking, Data.consumTV));
                appliances.add(new Appliance(ApplianceType.WashingMachine, Data.consumTV));
                break;
                
            case 3:
                appliances.add(new Appliance(ApplianceType.Cooking, Data.consumTV));
                appliances.add(new Appliance(ApplianceType.TV, Data.consumTV));
                appliances.add(new Appliance(ApplianceType.WashingMachine, Data.consumTV));
                break;
                
        }
        
        battery = new Battery(3,10);
        
        consumPerMinute = .2 * profile;
        mandatory = -battery.getLevel();
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
        
        bid = new LogBid(moment, mandatory,mandatory + battery.getCapacity(), distributor, appliances);
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
        
        // Pay mandatory
        double extra = 0;
        if( totalTraded >= mandatory )
        {
            extra = totalTraded - mandatory; // we get extra energy after trading
        }
        else
        {
            battery.changeLevel( totalTraded - mandatory);
        }
        
        // Compute next mandatory
        balance = extra + totalGenerated + totalApplied + baseConsum;
        if( balance >= 0)
        {
            // Store extra and no mandatory
            battery.changeLevel(balance);
            mandatory = - battery.getLevel();
        }
        else
        {
            // mandatory can be negative
            mandatory =-balance - battery.getLevel();
        }
    }
    
    public void refreshBid( Moment moment, IDistributor distributor )
    {
        // Regenerate bid
        bid.develop( moment, mandatory, mandatory+battery.getCapacity(), distributor, appliances);
    }
    
    public void writePlotData(PrintWriter writer, String outputFolder, Moment moment)
    {
        String plotFile = String.format("%s/id %d moment %s.png", outputFolder, id, moment.toString());
        
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
                TraderType type = link.dest.getId() > maxId ? TraderType.Distributor : TraderType.House;
                trades.put(+value, type);
                totalTraded += value;
            }
            else if(link.dest.getId() == id && link.source.getId() != id)
            {
                double value = assignment.get(link);
                TraderType type = link.source.getId() > maxId ? TraderType.Distributor : TraderType.House;
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