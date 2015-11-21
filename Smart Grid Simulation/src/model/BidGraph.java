
package model;

import java.io.*;
import java.util.Locale;

public class BidGraph 
{
    public static final float endesa = 2;
    private float tightness = 30;
    private float minX = 1;
    private float maxX = 10;
    private float sellX = 4;
    
    public void saveGraphData( String name ) 
    {
        try 
        {
            PrintWriter writer = new PrintWriter("output/graph.txt", "UTF-8");
            
            writer.print(String.format(Locale.US,
                    
                "set terminal png %n" +
                    
                "set output '%s.png' %n" +
                        
                "f(x) = x > %f ? %f*x : 1/0 %n"+
                        
                "g(x) = x > %f ? %f*(%f+%f*log((x-%f)/%f + 1)) : 1/0 %n"+
                
                "h(x) = (x > %f && x < %f) ? g(x) : 1/0 %n"+
                
                       
                "plot [%f:%f] f(x) with filledcurve y1=0, g(x) with filledcurve y1=0, h(x) with filledcurve y1=0",
               
                name, 
                minX, endesa,
                minX, endesa, minX, tightness, minX, tightness,
                minX, sellX,
                minX, maxX
            ));
            writer.close();
            System.out.println("file written");
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	
    }
}
