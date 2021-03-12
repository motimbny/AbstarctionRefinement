package entities;

import java.util.ArrayList;

public class wordRun 
{
	ArrayList<complexState> run;
	public wordRun()
	{
		 run=new ArrayList<complexState>();
	}
    public wordRun(ArrayList<complexState> run)
    {
    	this.run=run;
    }
    public ArrayList<complexState> getRun()
    {
    	return run;
    }
    public void updateRun(ArrayList<complexState> run)
    {
    	this.run=run;
    }
}
 