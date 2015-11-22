
package model;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.DoubleUnaryOperator;
import seas3.core.IntervalLinearValuation;
import seas3.core.PiecewiseLinearValuation;

/**
 *
 * @author Martin
 */
public class Bid 
{
    public double minX;
    public double maxX;
    public ArrayList<Double> trades;
    public double contactX;
    public DoubleUnaryOperator curve;
    public int resolution;
    
    public Bid( double minX, double maxX, DoubleUnaryOperator curve, int resolution )
    {
        this.minX = minX;
        this.maxX = maxX;
        this.curve = curve;
        this.resolution = resolution;
        this.trades = new ArrayList<>();
        addTrade(3);
        
        if( minX < 0 && maxX < 0 ) contactX = maxX;
        else if(minX>0 && maxX > 0) contactX = minX;
        else contactX = 0;
    }
    
    public PiecewiseLinearValuation toPLV()
    {
        PiecewiseLinearValuation plv = new PiecewiseLinearValuation();
        
        
        // Build plv by concatenating ilv's
        // The ilv's are built linearizing the curve function
        
        double x0,x1,y0,y1,slope,intercept;
        
        double step = (maxX - minX) / resolution;
        
        for( int i = 0; i < resolution; i++ )
        {
            x0 = minX + i*step;
            x1 = x0 + step;
            
            y0 = curve.applyAsDouble( x0 );
            y1 = curve.applyAsDouble( x1 );
            
            slope = (y1-y0)/(x1-x0);
            intercept = y0 - slope*x0;
            
            IntervalLinearValuation ilv = new IntervalLinearValuation(x0, x1, slope, intercept);
            
            plv.add(ilv);
        }
        
        return plv;
    }
    
    public void addTrade( double at )
    {
        trades.add(at);
    }
}
