
package model;

import java.util.ArrayList;

public class Appliance 
{
    private ArrayList<Double> energyCost;
    private int obligatoryAfter;
    private int framesDone;
    
    public boolean commited;
    
    public Appliance(ArrayList<Double> energyCost, int obligatoryAfter) 
    {
        this.energyCost = energyCost;
        this.obligatoryAfter = obligatoryAfter;
        this.framesDone = 0;
        this.commited = false;
    }
    
    public boolean hasEnded()
    {
        return framesDone >= energyCost.size();
    }
   
    public double getNextEnergyCost()
    {
        return energyCost.get(framesDone);
    }
    
    public double getPressure( int framesLeft )
    {
        return commited ? 1.0/ obligatoryAfter : 0;
    }
    
    public void commit()
    {
        framesDone = 1;
        commited = true;
    }
    
    public void passFrame()
    {
        if( commited )
        {
            framesDone++;
        }
        else
        {
            obligatoryAfter--;
        }
    }
}
