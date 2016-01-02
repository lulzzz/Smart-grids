
package View;

import Model.Moment;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Plotter 
{
    public static void plotBids( String outputFolder, int frames, Moment startingMoment, int timeStep ) throws IOException
    {
        for( int frame = 0; frame <= frames; frame++ )
        {
            // Execute gnuplot file
            Runtime.getRuntime().exec("gnuplot \"" + outputFolder + "\\frame"+startingMoment.toString()+".txt\"");
            // Delete the gnuplot script file
            //new File(outputFolder + "\\frame"+startingMoment.toString()+".txt").delete();
            
            startingMoment.advance(timeStep);
        }
    }
}
