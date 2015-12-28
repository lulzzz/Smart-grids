
package View;

import Model.Moment;
import java.io.IOException;

public class Plotter 
{
    public static void plotBids( String outputFolder, int frames, Moment startingMoment, int timeStep ) throws IOException
    {
        for( int frame = 0; frame <= frames; frame++ )
        {
            Runtime.getRuntime().exec("gnuplot \"" + outputFolder + "\\frame"+startingMoment.toString()+".txt\"");
            startingMoment.advance(5);
        }
    }
}
