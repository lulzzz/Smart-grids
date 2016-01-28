
package Model.Interfaces;

import Model.Core.Distributor;
import Model.Core.Moment;
import java.io.*;
import java.util.*;
import seas3.core.*;

public interface IBiddingStrategy 
{
    
    public void setBid( Moment moment, double minX, double maxX, Distributor distributor, ArrayList<IAppliance> appliances);
    public PiecewiseLinearValuation toPLV();
    public void processResults( HashMap<Double,  TraderType> trades );
    public void writePlotData( String plotFile, PrintWriter writer );
    
    public enum TraderType{ House, Distributor }
}
