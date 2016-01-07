
package Model.Interfaces;

import Model.Core.Moment;
import java.io.*;
import java.util.*;
import seas3.core.*;

public interface IBid 
{
    public PiecewiseLinearValuation toPLV();
    public void develop( Moment moment, double minX, double maxX, IDistributor distributor, ArrayList<IAppliance> appliances);
    public void writePlotData( String plotFile, PrintWriter writer );
    public void setTrades( HashMap<Double,  TraderType> trades );
    
    public enum TraderType{ House, Distributor }
}
