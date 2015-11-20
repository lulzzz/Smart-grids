package model;

import java.util.Collection;
import java.util.Map;

import seas3.core.*;

public class SimulationFrame {

	public Collection<Participant> participants;
	public Assignment assignment;
	
	public SimulationFrame(){}
	
	public SimulationFrame(Collection<Participant> collection, Assignment assignment)
	{
		this.participants = collection;
		this.assignment = assignment;
	}
	
	public String toString()
	{
		//return "Assignment: " + assignment.toString() + "\n";
		String res = "[ ";
		for(Map.Entry<Link, Double> e : assignment.entrySet())
		{
			if(e.getKey().source != e.getKey().dest)
			{
				res += e.getKey().toString() + "=" + e.getValue().toString() + " ";
			}				
		}
		return res + "]";
	}
	
	public String toJSON()
	{
		String res = "";
		
		//links
		res +="{\"links\":";
		res += "[";
		
		for(Map.Entry<Link, Double> e : assignment.entrySet())
		{
			if(e.getKey().source != e.getKey().dest)
			{
				res += String.format("{\"%s\":\"%s\"},", e.getKey().toString(), e.getValue().toString());
			}				
		}
		
		res = res.substring(0, res.lastIndexOf(",")) + "]";
		
		// plvs
		if(participants != null)
		{
		
			res += ",\"plvs\" : [";
			
			for(Participant p : participants)
			{
				int id =(int) p.getId();
				PiecewiseLinearValuation plv = p.getValuation();
				res += "{\""+id+"\":[";
				
				for(IntervalLinearValuation ilv : plv.getILVs())
				{
					res += "\""+ilv.toString() + "\",";
				}
				res = res.substring(0, res.lastIndexOf(",")) + "]},";
			}
			
			res = res.substring(0,res.lastIndexOf(",")) + "]";
		}
		
		return res +"}";
	}
}
