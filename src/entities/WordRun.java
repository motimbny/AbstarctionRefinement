package entities;

import java.util.ArrayList;
import java.util.LinkedList;

public class WordRun 
{
	private ArrayList<ComplexState> run;
	public WordRun()
	{
		 run=new ArrayList<ComplexState>();
	}
    public WordRun(ArrayList<ComplexState> run)
    {
    	this.run=run;
    }
    
    public WordRun(LinkedList<State> run)
    {
    	this.run=new ArrayList<ComplexState>();
    	for(State state: run)
    	{
    		state.convertToComplexState();
    		run.add(state);
    	}
    }
    
    public ArrayList<ComplexState> getRun()
    {
    	return run;
    }
    public void updateRun(ArrayList<ComplexState> run)
    {
    	this.run=run;
    }
}
 