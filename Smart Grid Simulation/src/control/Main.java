
package control;

import seas3.core.Participant;
import seas3.core.PiecewiseLinearValuation;

public class Main
{
    Participant p;
    
    public static void main(String[] args)
    {
        new Main().run();
    }
    
    private void run()
    {
        p = new Participant(0,PiecewiseLinearValuation.discrete(0, 0));
        System.out.println(p);
    }
}