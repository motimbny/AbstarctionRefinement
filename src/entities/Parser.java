package entities;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser 
{
	private String path;
	public Parser(String path)
	{
		this.path = path;
	}
	public void parseNFA(Automata automata)
	{
		StringBuilder str = new StringBuilder();
		str.append("Number of states: "+automata.getStates().size());
		ArrayList<AtomicProp> alphabet = automata.getAlphabet();
		ArrayList<State> states = automata.getStates();
		StringBuilder matrix =new StringBuilder("");
		for(State state: states)
		{
			matrix.append("{");
			for(AtomicProp input: alphabet)
			{
				Transition trans = automata.getTransition(automata.getTransitionFunction(), state, input);
				if(trans != null)
				{
					matrix.append("{");
					for(State dest: trans.getDestinationStates())
					{
						matrix.append(dest.getName() + ",");
					}
					matrix.deleteCharAt(matrix.length()-1);
					matrix.append("}");
				}
				else
					matrix.append("{-1}");
			}
			matrix.append("$},");
		}
		matrix.deleteCharAt(matrix.length()-1);
		str.append("\nThe movement matrix:" + matrix);
		str.append("\nThe alphabet:{");
		for(AtomicProp input: alphabet)
			str.append(input.getName() + ",");
		str.deleteCharAt(str.length()-1);
		str.append("}");
		str.append("\nNumber of final states: " + automata.getAcceptStates().size());
		str.append("\nThe final states:{");
		for(State state: automata.getAcceptStates())
			str.append(state.getName() + ",");
		str.deleteCharAt(str.length()-1);
		str.append("}");
		str.append("\nThe initail state: " + automata.getStartState().getName());
		try {
			FileWriter file = new FileWriter(path + "/nfa.txt");
			file.write(str.toString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
