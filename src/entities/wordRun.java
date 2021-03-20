package entities;

import java.util.ArrayList;

public class wordRun 
{
	private ArrayList<ComplexState> run;
	public wordRun()
	{
		 run=new ArrayList<ComplexState>();
	}
    public wordRun(ArrayList<ComplexState> run)
    {
    	this.run=run;
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
 