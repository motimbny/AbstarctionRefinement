package entities;

import java.util.ArrayList;

public class State implements Cloneable
{
	 private String name;
	 private ArrayList<State> neighbors;
	 private boolean accept = false;
	 private ArrayList<State> containedStates = new ArrayList<State>();
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
     public State(String name, boolean accept)
	 {
		 this.name=name;
    	 this.neighbors=new ArrayList<State>();
    	 this.accept = accept;
	 }
     public State(String name, ArrayList<State> neighbors, boolean accept) 
     {
		this.name = name;
		this.neighbors = neighbors;
		this.accept = accept;
	}
     public State(ArrayList<State> containedStates) 
     {
    	 name = "";
    	 for(State s: containedStates)
    		 name += s.getName();
 		setContainedStates(containedStates);
	}
	public String getName() {
 		return name;
 	}
 	public ArrayList<State> getNeighbours() {
 		return neighbors;
 	}
 	public void setNeighbours(ArrayList<State> neighbours) {
 		this.neighbors = neighbours;
 	}
     public ArrayList<State> getContainedStates() {
		return containedStates;
	}
	public void setContainedStates(ArrayList<State> containedStates) {
		this.containedStates = containedStates;
	}
	public boolean isAccept() {
		return accept;
	}
	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	public void removeNeighbour(State toRemove)
     {
    	 neighbors.remove(toRemove);
     }
     public void addNeighbour(State toAdd)
     {
    	 neighbors.add(toAdd);
     }
     
     /**
      * Depends only on state name
      */
     @Override
     public int hashCode() 
     {
         final int prime = 31;
         int result = 1;
         result = prime * result + name.hashCode();  
         return result;
     }
     
     /**
      *  Compare only state names
      */
     @Override
     public boolean equals(Object o)
     {
    	// If the object is compared with itself then return true  
         if (o == this)
             return true;
         /* Check if o is an instance of State or not
           "null instanceof [type]" also returns false */
         if (!(o instanceof State)) 
             return false;
         // typecast o to Complex so that we can compare data members 
         State s = (State) o;
         // Compare the name of the states and return accordingly 
         return name.equals(s.getName());
     }
     
	@SuppressWarnings("unchecked")
	public State clone()
     {
    	 State clone = null;
    	 try {
    		 clone = (State)super.clone();
    		 clone.setNeighbours((ArrayList<State>)this.getNeighbours().clone());
    		 clone.setContainedStates((ArrayList<State>)this.getContainedStates().clone());
    	 }
    	 catch(CloneNotSupportedException e) {
    		 throw new RuntimeException(e);
    	 }
    	 return clone;
     }
}
