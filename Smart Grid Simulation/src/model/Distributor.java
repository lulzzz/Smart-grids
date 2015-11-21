
package model;

import java.io.PrintWriter;
import seas3.core.Participant;


public class Distributor extends Prosumer
{
    public double[] defaultRate = new double[]
    {
        0.08949, 0.08865, 0.08121, 0.07561, 0.07401, 0.07265,
        0.07515, 0.08098, 0.09575, 0.10478, 0.10663, 0.10421,
        0.10268, 0.10031, 0.09254, 0.08972, 0.08958, 0.10057,
        0.10838, 0.10641, 0.10849, 0.10353, 0.10361, 0.09686
    };
    
    public double[] twoPeriodsRate = new double[]
    {
        0.04238, 0.04081, 0.03290, 0.02736, 0.02552, 0.02405,
        0.02611, 0.03155, 0.04536, 0.05392, 0.05568, 0.05338,
        0.12155, 0.11915, 0.11119, 0.10827, 0.10817, 0.11925,
        0.12707, 0.12504, 0.12714, 0.12214, 0.05468, 0.04887
    };
    
    public static double[] testRate = new double[]
    {
        0,1,2,3,4,5,6,7,8,9,10,11,12,0,1,2,3,4,5,6,7,8,9,10,11,12
    };
    
    public double[] rate;
    
    public Distributor( float[] position, double[] rate )
    {
        super(position);
        this.rate = rate;
    }

    @Override
    public void writePlotData(PrintWriter writer, String plotName, int frame) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Participant getParticipant(int frame) 
    {
        bid = new Bid(-10,10,x -> rate[frame] * x );
        
        return new Participant(id, bid.toPLV());
    }
}
