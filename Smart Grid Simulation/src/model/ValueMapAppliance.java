
package Model;

import Model.Interfaces.IAppliance;
import java.util.HashMap;

public class ValueMapAppliance implements IAppliance 
{
    private ApplianceState state;
    
    private HashMap<Moment, Double> consumAt = new HashMap<Moment,Double>()
    {{
      put(new Moment(10,0), 1.0);
      put(new Moment(10,5), 2.0);
      put(new Moment(10,7), 2.0);
      put(new Moment(10,10), 1.0);
    }};
    
    public ValueMapAppliance()
    {
        state = ApplianceState.Waiting;
    }
    @Override
    public double getConsum(Moment since, Moment until) 
    {
        double consum = 0;
        for( Moment moment : consumAt.keySet())
        {
            if( moment.isBetween(since, until))
                consum += consumAt.get(moment);
        }
        return consum;
    }

    @Override
    public ApplianceState getState() 
    {
        return state;
    }
}
