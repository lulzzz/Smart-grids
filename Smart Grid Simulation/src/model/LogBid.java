
package Model;

import com.google.gson.annotations.*;
import java.io.Writer;
import java.util.*;
import java.util.function.*;
import seas3.core.*;

public class LogBid implements IBid 
{    
    public DoubleUnaryOperator curve; // funcion que va cambiando con el tiempo
    
    private static final int resolution = 10;
    
    @Expose
    public ArrayList<Double> trades;
    
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
            
            y0 = curve( x0 );
            y1 = curve( x1 );
            
            slope = (y1-y0)/(x1-x0);
            intercept = y0 - slope*x0;
            
            IntervalLinearValuation ilv = new IntervalLinearValuation(x0, x1, slope, intercept);
            
            plv.add(ilv);
        }
        
        return plv;
    }
    
    public double curve( double x )
    {
        return Math.signum(x) * linear * ( contactX + tightness * Math.log( (Math.abs(x) - minX) / tightness + 1));
    }
    
    public void addTrade( double at )
    {
        trades.add(at);
    }

    @Override
    public void develop(double baseConsum, IBattery battery) 
    {
        
    }

    @Override
    public void writePlotData(Writer writer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
