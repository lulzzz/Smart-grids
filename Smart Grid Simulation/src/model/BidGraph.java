
package model;

import java.io.*;

public class BidGraph 
{
    public static final float endesa = 5;
    private float tightness = 30;
    private float minX = 1;
    private float maxX = 10;
    
    private double curve(float x)
    {
        return endesa*( minX + tightness * Math.log((Math.abs(x) - minX)/tightness + 1));
    }
    
    public void saveGraphData() 
    {
        try 
        {
            PrintWriter writer = new PrintWriter("output/graph.txt", "UTF-8");
            
            for(int i = 1; i <=10; i++)
            {
                writer.println(String.format("%d %.5f", i, curve(i)));
            }
            writer.close();
            System.out.println("file written");
            
            writer = new PrintWriter("output/linear.txt", "UTF-8");
            
            for(int i = 1; i <=10; i++)
            {
                writer.println(String.format("%d %.5f", i, endesa*i));
            }
            writer.close();
            System.out.println("file written");
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	
    }
}
