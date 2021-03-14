package entities;

import java.util.ArrayList;
import java.util.HashMap;

import enums.kripkeType;

public class kripkeSt 
{
	private ArrayList<complexState> Sk;
	private ArrayList<complexState> Ik;
	private HashMap<complexState,ArrayList<complexState>> Rk; 
	private HashMap<complexState, ArrayList<atomicProp>> Lk; 
    private kripkeType type;
    private int counter;
    public kripkeSt()
    {
    	Sk=new ArrayList<complexState>();
    	Ik=new ArrayList<complexState>();
    	Rk=new HashMap<complexState,ArrayList<complexState> >(); 
    	Lk=new HashMap<complexState, ArrayList<atomicProp>>();
    	type=kripkeType.Regular;
    	counter=0;
    }
    public kripkeSt(ArrayList<complexState> Sk,ArrayList<complexState> Ik,HashMap<complexState,ArrayList<complexState> > Rk,HashMap<complexState,ArrayList<atomicProp>> Lk,kripkeType type, int counter)
    {
    	Sk=new ArrayList<complexState>();
    	Ik=new ArrayList<complexState>();
    	Rk=new HashMap<complexState,ArrayList<complexState> >(); 
    	Lk=new HashMap<complexState, ArrayList<atomicProp>>();
    	type=kripkeType.Regular;
    	counter=0;
    }
	public ArrayList<complexState> getSk() {
		return Sk;
	}
	public void setSk(ArrayList<complexState> sk) {
		Sk = sk;
	}
	public ArrayList<complexState> getIk() {
		return Ik;
	}
	public void setIk(ArrayList<complexState> ik) {
		Ik = ik;
	}
	public HashMap<complexState, ArrayList<complexState>> getRk() {
		return Rk;
	}
	public void setRk(HashMap<complexState, ArrayList<complexState>> rk) {
		Rk = rk;
	}
	public HashMap<complexState, ArrayList<atomicProp>> getLk() {
		return Lk;
	}
	public void setLk(HashMap<complexState, ArrayList<atomicProp>> lk) {
		Lk = lk;
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
	public automata convertToAutomata()
	{
		return null;
	}

}
