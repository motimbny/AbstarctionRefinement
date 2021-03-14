package entities;

import java.util.ArrayList;

public class complexState 
{
	private String name;
	private ArrayList<state> containingStates; 
	private ArrayList<complexState> neighbours;
	public complexState(String name)
	{
		this.name=name;
		containingStates=new ArrayList<state>();
		neighbours=new ArrayList<complexState>();
	}
	public complexState(String name,ArrayList<state> containingStates,ArrayList<complexState> neighbours)
	{
		this.name=name;
		this.containingStates=containingStates;
		this.neighbours=neighbours;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<state> getContainingStates() {
		return containingStates;
	}
	public void setContainingStates(ArrayList<state> containingStates) {
		this.containingStates = containingStates;
	}
	public ArrayList<complexState> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(ArrayList<complexState> neighbours) {
		this.neighbours = neighbours;
	}
	 public void removeNeighbour(complexState toRemove)
     {
    	 neighbours.remove(toRemove);
     }
     public void addNeighbour(complexState toAdd)
     {
    	 neighbours.add(toAdd);
     }

}
