
package Model.DiscreteImplementations;

import Model.Core.Moment;
import java.util.SortedMap;
import java.util.TreeMap;

public class TimeValueMap extends TreeMap<Moment, Double>
{
    public Moment getLowerNearest(Moment moment) 
    {
        Moment result = null;
        for( Moment key : keySet() ) 
        {
            if( key.compareTo(moment) <= 0 )
                result = key;
        }
        
        return result;
    }
    
    public Double getLowerNearestValue(Moment moment) 
    {
        Moment result = null;
        for( Moment key : keySet() ) 
        {
            if( key.compareTo(moment) <= 0 )
                result = key;
        }
        
        return get(result);
    }
    
    public double getMeanBetween(Moment since, Moment until) 
    {
        double res = 0;
        
        Moment start = getLowerNearest(since);
        
        SortedMap<Moment, Double> submap = subMap(start, until);

        for( Double value : submap.values())
        {
            res += value;
        }

        return res / submap.size();
        
    }

    public double getAddedValue(Moment since, Moment until) 
    {        
        return 1;
    }

    public double getProgress(Moment until) 
    {
        int elapsed = until.minutesSince(firstKey());
        int total = lastKey().minutesSince(firstKey());
        
        double progress = elapsed / (double) total;
        
        return Math.min(1, progress);
    }
}
