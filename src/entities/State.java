package entities;

import java.util.ArrayList;

public class State
{
	 private String name;
	 private ArrayList<State> neighbors;
	 public State(String name)
	 {
		 this.name=name;
    	 this.neighbors=new ArrayList<State>();
	 }
     public State(String name,ArrayList<State> neighbours)
     {
    	 this.name=name;
    	 this.neighbors=neighbours;    	 
     }
     public String getName() {
 		return name;
 	}
 	public void setName(String name) {
 		this.name = name;
 	}
 	public ArrayList<State> getNeighbours() {
 		return neighbors;
 	}
 	public void setNeighbours(ArrayList<State> neighbours) {
 		this.neighbors = neighbours;
 	}
     public void removeNeighbour(State toRemove)
     {
    	 neighbors.remove(toRemove);
     }
     public void addNeighbour(State toAdd)
     {
    	 neighbors.add(toAdd);
     }
}
