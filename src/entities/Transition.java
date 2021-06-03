package entities;

import java.util.ArrayList;

public class Transition 
{
	private State currentState;
	private AtomicProp input;
	private ArrayList<State> destinationStates;
	
	public Transition(State currentState, AtomicProp input, ArrayList<State> destinationStates) {
		this.currentState = currentState;
		this.input = input;
		this.destinationStates = destinationStates;
	}
	public Transition(State currentState, AtomicProp input, State destinationState) {
		this.currentState = currentState;
		this.input = input;
		this.destinationStates = new  ArrayList<State>();
		this.destinationStates.add(destinationState);
	}

	public State getCurrentState() {
		return currentState;
	}

	public AtomicProp getInput() {
		return input;
	}

	public ArrayList<State> getDestinationStates() {
		return destinationStates;
	}
	
}
