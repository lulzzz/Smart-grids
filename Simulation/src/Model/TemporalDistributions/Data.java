
package Model.TemporalDistributions;

import Model.Core.Moment;
import java.util.HashMap;
import java.util.TreeMap;

public class Data 
{
    public static double[] defaultRate = new double[]
    {
        0.08949, 0.08865, 0.08121, 0.07561, 0.07401, 0.07265,
        0.07515, 0.08098, 0.09575, 0.10478, 0.10663, 0.10421,
        0.10268, 0.10031, 0.09254, 0.08972, 0.08958, 0.10057,
        0.10838, 0.10641, 0.10849, 0.10353, 0.10361, 0.09686
    };
    
    public static double[] twoPeriodsRate = new double[]
    {
        0.04238, 0.04081, 0.03290, 0.02736, 0.02552, 0.02405,
        0.02611, 0.03155, 0.04536, 0.05392, 0.05568, 0.05338,
        0.12155, 0.11915, 0.11119, 0.10827, 0.10817, 0.11925,
        0.12707, 0.12504, 0.12714, 0.12214, 0.05468, 0.04887
    };
    
    public static DiscreteTemporalDistribution testRate = new DiscreteTemporalDistribution()
    {{
        put(new Moment(0,0), 0.0);
        put(new Moment(1,0), 1.0);
        put(new Moment(2,0), 2.0);
        put(new Moment(3,0), 3.0);
        put(new Moment(4,0), 4.0);
        put(new Moment(5,0), 5.0);
        put(new Moment(6,0), 6.0);
        put(new Moment(7,0), 7.0);
        put(new Moment(8,0), 8.0);
        put(new Moment(9,0), 9.0);
        put(new Moment(10,0), 10.0);
        put(new Moment(11,0), 11.0);
        
        put(new Moment(12,0), 12.0);
        put(new Moment(13,0), 13.0);
        put(new Moment(14,0), 14.0);
        put(new Moment(15,0), 15.0);
        put(new Moment(16,0), 16.0);
        put(new Moment(17,0), 17.0);
        put(new Moment(18,0), 18.0);
        put(new Moment(19,0), 19.0);
        put(new Moment(20,0), 20.0);
        put(new Moment(21,0), 21.0);
        put(new Moment(22,0), 22.0);
        put(new Moment(23,0), 23.0);
    }};
    
    public static DiscreteTemporalDistribution consumTV = new DiscreteTemporalDistribution()
    {{
      put(new Moment(10,0), 1.0);
      put(new Moment(10,5), 1.0);
      put(new Moment(10,10), 1.0);
    }};
    
    public static DiscreteTemporalDistribution cloudMap = new DiscreteTemporalDistribution()
    {{
      put(new Moment(0,0), 56.0);
      put(new Moment(3,0), 76.0);
      put(new Moment(6,0), 88.0);
      put(new Moment(9,0), 88.0);
      put(new Moment(12,0), 100.0);
      put(new Moment(15,0), 88.0);
      put(new Moment(18,0), 36.0);
      put(new Moment(21,0), 92.0);  
    }};
    
    public static final float defaultCapacity = 10f;
}
