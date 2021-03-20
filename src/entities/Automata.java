package entities;

import java.util.ArrayList;

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
	
	public Automata getComplementAutomata()
	{
		ArrayList<State> acceptStates_temp = null;
		for(State state: states)
			if(!acceptStates.contains(state))
				acceptStates_temp.add(state);
		Automata complementAutomata = new Automata(startState, states, alphabet, acceptStates_temp, transitionFunction);
		return complementAutomata;
	}
	
	public KripkeSt convertToKripke()  //not finish!
	{
		ArrayList<ComplexState> kripkeStates = null;
		for(State state: states)
		{
			ArrayList<State> containingStates = new ArrayList<State>();
			containingStates.add(state);
			kripkeStates.add(new ComplexState(state.getName(), containingStates, null));
		}
		return null;
	}
}
