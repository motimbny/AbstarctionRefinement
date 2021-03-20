package entities;

import java.util.ArrayList;
import java.util.HashMap;

import enums.kripkeType;

public class KripkeSt 
{
	private ArrayList<ComplexState> Sk;
	private ArrayList<ComplexState> Ik;
	private HashMap<ComplexState,ArrayList<ComplexState>> Rk; 
	private HashMap<ComplexState, ArrayList<AtomicProp>> Lk; 
    private kripkeType type;
    private int counter;
    public KripkeSt()
    {
    	Sk=new ArrayList<ComplexState>();
    	Ik=new ArrayList<ComplexState>();
    	Rk=new HashMap<ComplexState,ArrayList<ComplexState> >(); 
    	Lk=new HashMap<ComplexState, ArrayList<AtomicProp>>();
    	type=kripkeType.Regular;
    	counter=0;
    }
    public KripkeSt(ArrayList<ComplexState> Sk,ArrayList<ComplexState> Ik,HashMap<ComplexState,ArrayList<ComplexState> > Rk,HashMap<ComplexState,ArrayList<AtomicProp>> Lk,kripkeType type, int counter)
    {
    	this.Sk=new ArrayList<ComplexState>();
    	this.Ik=new ArrayList<ComplexState>();
    	this.Rk=new HashMap<ComplexState,ArrayList<ComplexState> >(); 
    	this.Lk=new HashMap<ComplexState, ArrayList<AtomicProp>>();
    	this.type=kripkeType.Regular;
    	this.counter=0;
    }
	public ArrayList<ComplexState> getSk() {
		return Sk;
	}
	public void setSk(ArrayList<ComplexState> sk) {
		Sk = sk;
	}
	public ArrayList<ComplexState> getIk() {
		return Ik;
	}
	public void setIk(ArrayList<ComplexState> ik) {
		Ik = ik;
	}
	public HashMap<ComplexState, ArrayList<ComplexState>> getRk() {
		return Rk;
	}
	public void setRk(HashMap<ComplexState, ArrayList<ComplexState>> rk) {
		Rk = rk;
	}
	public HashMap<ComplexState, ArrayList<AtomicProp>> getLk() {
		return Lk;
	}
	public void setLk(HashMap<ComplexState, ArrayList<AtomicProp>> lk) {
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
	public Automata convertToAutomata()
	{
		return null;
	}

}
