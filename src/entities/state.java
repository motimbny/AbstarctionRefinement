package entities;

import java.util.ArrayList;
import java.util.List;

public class state
{
	 String name;
	 List<state> neighbours;
	 public state(String name)
	 {
		 this.name=name;
    	 this.neighbours=new ArrayList<state>();
	 }
     public state(String name,ArrayList<state> neighbours)
     {
    	 this.name=name;
    	 this.neighbours=neighbours;    	 
     }
     public void removeNeighbour(state toRemove)
     {
    	 neighbours.remove(toRemove);
     }
     public void addNeighbour(state toAdd)
     {
    	 neighbours.add(toAdd);
     }
}
