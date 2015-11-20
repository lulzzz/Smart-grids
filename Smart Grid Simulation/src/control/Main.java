
package control;

import java.io.*;
import javax.swing.JFrame;
import model.*;
import seas3.core.*;

public class Main extends JFrame
{
    Participant p;
    
    public static void main(String[] args)
    {
        new Main().run();
    }
    
    private void run()
    {
        Simulation simulation = new Simulation(new City(),24);
        BidGraph graph = new BidGraph();
        graph.saveGraphData();
    }

    
    
}