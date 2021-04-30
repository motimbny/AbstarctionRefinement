package entities;

import java.util.LinkedList;

import enums.kripkeType;

public class WordRun 
{
	private LinkedList<String> run;
	public WordRun()
	{
		 run=new LinkedList<String>();
	}
    public WordRun(LinkedList<String> run)
    {
    	this.run=run;
    }
    
    public WordRun(LinkedList<State> run, kripkeType runType)
    {
    	this.run = new LinkedList<String>();
    	for(State state: run)
    		this.run.add(getLabelName(state.getName(),runType));
    }
    
    private String getLabelName(String str,kripkeType runType)
    {
    	StringBuilder temp = new StringBuilder();
    	int i;
    	if(runType== kripkeType.Over)
    	{
    		i=str.length()-1;
    		while(!Character.isLetter(str.charAt(i)))
    		{
    			temp=temp.insert(0, str.charAt(i));
    			i--;
    		}
    		temp.insert(0, str.charAt(i));
    	}
    	else
    	{
    		temp.append(str.charAt(0));
    		i=1;
    		while(Character.isDigit(str.charAt(i)))
    		{
    			temp=temp.append(str.charAt(i));
    			i++;
    		}
    	}
    	return temp.toString();
    }
    public LinkedList<String> getRun()
    {
    	return run;
    }
    public void updateRun(LinkedList<String> run)
    {
    	this.run=run;
    }
}
 