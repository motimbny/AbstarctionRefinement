package entities;


public class AtomicProp implements Cloneable
{
	private String name;

	public AtomicProp(String name) 
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public AtomicProp clone() throws CloneNotSupportedException
     {
    	 return (AtomicProp)super.clone();
     }
}
