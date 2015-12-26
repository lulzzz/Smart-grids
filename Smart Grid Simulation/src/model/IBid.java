
package Model;

import java.io.*;
import seas3.core.*;

public interface IBid 
{
    public PiecewiseLinearValuation toPLV();
    public void develop( double baseConsum, IBattery battery );
    public void writePlotData( Writer writer );
}
