
package View;

import java.io.IOException;

public class Plotter 
{
    public static void plotBids( String outputFolder, int frames ) throws IOException
    {
        for( int frame = 0; frame < frames; frame++ )
        {
            Runtime.getRuntime().exec("gnuplot \"" + outputFolder + "\\frame"+frame+".txt\"");
        }
    }
}
