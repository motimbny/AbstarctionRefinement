package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Automata 
{
	private State startState;
	private ArrayList<State> states;
	private ArrayList<AtomicProp> alphabet;
	private ArrayList<State> acceptStates;
	private ArrayList<Transition> transitionFunction; 
   
    public Automata(State startState, ArrayList<State> states, ArrayList<AtomicProp> alphabet,
			ArrayList<State> acceptStates, ArrayList<Transition> transitionFunction) {
		this.startState = startState;
		this.states = states;
		this.alphabet = alphabet;
		this.acceptStates = acceptStates;
		this.transitionFunction = transitionFunction;
	}
   
    public State getStartState() {
		return startState;
	}
	public void setStartState(State startState) {
		this.startState = startState;
	}
	public ArrayList<State> getStates() {
		return states;
	}
	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	public ArrayList<AtomicProp> getAlphabet() {
		return alphabet;
	}
	public void setAlphabet(ArrayList<AtomicProp> alphabet) {
		this.alphabet = alphabet;
	}
	public ArrayList<State> getAcceptStates() {
		return acceptStates;
	}
	public void setAcceptStates(ArrayList<State> acceptStates) {
		this.acceptStates = acceptStates;
	}
	public ArrayList<Transition> getTransitionFunction() {
		return transitionFunction;
	}
	public void setTransitionFunction(ArrayList<Transition> transitionFunction) {
		this.transitionFunction = transitionFunction;
	}
	
	public void addTransitionRelation(State currentState, AtomicProp input, State destinationState)
	{
		Transition trans = getTransition(transitionFunction, currentState, input);
		if(trans != null)
			trans.getDestinationStates().add(destinationState);
		else
			transitionFunction.add(new Transition(currentState, input, destinationState));
	}
	
	public ArrayList<State> getDestinationStates(State src, AtomicProp input)
	{
		ArrayList<State> destinationStates = new ArrayList<State>();
		for(Transition trans: transitionFunction)
			if(trans.getCurrentState().equals(src) && trans.getInput().equals(input))
			{
				destinationStates = trans.getDestinationStates();
				break;
			}
		return destinationStates;
	}

	public Automata getComplementAutomata()
	{
		ArrayList<State> acceptStates_temp = new ArrayList<State>();
		for(State state: states)
		{
			if(!acceptStates.contains(state))
			{
				acceptStates_temp.add(state);
				state.setAccept(true);
			}
			else
				state.setAccept(false);
		}		
		Automata complementAutomata = new Automata(startState, states, alphabet, acceptStates_temp, transitionFunction);
		return complementAutomata;
	}
	
	/**
	 * Finding path in automata using BFS algorithm
	 * @return list of all the path in the automata
	 */
	public ArrayList<LinkedList<State>> getPath()
	{
		HashMap<State, List<State>> adjList = new HashMap<>();
		HashMap<State, State> parent = new HashMap<State, State>();
		State que[] = new State[states.size()];
		Arrays.fill(que, null);
		HashMap<State, Boolean> visited = new HashMap<State, Boolean>();
		ArrayList<State> tmp;
		for(State state: states)
		{
			tmp = new ArrayList<State>();
			for(Transition trans: transitionFunction)
				if(trans.getCurrentState().equals(state))
				{
					ArrayList<State> destinationSrates = trans.getDestinationStates();
					for(int i=0; i<destinationSrates.size(); i++)
						if(!tmp.contains(destinationSrates.get(i)))
							tmp.add(destinationSrates.get(i));		
				}
			adjList.put(state, tmp);
			parent.put(state, null);
			visited.put(state, false);
		}
		int front = -1, rear = -1;
        visited.put(startState, true);
        parent.put(startState, startState);
     // To add any non visited node we will increment the rear
        // and add that vertex to the end of the array (enqueuing)
        que[++rear] = startState;
        State k;
        // The loop will continue till the rear and front are equal
        while (front != rear)
        {
        	// Here Dequeuing is nothing but to increment the front int
            k = que[++front];
            List<State> list = adjList.get(k);
            for (int i = 0; i < list.size(); i++) 
            {
                State j = list.get(i);
                if (!visited.get(j)) 
                {
                    que[++rear] = j;
                    visited.put(j, true);
                    parent.put(j, k);
                }
            }
        }
        ArrayList<LinkedList<State>> allPath = new ArrayList<>();
        for(State state: states)
        {
        	LinkedList<State> path = new LinkedList<>(insertPathToList(parent, startState, state));
        	if(path.size() != 1 || path.get(0).equals(startState))
        		allPath.add(path);
        }
		return allPath;
	}
	
	// Function to save the path from
    private LinkedList<State> insertPathToList(HashMap<State, State> parent, State source, State detenation)
    {
    	LinkedList<State> path = new LinkedList<>();
        // The while loop will stop only when the
        // destination and the source node become equal
        while (!source.equals(detenation) ) 
        {
            // add the destination to the list and store the parent
            // of the node in the destination since parent
            // stores the node through which
            // the current node has been reached
        	if(parent.get(detenation) == null)
        		break;
            path.addFirst(detenation);
            detenation = parent.get(detenation);
        }
        path.addFirst(detenation);
        return path;
    }
    
    /**
     * Returns a string that contains all the characteristics of the automata.
     */
    public String toString()
    {
    	int tempIndex;
    	StringBuilder statesStr = new StringBuilder("States: { ");
    	for(State state: states)
    		statesStr.append(state.getName() + ", ");
    	tempIndex = statesStr.lastIndexOf(",");
    	if(tempIndex != -1)
    		statesStr.deleteCharAt(statesStr.lastIndexOf(","));
    	statesStr.append("}\n");
    	StringBuilder alphabetStr = new StringBuilder("Alphabet: { ");
    	for(AtomicProp input: alphabet)
    		alphabetStr.append(input.getName() + ", ");
    	tempIndex = alphabetStr.lastIndexOf(",");
    	if(tempIndex != -1)
    		alphabetStr.deleteCharAt(alphabetStr.lastIndexOf(","));
    	alphabetStr.append("}\n");
    	String initStateStr = new String("Initial state: " + startState.getName() + "\n");
    	StringBuilder acceptStatesStr = new StringBuilder("Accept states: { ");
    	for(State state: acceptStates)
    		acceptStatesStr.append(state.getName() + ", ");
		tempIndex = acceptStatesStr.lastIndexOf(",");
    	if(tempIndex != -1)
    		acceptStatesStr.deleteCharAt(acceptStatesStr.lastIndexOf(","));
    	acceptStatesStr.append("}\n");    	
    	StringBuilder transitionFuncStr = new StringBuilder("Transition function: { ");
    	for(Transition trans: transitionFunction)
	   		for(State dest: trans.getDestinationStates())
	   			transitionFuncStr.append("( " + trans.getCurrentState().getName() + ", " + trans.getInput().getName() + " ) -> " + dest.getName() + ",\n\t\t\t");
    	
    	tempIndex = transitionFuncStr.lastIndexOf(",");
    	if(tempIndex != -1)    	
    		transitionFuncStr.delete(tempIndex, transitionFuncStr.length()-1);
    	transitionFuncStr.append("}\n");   
    	String result = statesStr.toString() + alphabetStr.toString() + initStateStr + acceptStatesStr.toString() + transitionFuncStr.toString();
    	return result;
    }
    
    public Automata getIntersection(Automata b)
    {
    	State intersectState;
    	boolean isAccept = false;
    	State startState;
    	ArrayList<State> states = new ArrayList<State>();
    	ArrayList<AtomicProp> alphabet;
    	ArrayList<State> acceptStates = new ArrayList<State>();
    	ArrayList<Transition> transitionFunction = new ArrayList<Transition>(); 
    	for(State stateA: this.states)
    		for(State stateB: b.getStates())
    		{
    			if(stateA.isAccept() && stateB.isAccept())
    				isAccept = true;
    	    	ArrayList<State> containedStates = new ArrayList<State>();
    			containedStates.add(stateA);
    			containedStates.add(stateB);
    			intersectState = new State(containedStates);
    			intersectState.setAccept(isAccept);
    			if(isAccept)
    				acceptStates.add(intersectState);
    			states.add(intersectState);
    			isAccept = false;
    		}
    	ArrayList<State> containedStates = new ArrayList<State>();
    	containedStates.add(this.startState);
    	containedStates.add(b.getStartState());
    	startState = new State(containedStates);
    	startState.setAccept(this.startState.isAccept() && b.getStartState().isAccept());
    	alphabet = b.getAlphabet();                //intersection of the alphabet lists
    	alphabet.retainAll(this.getAlphabet()); 
    	Transition trans;
    	for(State state1: states) 
    	{
    		for(State state2: states)
    		{
    			for(AtomicProp input: alphabet)
    			{
    				if(this.getDestinationStates(state1.getContainedStates().get(0), input).contains(state2.getContainedStates().get(0)))
    					if(b.getDestinationStates(state1.getContainedStates().get(1), input).contains(state2.getContainedStates().get(1)))
    					{
    						trans = getTransition(transitionFunction, state1, input);
    						if(trans != null)
    							trans.getDestinationStates().add(state2);
    						else
	    						transitionFunction.add(new Transition(state1, input, state2));
    					}	
    			}
    		}
    	}
    	Automata intersection = new Automata(startState, states, alphabet, acceptStates, transitionFunction);
    	return intersection;
    }
    
    public Transition getTransition(ArrayList<Transition> transitions, State src, AtomicProp input)
    {
    	for(Transition trans: transitions)
    		if(trans.getCurrentState().equals(src) && trans.getInput().equals(input))
    			return trans;
		return null;
    }
    
    
	public Automata convertToDeterministic()
	{
    	State startState = this.startState;
    	ArrayList<State> states = new ArrayList<State>();
    	ArrayList<AtomicProp> alphabet = this.alphabet;
    	ArrayList<State> acceptStates = new ArrayList<State>();
    	ArrayList<Transition> transitionFunction = new ArrayList<Transition>(); 
    	State newState;
    	State bor = new State ("bor");
    	states.add(startState);
    	State state;
    	for(int i=0; i<states.size(); i++)
    	{
    		state = states.get(i);
    		for(AtomicProp input: alphabet)
    		{
    			ArrayList<State> destinationStates = this.getDestinationStates(state, input);
    			if(destinationStates.size() ==1)
    			{
    				Transition newTrans = new Transition(state, input, destinationStates.get(0));
    				transitionFunction.add(newTrans);
    				if(!states.contains(destinationStates.get(0)))
    					states.add(destinationStates.get(0));
    			}
    			else if(destinationStates.size() > 1)
    			{
    				newState = new State(destinationStates);
    	    		if(!states.contains(newState))
    	    			states.add(newState); 
    	    		transitionFunction.add(new Transition(state, input, newState));
    			}
    			else  //There is no transition from the current state with the current input
    			{
    				Transition newTrans = new Transition(state, input, bor);
    				transitionFunction.add(newTrans);
    				if(!states.contains(bor))
    					states.add(bor);
    			}
    		}
    	}
    	
    	/* accepted state of DFA will be state which has accepted state as its component*/
    	this.acceptStates.forEach(acceptState -> {
    		states.forEach(s ->{
        		if(s.equals(acceptState) || s.getContainedStates().contains(acceptState))
        		{
        			s.setAccept(true);
        			acceptStates.add(s);
        		}
        	});    		
    	});
		Automata deterministicAutomata = new Automata(startState, states, alphabet, acceptStates, transitionFunction);
		return deterministicAutomata;
	}

	public ArrayList<State> union(ArrayList<State> list1, ArrayList<State> list2)
	{
		Set<State> set = new HashSet<State>();
		set.addAll(list1);
		set.addAll(list2);
		return new ArrayList<State>(set);
		
	}
    
	public ArrayList<State> getPowerset()
	{
		ArrayList<State> pStates = new ArrayList<State>();
		String stateName = "";
		int set_size = states.size();
		long pow_set_size = (long)Math.pow(2, set_size);
		int counter, j;
		boolean isAccept = false;
	     
	        /*Run from counter 000..0 to
	        111..1*/
	        for(counter = 0; counter < pow_set_size; counter++)
	        {
	            for(j = 0; j < set_size; j++)
	            {
	                /* Check if jth bit in the
	                counter is set If set then
	                print jth state from set */
	                if((counter & (1 << j)) > 0)
	                {
	                	stateName += states.get(j).getName();   
	                	if(states.get(j).isAccept())
	                		isAccept = true;
	                }

	            }
	            pStates.add(new State(stateName, isAccept));
	            stateName = "";
	            isAccept = false;
	        }
			return pStates;
	}

}
