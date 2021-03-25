package entities;

public class Transition 
{
	private State currentState;
	private AtomicProp input;
	private State destinationState;
	
	public Transition(State currentState, AtomicProp input, State destinationState)
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

	public AtomicProp getInput() {
		return input;
	}
}
