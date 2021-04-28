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
	
/*	public void addTransitionRelation(State currentState, ArrayList<AtomicProp> input, State destinationState)
	{
		Transition newTransition = new Transition(currentState, input, destinationState);
		transitionFunction.add(newTransition);
	}*/
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
			if(!acceptStates.contains(state))
			{
				acceptStates_temp.add(state);
				state.setAccept(true);
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
		ArrayList<State> tmp = new ArrayList<State>();
		for(State state: states)
		{
			for(Transition trans: transitionFunction)
				if(trans.getCurrentState().equals(state))
					trans.getDestinationStates().forEach(dest ->{
						if(!tmp.contains(dest))
							tmp.add(dest);
						});
			adjList.put(state, tmp);
			parent.put(state, null);
			visited.put(state, false);
			tmp.clear();
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
        while (!source.equals(detenation)) 
        {
            // add the destination to the list and store the parent
            // of the node in the destination since parent
            // stores the node through which
            // the current node has been reached
            path.addFirst(detenation);
            System.out.print(detenation.getName() + " <- "); //for testing
            detenation = parent.get(detenation);
        }
        path.addFirst(detenation);
        System.out.println(detenation.getName());   //for testing
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
  /*  	for(Transition trans: transitionFunction)
    		for(AtomicProp input: trans.getInput())
    			transitionFuncStr.append("( " + trans.getCurrentState().getName() + ", " + input.getName() + " ) -> " + trans.getDestinationState().getName() + ",\n\t\t\t");
   */	for(Transition trans: transitionFunction)
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
    	
    	/*Add a step of reducing states that cannot be reached*/
    	
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
    	Transition newTrans;
    	states.add(startState);
    	State state;
    	for(int i=0; i<states.size(); i++)
    		//for(State state: states)
	    	{
    			state = states.get(i);
	    		if(state.getContainedStates().isEmpty())
	    		{
	        		for( Transition trans: this.transitionFunction)
	        		{
	        			if(trans.getCurrentState().equals(state))
	        			{
	        				if(trans.getDestinationStates().size() > 1)
	        				{
	            	    		newState = new State(trans.getDestinationStates());
	            	    		if(!states.contains(newState))
	            	    			states.add(newState); 
	            	    		ArrayList<State> newDest = new ArrayList<State>();
	            	    		newDest.add(newState);
	            	    		newTrans = new Transition(trans.getCurrentState(), trans.getInput(), newDest);
	            	    		transitionFunction.add(newTrans);
	        				}
	        				else
	        				{
	        					if(!states.contains(trans.getDestinationStates().get(0)))
	        						states.add(trans.getDestinationStates().get(0)); 
	        					transitionFunction.add(trans);
	        				}
	        			}
	        		}
	    		}
	    		else
	    		{
	    			for(AtomicProp input: alphabet)
	    			{
	    	    		ArrayList<State> destStates = new ArrayList<State>();
	    	    		for(State containedState: state.getContainedStates())
	    	    		{
	    	    			destStates = union(destStates, this.getDestinationStates(containedState, input));
	    	    		}
	    	    		if(!destStates.isEmpty())
	    	    		{
	    	    			if(destStates.size()>1)
	    	    			{
	    	    				newState = new State(destStates);
		        	    		ArrayList<State> newDest = new ArrayList<State>();
		        	    		newDest.add(newState);
		        	    		if(!states.contains(newState))
		        	    			states.add(newState); 
		        	    		transitionFunction.add(new Transition(state, input, newDest));
	    	    			}
	    	    			else
	    	    			{
	    	    				if(!states.contains(destStates.get(0)))
	    	    					states.add(destStates.get(0)); 
	    	    				transitionFunction.add(new Transition(state, input, destStates));
	    	    			}	
	    	    		}
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

	
    /**
     * for test getPath method
     * @param args
     */
/*	public static void main(String[]args) 
	{
		State q0 = new State("q0"); 
		State q1 = new State("q1"); 
		State q2 = new State("q2"); 
		State q3 = new State("q3"); 
		State q4 = new State("q4"); 
		State q5 = new State("q5"); 
		State q6 = new State("q6"); 
		q0.addNeighbour(q1);
		q0.addNeighbour(q2);
		q1.addNeighbour(q3);
		q2.addNeighbour(q0);
		q2.addNeighbour(q5);
		q2.addNeighbour(q6);
		q3.addNeighbour(q1);
		q3.addNeighbour(q4);
		q4.addNeighbour(q2);
		q4.addNeighbour(q3);
		q5.addNeighbour(q4);
		q5.addNeighbour(q6);
		q6.addNeighbour(q5);
		ArrayList<State> states = new ArrayList<State>();
		states.add(q0);
		states.add(q1);
		states.add(q2);
		states.add(q3);
		states.add(q4);
		states.add(q5);
		states.add(q6);
		AtomicProp ap = new AtomicProp("a");
		ArrayList<AtomicProp> p= new ArrayList<AtomicProp>();
		p.add(ap);
		ArrayList<AtomicProp> alphabet = new ArrayList<AtomicProp>();
		alphabet.add(ap);
		ArrayList<State> acceptStates = new ArrayList<State>();
		acceptStates.add(q6);		
		ArrayList<Transition> transitionFunction = new ArrayList<Transition>();
		Automata automat = new Automata(q2, states, alphabet, acceptStates, transitionFunction);
		automat.addTransitionRelation(q0, p, q1);
		automat.addTransitionRelation(q0, p, q2);
		automat.addTransitionRelation(q1, p, q3);
		automat.addTransitionRelation(q2, p, q0);
		automat.addTransitionRelation(q2, p, q5);
		automat.addTransitionRelation(q2, p, q6);
		automat.addTransitionRelation(q3, p, q1);
		automat.addTransitionRelation(q3, p, q4);
		automat.addTransitionRelation(q4, p, q2);
		automat.addTransitionRelation(q4, p, q3);
		automat.addTransitionRelation(q5, p, q4);
		automat.addTransitionRelation(q5, p, q6);
		automat.addTransitionRelation(q6, p, q5);
		automat.getPath();
		System.out.println("\n" + automat.toString());
	}*/
    
/*    public static void main(String[]args) 
	{
		State q0 = new State("q0"); 
		State q1 = new State("q1"); 
		State q2 = new State("q2"); 
		q0.addNeighbour(q1);
		q0.addNeighbour(q2);
		q2.addNeighbour(q2);
		q1.addNeighbour(q1);
		ArrayList<State> states = new ArrayList<State>();
		states.add(q0);
		states.add(q1);
		states.add(q2);
		AtomicProp ap = new AtomicProp("a");
		AtomicProp ap2 = new AtomicProp("b");	
		ArrayList<AtomicProp> p= new ArrayList<AtomicProp>();
		p.add(ap);
		p.add(ap2);
		ArrayList<AtomicProp> p1= new ArrayList<AtomicProp>();
		p1.add(ap);
		ArrayList<AtomicProp> p2= new ArrayList<AtomicProp>();
		p2.add(ap2);
		ArrayList<AtomicProp> alphabet = new ArrayList<AtomicProp>();
		alphabet.add(ap);
		alphabet.add(ap2);
		ArrayList<State> acceptStates = new ArrayList<State>();
		acceptStates.add(q1);		
		ArrayList<Transition> transitionFunction = new ArrayList<Transition>();
		Automata automat = new Automata(q0, states, alphabet, acceptStates, transitionFunction);
		automat.addTransitionRelation(q0, p1, q1);
		automat.addTransitionRelation(q0, p2, q2);
		automat.addTransitionRelation(q2, p, q2);
		automat.addTransitionRelation(q1, p, q1);
		System.out.println(automat.toString());
		ArrayList<State> pstates = automat.getPowerset();
		pstates.forEach(s -> System.out.println(s.getName()));
		System.out.println();
		
		State p0 = new State("p0"); 
		State p11 = new State("p1"); 
		p0.addNeighbour(p11);
		p11.addNeighbour(p0);
		ArrayList<State> states2 = new ArrayList<State>();
		states2.add(p0);
		states2.add(p11);
		ArrayList<AtomicProp> alphabet2 = new ArrayList<AtomicProp>();
		alphabet2.add(ap);
		alphabet2.add(ap2);
		ArrayList<State> acceptStates2 = new ArrayList<State>();
		acceptStates2.add(p11);		
		ArrayList<Transition> transitionFunction2 = new ArrayList<Transition>();
		Automata automat2 = new Automata(p0, states2, alphabet2, acceptStates2, transitionFunction2);
		automat2.addTransitionRelation(p0, p, p11);
		automat2.addTransitionRelation(p11, p, p0);
		System.out.println(automat2.toString());
		
		System.out.println(automat.getIntersection(automat2).toString());
	}*/
	
    public static void main(String[]args) 
	{
		State q0 = new State("q0"); 
		State q1 = new State("q1"); 
		State q2 = new State("q2"); 
		q0.addNeighbour(q1);
		q1.addNeighbour(q2);
		q2.addNeighbour(q0);
		q2.addNeighbour(q1);
		ArrayList<State> states = new ArrayList<State>();
		states.add(q0);
		states.add(q1);
		states.add(q2);
		AtomicProp a = new AtomicProp("a");
		AtomicProp b = new AtomicProp("b");	
		ArrayList<AtomicProp> alphabet = new ArrayList<AtomicProp>();
		alphabet.add(a);
		alphabet.add(b);
		ArrayList<State> acceptStates = new ArrayList<State>();
		acceptStates.add(q1);		
		ArrayList<Transition> transitionFunction = new ArrayList<Transition>();
		Automata automat = new Automata(q0, states, alphabet, acceptStates, transitionFunction);
		automat.addTransitionRelation(q0, a, q1);
		automat.addTransitionRelation(q1, a, q1);
		automat.addTransitionRelation(q1, a, q2);
		automat.addTransitionRelation(q2, a, q0);
		automat.addTransitionRelation(q2, a, q2);
		automat.addTransitionRelation(q2, b, q1);
		System.out.println(automat.toString());
		System.out.println();
		Automata dfa = automat.convertToDeterministic();
		System.out.println(automat.toString());
		System.out.println(dfa.toString());
	}
}
