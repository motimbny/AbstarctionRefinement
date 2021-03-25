package entities;

import java.util.ArrayList;
import java.util.HashMap;

import enums.kripkeType;

public class KripkeSt 
{
	private ArrayList<ComplexState> states;
	private ArrayList<ComplexState> initialStates;
	private HashMap<ComplexState,ArrayList<ComplexState>> transitionRelation; 
	private HashMap<ComplexState, ArrayList<AtomicProp>> labelingFunction; 
    private kripkeType type;
    private int counter;
    public KripkeSt()
    {
    	states=new ArrayList<ComplexState>();
    	initialStates=new ArrayList<ComplexState>();
    	transitionRelation=new HashMap<ComplexState,ArrayList<ComplexState> >(); 
    	labelingFunction=new HashMap<ComplexState, ArrayList<AtomicProp>>();
    	type=kripkeType.Regular;
    	counter=0;
    }
    public KripkeSt(ArrayList<ComplexState> Sk,ArrayList<ComplexState> Ik,HashMap<ComplexState,ArrayList<ComplexState> > Rk,HashMap<ComplexState,ArrayList<AtomicProp>> Lk)
    {
    	this.states=new ArrayList<ComplexState>();
    	this.initialStates=new ArrayList<ComplexState>();
    	this.transitionRelation=new HashMap<ComplexState,ArrayList<ComplexState> >(); 
    	this.labelingFunction=new HashMap<ComplexState, ArrayList<AtomicProp>>();
    	this.type=kripkeType.Regular;
    	this.counter=0;
    }
	public ArrayList<ComplexState> getSk() {
		return states;
	}
	public void setStates(ArrayList<ComplexState> sk) {
		states = sk;
	}
	public ArrayList<ComplexState> getIk() {
		return initialStates;
	}
	public void setInitialStates(ArrayList<ComplexState> ik) {
		initialStates = ik;
	}
	public HashMap<ComplexState, ArrayList<ComplexState>> getRk() {
		return transitionRelation;
	}
	public void setRk(HashMap<ComplexState, ArrayList<ComplexState>> rk) {
		transitionRelation = rk;
	}
	public HashMap<ComplexState, ArrayList<AtomicProp>> getLk() {
		return labelingFunction;
	}
	public void setLk(HashMap<ComplexState, ArrayList<AtomicProp>> lk) {
		labelingFunction = lk;
	}
	public kripkeType getType() {
		return type;
	}
	public void setType(kripkeType type) {
		this.type = type;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public void addCount() {
		this.counter++;
	}
	public void removeCount() {
		this.counter--;
	}
	public Automata convertToAutomata()
	{
		return null;
	}

}
