package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
	
	public void addTransitionRelation(State currentState, ArrayList<AtomicProp> input, State destinationState)
	{
		Transition newTransition = new Transition(currentState, input, destinationState);
		transitionFunction.add(newTransition);
	}

	public Automata getComplementAutomata()
	{
		ArrayList<State> acceptStates_temp = new ArrayList<State>();
		for(State state: states)
			if(!acceptStates.contains(state))
				acceptStates_temp.add(state);
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
		List<State> tmp;
		for(State state: states)
		{
			tmp = new ArrayList<State>();
			for(Transition trans: transitionFunction)
				if(trans.getCurrentState().equals(state))
					tmp.add(trans.getDestinationState());
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
    	statesStr.deleteCharAt(statesStr.length()-2);
    	statesStr.append("}\n");
    	StringBuilder alphabetStr = new StringBuilder("Alphabet: { ");
    	for(AtomicProp input: alphabet)
    		alphabetStr.append(input.getName() + ", ");
    	alphabetStr.deleteCharAt(alphabetStr.length()-2);
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
    		for(AtomicProp input: trans.getInput())
    			transitionFuncStr.append("( " + trans.getCurrentState().getName() + ", " + input.getName() + " ) -> " + trans.getDestinationState().getName() + ",\n\t\t\t");
    	transitionFuncStr.delete(transitionFuncStr.length()-5, transitionFuncStr.length()-1);
    	transitionFuncStr.append("}\n");   
    	String result = statesStr.toString() + alphabetStr.toString() + initStateStr + acceptStatesStr.toString() + transitionFuncStr.toString();
    	return result;
    }
    
    /**
     * for test getPath method
     * @param args
     */
	public static void main(String[]args) 
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
	}
    
    /**
     * for test getComplementAutomata method
     * @param args
     */
 /*   public static void main(String[]args) 
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
		AtomicProp p= new AtomicProp("a");
		ArrayList<AtomicProp> alphabet = new ArrayList<AtomicProp>();
		alphabet.add(p);
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
		automat = automat.getComplementAutomata();
		for(State q: automat.acceptStates)
			System.out.println(q.getName());
	}*/
}
