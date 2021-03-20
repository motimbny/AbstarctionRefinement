package entities;

import java.util.ArrayList;

public class ComplexState 
{
	private String name;
	private ArrayList<State> containingStates; 
	private ArrayList<ComplexState> neighbours;
	public ComplexState(String name)
	{
		this.name=name;
		containingStates=new ArrayList<State>();
		neighbours=new ArrayList<ComplexState>();
	}
	public ComplexState(String name,ArrayList<State> containingStates,ArrayList<ComplexState> neighbours)
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
	public ArrayList<State> getContainingStates() {
		return containingStates;
	}
	public void setContainingStates(ArrayList<State> containingStates) {
		this.containingStates = containingStates;
	}
	public ArrayList<ComplexState> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(ArrayList<ComplexState> neighbours) {
		this.neighbours = neighbours;
	}
	 public void removeNeighbour(ComplexState toRemove)
     {
    	 neighbours.remove(toRemove);
     }
     public void addNeighbour(ComplexState toAdd)
     {
    	 neighbours.add(toAdd);
     }

}
