package entities;

import java.util.ArrayList;

public class state
{
	 private String name;
	 private ArrayList<state> neighbours;
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
     public String getName() {
 		return name;
 	}
 	public void setName(String name) {
 		this.name = name;
 	}
 	public ArrayList<state> getNeighbours() {
 		return neighbours;
 	}
 	public void setNeighbours(ArrayList<state> neighbours) {
 		this.neighbours = neighbours;
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
