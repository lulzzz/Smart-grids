
package model;

import com.google.gson.Gson;
import seas3.core.IntervalLinearValuation;
import seas3.core.Participant;
import seas3.core.PiecewiseLinearValuation;

/**
 *
 * @author Martin
 */
public class House
{
    public static int totalHouses = 0;
    public int id;
    public float[] position;
    public float minX;
    public float maxX;
    public float necessity;
    public Distributor distributor;
    
    public House(float[] position)
    {
        totalHouses++;
        
        this.id = totalHouses;
        this.position = position;
    }

    Participant getParticipant(int time) 
    {
        PiecewiseLinearValuation plv = new PiecewiseLinearValuation();
        
        
        double x0,x1,y0,y1,slope,intercept;
        double step = (maxX - minX) / 10;
        for( x0 = minX; x0 < maxX; x0 += step)
        {
            y0 = curve(x0);
            x1 = x0 + step;
            y1 = curve(x1);
            
            slope = slope(x0,y0,x1,y1);
            intercept = intercept(x0,y0,slope);
            
            IntervalLinearValuation ilv = new IntervalLinearValuation(x0, x1, slope, intercept);
            
            plv.add(ilv);
        }
        
        
        Participant participant = new Participant( id, plv);
        
        System.out.println(participant);
        
        return participant;
    }
    
    public double curve(double x)
    {
        return distributor.price * ( minX + necessity * Math.log( x - minX) / necessity + 1);
    }
    
    public double slope( double x0, double y0, double x1, double y1)
    {
        return (y1-y0)/(x1-x0);
    }
    
    public double intercept( double x, double y, double slope )
    {
        return y - x*slope;
    }
    
    
}
