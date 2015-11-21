
package model;

import java.util.ArrayList;

public class City 
{
    private ArrayList<House> houses;
    private ArrayList<Wire> wires;
    
    public City()
    {
        houses = new ArrayList<>();
    }
    
    public void buildExampleCity()
    {
        House house = new House();
    }

    public void saveJSON() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
