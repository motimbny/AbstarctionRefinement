package entities;

import java.util.ArrayList;

import enums.Quantifier;

public class MMC 
{
	private int i=2;
   public boolean runMMC(ArrayList<KripkeSt> m, ArrayList<Quantifier> p)
   {
	   return true;
   }
   public boolean runMMCF(ArrayList<KripkeSt> m, ArrayList<Quantifier> p)
   {
	   if(i==0)
		   return true;
	   i--;
	   return false;
   }
   
}
