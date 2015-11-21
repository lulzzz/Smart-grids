package model;

import java.util.ArrayList;
import seas3.core.*;

public class Simulation 
{
        private City city;
        
	private ArrayList<SimulationFrame> frames;
	
	public Simulation( City city, int steps )
	{
            this.city = city;
            frames = new ArrayList<>(steps);
	}
	
	public void AddFrame(SimulationFrame frame)
	{
            frames.add(frame);
	}
	
	public String toJSON()
	{
            String res = "{ \"assignments\" : [";
            for(int i = 0; i < frames.size(); i++)
            {
                    SimulationFrame frame = frames.get(i);
                    res += String.format("{\"%d\":%s},", i, frame.toJSON());
            }
            res = res.substring(0, res.lastIndexOf(",")) + "]}";


            return res;
	}
	
	public String toString()
	{
            String res = "";

            for(int i = 0; i < frames.size(); i++)
            {
                    res += "Step " + i + "\n";
                    res += frames.get(i).toString() + "\n";
            }

            return res;
	}

    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
