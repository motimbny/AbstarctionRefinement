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
		Arrays.fill(que, -1);
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
    static LinkedList<State> insertPathToList(HashMap<State, State> parent, State source, State detenation)
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
            detenation = parent.get(detenation);
        }
        path.addFirst(detenation);
        return path;
    }
}
