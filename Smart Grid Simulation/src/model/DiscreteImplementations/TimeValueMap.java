
package Model.DiscreteImplementations;

import Model.Moment;
import java.util.HashMap;

public class TimeValueMap 
{
    public HashMap<Moment,Double> map;

    public TimeValueMap( HashMap<Moment, Double> map )
    {
        this.map = map;
    }

    public void getLowerNearest(Moment moment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getMeanBetween(Moment since, Moment until) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getAddedValue(Moment since, Moment until) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    double getProgress(Moment until) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Moment getLowestMoment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
