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
	
	public void addTransitionRelation(State currentState, AtomicProp input, State destinationState)
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
	
	public KripkeSt convertToKripke()
	{
		ArrayList<ComplexState> kripkeStates = new ArrayList<ComplexState>();
		ArrayList<ComplexState> initialStates = new ArrayList<ComplexState>();
		HashMap<ComplexState,ArrayList<ComplexState>> transitionRelation = new HashMap<ComplexState,ArrayList<ComplexState>> (); 
		HashMap<ComplexState, ArrayList<AtomicProp>> labelingFunction = new HashMap<ComplexState, ArrayList<AtomicProp>>(); 
		for(State state: states)
		{
			ArrayList<State> containingStates = new ArrayList<State>();
			containingStates.add(state);
			ComplexState complexState = new ComplexState(state.getName(), containingStates, null);
			kripkeStates.add(complexState);
			if(state.equals(startState))  //???
				initialStates.add(complexState);
		}
		for(Transition transition: transitionFunction)
		{
			ArrayList<State> containingStatesSrc = new ArrayList<State>();
			containingStatesSrc.add(transition.getCurrentState());
			ComplexState srcState = new ComplexState(transition.getCurrentState().getName(), containingStatesSrc, null);
			ArrayList<State> containingStatesDest = new ArrayList<State>();
			containingStatesDest.add(transition.getDestinationState());
			ComplexState destState = new ComplexState(transition.getDestinationState().getName(), containingStatesDest, null);
			if(transitionRelation.containsKey(srcState))
			{
				ArrayList<ComplexState> destStateList = transitionRelation.get(srcState);
				if(!destStateList.contains(destState))
					destStateList.add(destState);
			}				
			else
			{
				ArrayList<ComplexState> destStateList = new ArrayList<ComplexState>();
				destStateList.add(destState);
				transitionRelation.put(srcState, destStateList);
			}
			if(!labelingFunction.containsKey(srcState))
			{
				ArrayList<AtomicProp> atomicPropList = new ArrayList<AtomicProp>();
				atomicPropList.add(transition.getInput());
				labelingFunction.put(srcState, atomicPropList);
			}
			else
				labelingFunction.get(srcState).add(transition.getInput());	
		}
		KripkeSt kripke = new KripkeSt(kripkeStates, initialStates, transitionRelation, labelingFunction);
		return kripke;
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
		ArrayList<AtomicProp> alphabet = null;
		ArrayList<State> acceptStates = null;
		AtomicProp p= new AtomicProp();
		Transition q0q1 = new Transition(q0, p, q1);
		Transition q0q2 = new Transition(q0, p, q2);
		Transition q1q3 = new Transition(q1, p, q3);
		Transition q2q0 = new Transition(q2, p, q0);
		Transition q2q5 = new Transition(q2, p, q5);
		Transition q2q6 = new Transition(q2, p, q6);
		Transition q3q1 = new Transition(q3, p, q1);
		Transition q3q4 = new Transition(q3, p, q4);
		Transition q4q2 = new Transition(q4, p, q2);
		Transition q4q3 = new Transition(q4, p, q3);
		Transition q5q4 = new Transition(q5, p, q4);
		Transition q5q6 = new Transition(q5, p, q6);
		Transition q6q5 = new Transition(q6, p, q5);
		ArrayList<Transition> transitionFunction = new ArrayList<Transition>();
		transitionFunction.add(q0q1);
		transitionFunction.add(q0q2);
		transitionFunction.add(q1q3);
		transitionFunction.add(q2q0);
		transitionFunction.add(q2q5);
		transitionFunction.add(q2q6);
		transitionFunction.add(q3q1);
		transitionFunction.add(q3q4);
		transitionFunction.add(q4q2);
		transitionFunction.add(q4q3);
		transitionFunction.add(q5q4);
		transitionFunction.add(q5q6);
		transitionFunction.add(q6q5);
		Automata automat = new Automata(q2, states, alphabet, acceptStates, transitionFunction);
		automat.getPath();
	}*/
    
    /**
     * for test getComplementAutomata method
     * @param args
     */
/*    public static void main(String[]args) 
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
		ArrayList<AtomicProp> alphabet = null;
		ArrayList<State> acceptStates = new ArrayList<State>();
		acceptStates.add(q0);		
		AtomicProp p= new AtomicProp();
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
