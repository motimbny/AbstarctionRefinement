package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import enums.kripkeType;

public class KripkeSt 
{
	private ArrayList<ComplexState> states;
	private ArrayList<ComplexState> initialStates;
	private HashMap<ComplexState,ArrayList<ComplexState>> transitionRelation; 
    private kripkeType type;
    private int counter;
    
    public KripkeSt()
    {
    	states=new ArrayList<ComplexState>();
    	initialStates=new ArrayList<ComplexState>();
    	transitionRelation=new HashMap<ComplexState,ArrayList<ComplexState> >(); 
    	type=kripkeType.Regular;
    	counter=0;
    }
    
	public KripkeSt(ArrayList<ComplexState> states, ArrayList<ComplexState> initialStates,
			HashMap<ComplexState, ArrayList<ComplexState>> transitionRelation, kripkeType type) 
	{
		this.states = states;
		this.initialStates = initialStates;
		this.transitionRelation = transitionRelation;
		this.type = type;
		this.counter = states.size();
	}

	public ArrayList<ComplexState> getStates() {
		return states;
	}
	public void setStates(ArrayList<ComplexState> sk) {
		states = sk;
	}
	public ArrayList<ComplexState> getInitialStates() {
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
	
	public void addTransitionRelation(ComplexState src, ComplexState dest)
	{
		if(!transitionRelation.containsKey(src))
			transitionRelation.put(src, new ArrayList<ComplexState>());
		ArrayList<ComplexState> destList = transitionRelation.get(src);
		if(!destList.contains(dest))
		{
			destList.add(dest);
			transitionRelation.put(src, destList);
		}
	}
	
	/**
     * Returns a string that contains all the characteristics of the kripke structure.
     */
    public String toString()
    {
    	int tempIndex;
    	StringBuilder statesStr = new StringBuilder("States: { ");
    	for(ComplexState state: states)
    		statesStr.append(state.getName() + ", ");
		tempIndex = statesStr.lastIndexOf(",");
    	if(tempIndex != -1)
    		statesStr.deleteCharAt(statesStr.lastIndexOf(","));
    	statesStr.append("}\n");
    	StringBuilder initStatesStr = new StringBuilder("Initial states: { ");
    	for(ComplexState state: initialStates)
    		initStatesStr.append(state.getName() + ", ");
    	tempIndex = initStatesStr.lastIndexOf(",");
    	if(tempIndex != -1)
    		initStatesStr.deleteCharAt(initStatesStr.lastIndexOf(","));
    	initStatesStr.append("}\n");
    	StringBuilder transitionFuncStr = new StringBuilder("Transition relation: { ");
    	ArrayList<ComplexState> destStates = new ArrayList<ComplexState>();
    	for(ComplexState srcState: states) 
    	{
    		destStates = transitionRelation.get(srcState);
    		if(destStates != null)
	    		for(ComplexState destState: destStates)
	    			transitionFuncStr.append("( " + srcState.getName() + ", " + destState.getName() + " ), " );	
    	}
    	tempIndex = transitionFuncStr.lastIndexOf(",");
    	if(tempIndex != -1)
    		transitionFuncStr.deleteCharAt(transitionFuncStr.lastIndexOf(","));
    	transitionFuncStr.append("}\n");
    	StringBuilder labelingFuncStr = new StringBuilder("Labeling function: { ");
    	ArrayList<AtomicProp> atomicProps = new ArrayList<AtomicProp>();
    	for(ComplexState state: states) 
    	{
    		labelingFuncStr.append("( " + state.getName() + ", { ");
    		atomicProps = state.getLabels();
    		if(atomicProps != null)
    			for(AtomicProp ap: state.getLabels())
    				labelingFuncStr.append(ap.getName() + ", ");
    		tempIndex = labelingFuncStr.lastIndexOf(",");
        	if(tempIndex != -1)
        		labelingFuncStr.deleteCharAt(labelingFuncStr.lastIndexOf(","));
    		labelingFuncStr.append("} ), ");
    	}
    	tempIndex = labelingFuncStr.lastIndexOf(",");
    	if(tempIndex != -1)
    		labelingFuncStr.deleteCharAt(labelingFuncStr.lastIndexOf(","));
    	labelingFuncStr.append("}\n");    	
    	String result = statesStr.toString() + initStatesStr.toString() + transitionFuncStr.toString() + labelingFuncStr.toString() + "Type: " + type.toString() + "\nCount:" + counter;
    	return result;
    }
	
	public Automata convertToAutomata(ComplexState startState)
	{
		State automataStartState = null;
		ArrayList<State> automataStates = new ArrayList<State>();
		ArrayList<AtomicProp> alphabet = new ArrayList<AtomicProp>();
		ArrayList<State> automataAcceptStates = new ArrayList<State>();
		ArrayList<Transition> automataTransitionFunction = new ArrayList<Transition>(); 
		for(ComplexState cs: states) //creates state for each complex state in the kripke
		{
			State state = new State(cs.getName());
			automataStates.add(state);
			if(cs.equals(startState))
				automataStartState = state;
			if(cs.isAccept()) 
			{
				automataAcceptStates.add(state);
				state.setAccept(true);
			}
			for(AtomicProp lbl: cs.getLabels())
            	if(!alphabet.contains(lbl))  //maybe we will need to change hashcode of AP
            		alphabet.add(lbl);
		}
		ComplexState src = null, dest;
		ArrayList<AtomicProp> inputs = null;
		Iterator<Map.Entry<ComplexState,ArrayList<ComplexState>>> transitionIterator = transitionRelation.entrySet().iterator();
		while (transitionIterator.hasNext()) 
		{
            Map.Entry<ComplexState,ArrayList<ComplexState>> transition = (Map.Entry<ComplexState,ArrayList<ComplexState>>)transitionIterator.next();
            if(transition.getKey() instanceof ComplexState)
            {
            	src = (ComplexState)transition.getKey();
            	inputs = src.getLabels(); 
            	ArrayList<ComplexState> destinationStates = null;
	            if(transition.getValue() instanceof ArrayList)
	            {
	            	destinationStates = (ArrayList<ComplexState>)transition.getValue();
	        		ArrayList<State> destStates = new ArrayList<State>();
	            	for(ComplexState state: destinationStates)
		            {
		            	dest = state;
		            	destStates.add(dest.convertToState());
		            }
		            for(AtomicProp input: inputs)
		            {
		            	Transition trans = new Transition(src.convertToState(), input, destStates);
			            automataTransitionFunction.add(trans);
		            }
	            }
	        }
        }
		Automata automata = new Automata(automataStartState, automataStates, alphabet, automataAcceptStates, automataTransitionFunction);
		return automata;
	}
	
	public KripkeSt getClone() throws CloneNotSupportedException
	{
		ArrayList<ComplexState> states=new ArrayList<ComplexState>();
		ArrayList<ComplexState> initialStates = new ArrayList<ComplexState>();
		HashMap<ComplexState,ArrayList<ComplexState>> transitionRelation; 
		for (ComplexState initState : this.initialStates)
			initialStates.add(initState.clone());
		for (ComplexState state : this.states)
			states.add(state.clone());
		
		return new KripkeSt(states, initialStates, (HashMap<ComplexState,ArrayList<ComplexState>>)this.transitionRelation.clone(), this.type);
	}

	public static void main(String[]args)
	{
		State q1s = new State("q1"); 
		State q2s = new State("q2"); 
		State q3s = new State("q3"); 
		q3s.setAccept(true);
		ComplexState q1 = new ComplexState("q1");
		ComplexState q2 = new ComplexState("q2");
		ComplexState q3 = new ComplexState("q3");
		q1.addContainingStates(q1s);
		q2.addContainingStates(q2s);
		q3.addContainingStates(q3s);
		ArrayList<ComplexState> states = new ArrayList<ComplexState> ();
		states.add(q1);
		states.add(q2);
		states.add(q3);
		ArrayList<ComplexState> initialStates = new ArrayList<ComplexState>();
		initialStates.add(q1);
		HashMap<ComplexState, ArrayList<ComplexState>> transitionRelation = new HashMap<ComplexState, ArrayList<ComplexState>>();
		AtomicProp a = new AtomicProp("a");
		AtomicProp b = new AtomicProp("b");
		q1.addLabel(a);
		q1.addLabel(b);
		q2.addLabel(a);
		q3.addLabel(b);
		KripkeSt kripke = new KripkeSt(states, initialStates, transitionRelation, kripkeType.Regular);
		kripke.addTransitionRelation(q1, q2);
		kripke.addTransitionRelation(q1, q3);	
		System.out.println(kripke.toString());
		Automata automata = kripke.convertToAutomata(q1);
		System.out.println("\n" + automata.toString());
	}
}
