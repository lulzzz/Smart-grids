
package model;

import java.util.concurrent.Callable;
import java.util.function.DoubleUnaryOperator;
import static model.House.plotResolution;
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
    public double tradeX;
    public double contactX;
    public DoubleUnaryOperator curve;
    
    public Bid( double minX, double maxX, DoubleUnaryOperator curve )
    {
        this.minX = minX;
        this.maxX = maxX;
        this.curve = curve;
    }
    
    public PiecewiseLinearValuation toPLV()
    {
        PiecewiseLinearValuation plv = new PiecewiseLinearValuation();
        
        
        // Build plv by concatenating ilv's
        // The ilv's are built linearizing the curve function
        
        double x0,x1,y0,y1,slope,intercept;
        
        double step = (maxX - minX) / plotResolution;
        
        for( int i = 0; i < plotResolution; i++ )
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
    
    
    
    public void toFile()
    {
        
    }
}
