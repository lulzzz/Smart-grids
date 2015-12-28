
package Model;

import java.util.HashMap;

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
    
    public static double[] testRate = new double[]
    {
        1,2,3,4,5,6,7,8,9,10,11,12,1,2,3,4,5,6,7,8,9,10,11,12
    };
    
    public static HashMap<Moment, Double> consumTV = new HashMap<Moment,Double>()
    {{
      put(new Moment(10,0), 1.0);
      put(new Moment(10,5), 2.0);
      put(new Moment(10,7), 2.0);
      put(new Moment(10,10), 1.0);
    }};
    
    public static HashMap<Integer, Integer> cloudsAt = new HashMap<Integer,Integer>()
    {{
      put(0, 56);
      put(3, 76);
      put(6, 88);
      put(9, 88);
      put(12, 100);
      put(15, 88);
      put(18, 36);
      put(21, 92);  
    }};
}
