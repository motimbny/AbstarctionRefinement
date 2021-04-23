package entities;

import java.util.ArrayList;

public class ComplexState implements Cloneable
{
	private String name;
	private ArrayList<State> containingStates; 
	private ArrayList<ComplexState> neighbours;
	private ArrayList<AtomicProp> labels;
	public ComplexState(String name)
	{
		this.name=name;
		containingStates=new ArrayList<State>();
		neighbours=new ArrayList<ComplexState>();
		labels = new ArrayList<AtomicProp>();
	}
	public ComplexState(String name,ArrayList<State> containingStates,ArrayList<ComplexState> neighbours, ArrayList<AtomicProp> labels)
	{
		this.name=name;
		this.containingStates=containingStates;
		this.neighbours=neighbours;
		this.labels = labels;
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
     
     public void addContainingStates(State state)
     {
    	 containingStates.add(state);
     }
     public void addContainingStates(ArrayList<State> states)
     {
    	 containingStates.addAll(states);
     }     
     
     public ArrayList<AtomicProp> getLabels() {
		return labels;
	}
	public void setLabels(ArrayList<AtomicProp> labels) {
		this.labels = labels;
	}
	public void addLabel(AtomicProp label)
	{
		labels.add(label);
	}
	public boolean compareLabels(ArrayList<AtomicProp> other)
	{
	    if(other.size()!=this.labels.size())
	    	return false;
	    else
	    {
	    	for(int i=0;i<this.labels.size();i++)
	    		if(!other.contains(this.labels.get(i)))
	    			return false;
	    }
	    return true;
	}
	public boolean isAccept()
     {
    	 for(State s: containingStates)
    		 if(s.isAccept())
    			 return true;
    	 return false;
     }
	
	public State convertToState()
	{
		State state = new State(name);
		if(this.isAccept())
			state.setAccept(true);
		return state;
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
         /* Check if o is an instance of ComplexState or not
           "null instanceof [type]" also returns false */
         if (!(o instanceof ComplexState)) 
             return false;
         // typecast o to Complex so that we can compare data members 
         ComplexState cs = (ComplexState) o;
         // Compare the name of the states and return accordingly 
         return name.equals(cs.getName());
     }
     
     public Object clone() throws CloneNotSupportedException
     {
		return super.clone(); 
     }

}
