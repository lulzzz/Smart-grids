
package Model.Interfaces;

import Model.Moment;
import java.io.*;
import java.util.ArrayList;
import seas3.core.*;

public interface IBid 
{
    public PiecewiseLinearValuation toPLV();
    public void develop( Moment since, Moment until, double baseConsum, IBattery battery, IDistributor distributor, ArrayList<IAppliance> appliances);
    public void writePlotData( String plotFile, PrintWriter writer );
    public void setTrades( ArrayList<Double> trades );
}
