package entities;

import java.util.ArrayList;

public class State
{
	 private String name;
	 private ArrayList<State> neighbors;
	 private boolean accept = false;
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
     public State(String name, ArrayList<State> neighbors, boolean accept) 
     {
		this.name = name;
		this.neighbors = neighbors;
		this.accept = accept;
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
}
