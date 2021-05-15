package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import entities.AtomicProp;
import entities.ComplexState;
import entities.KripkeSt;
import entities.State;
import enums.Quantifier;
import enums.kripkeType;

public class TxtInput
{
	private File inputFile;
	
    public TxtInput(String path)
    {
    	this.inputFile = new File(path);
    }
    
    public ArrayList<KripkeSt> getMultiModel() throws FileNotFoundException
    {
    	Scanner read = new Scanner(inputFile);
    	ArrayList<KripkeSt> m = new ArrayList<KripkeSt>();
    	String line;
    	while(read.hasNextLine())
    	{
    		line = read.nextLine();
    		while(!line.equals("END"))
    		{
    			String[] states = line.replace("states:", "").split(",");
    			line = read.nextLine();
    			String[] initialStates = line.replace("initial_states:", "").split(",");
    			line = read.nextLine();
    			String[] lables = line.replace("lables:", "").split(",");
    			line = read.nextLine();
    			String[] lablesFunction = line.replace("lables_function:", "").split(",");
    			line = read.nextLine();
    			String[] transitionRelation = line.replace("transition_relation:", "").split(",");
    			m.add(createKripke(states, initialStates, lables, lablesFunction, transitionRelation));
    			line = read.nextLine();	
    		}
    	}
    	read.close();
		return m;    	
    }

	private KripkeSt createKripke(String[] states, String[] initialStates, String[] lables, String[] lablesFunction,
			String[] transitionRelation) 
	{
		HashMap<String, AtomicProp> lablesArray = new HashMap<String, AtomicProp>();
		HashMap<String, Integer> statesIndex = new HashMap<String, Integer>();
		ArrayList<State> statesArray = new ArrayList<State>();
		ArrayList<ComplexState> complexStates = new ArrayList<ComplexState> ();
		ArrayList<ComplexState> initialStatesK = new ArrayList<ComplexState>();
		HashMap<ComplexState, ArrayList<ComplexState>> transitionRelationK = new HashMap<ComplexState, ArrayList<ComplexState>>();
		int index = 0;
		for(int i=0; i<lables.length; i++)
			lablesArray.put(lables[i], new AtomicProp(lables[i]));
		for(int i=0; i<states.length; i++)
		{
			State q = new State(states[i]); 
			q.setAccept(true);
			statesArray.add(q);
			ComplexState complexState = new ComplexState(states[i]);
			complexState.addContainingStates(q);
			complexStates.add(index, complexState);
			statesIndex.put(complexState.getName(), index);
			index++;
			if(Arrays.asList(initialStates).contains(states[i]))
				initialStatesK.add(complexState);
			for(int j=0; j<lablesFunction.length; j++)
			{
				String[] temp = lablesFunction[j].split(":"); 
				if(temp[0].equals(states[i]))
					complexState.addLabel(lablesArray.get(temp[1]));			
			}
		}
		KripkeSt kripke = new KripkeSt(complexStates, initialStatesK, transitionRelationK, kripkeType.Regular);
		for(int j=0; j<transitionRelation.length; j++)
		{
			String[] temp = transitionRelation[j].split(":"); 
			kripke.addTransitionRelation(complexStates.get(statesIndex.get(temp[0])), complexStates.get(statesIndex.get(temp[1])));
		}
		return kripke;
	}
	
	public ArrayList<Quantifier> getMultiLTL() throws FileNotFoundException
	{
		Scanner read = new Scanner(inputFile);
		ArrayList<Quantifier> quantifiers = new ArrayList<Quantifier>();
		String line;
    	while(read.hasNextLine())
    	{
    		line = read.nextLine();
    		String[] temp = line.split(":");
    		if(temp[0].equals("EACH"))
    			quantifiers.add(Quantifier.forEach);
    		else
    			quantifiers.add(Quantifier.exist);
    	}
    	read.close();
		return quantifiers;		
	}
	
	public static void main(String[]args) 
	{
		TxtInput input = new TxtInput("multiModelInput.txt");
		ArrayList<KripkeSt> arr;
		try {
			arr = input.getMultiModel();
			System.out.println(arr.get(0).toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		TxtInput input2 = new TxtInput("multiLTLInput.txt");
		ArrayList<Quantifier> arr2;
		try {
			arr2 = input2.getMultiLTL();
			System.out.println(arr2.get(0).toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
}
