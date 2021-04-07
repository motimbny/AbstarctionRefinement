package entities;

import java.util.ArrayList;

public class Transition 
{
	private State currentState;
	private ArrayList<AtomicProp> input;
	private State destinationState;
	
	public Transition(State currentState, ArrayList<AtomicProp> input, State destinationState)
	{
		this.currentState = currentState;
		this.input = input;
		this.destinationState = destinationState;
	}
	
	public State getDestinationState()
	{
		return this.destinationState;
	}
	
	public State getCurrentState() {
		return currentState;
	}

	public ArrayList<AtomicProp> getInput() {
		return input;
	}
}
