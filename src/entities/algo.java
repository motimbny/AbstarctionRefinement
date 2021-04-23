package entities;
import entities.KripkeSt;
import gui.secondPgCNT;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
public class algo 
{
  private ArrayList<KripkeSt> M;
  private KripkeSt[] A;
  private KripkeSt[] B;
  static long totalTime;
  public algo(KripkeSt[] M)
  {
	  boolean res;
	  this.M=M;
  }
  private KripkeSt[] createAbstraction(KripkeSt[] m) 
  {
	return null;
  }
  public boolean findResultMMC(String path)
  {
	boolean answer=false;
	try 
	{     
	    PdfReader reader = new PdfReader(path);
	    int n = reader.getNumberOfPages(); 
	    String str=PdfTextExtractor.getTextFromPage(reader, 1); //Extracting the content from a particular page.
        reader.close();
        String ans;
        ans=str.substring(str.lastIndexOf("\n")).replace("Is the NFA language belongs to the NFH language: ","");
        answer= (ans=="True") ? true : false;
	} 
	catch (Exception e) 
	{
	    System.out.println(e);
	}
	return answer;
  }
  public wordRun getCEX(KripkeSt[] a, KripkeSt[] b)
  {
	return null;
  }
  private KripkeSt[] refine(wordRun counterExampleA, KripkeSt[] a2)
  {
	return null;
  }
  public boolean runAbstraction()
  {
	  //long endTime = System .nanoTime(); 
	  //totalTime=endTime-secondPgCNT.startTime;
	  //System.out.println("total time is : "+totalTime); 
	  boolean resa,resb;
	  wordRun counterExampleA;
	  wordRun counterExampleB;
	  MMC mmc=new MMC();
	  this.A=createAbstraction(M);
	  this.B=createAbstraction(M);
	  while(true)
	  {
		  resa=mmc.runMMCF(A);
		  if(resa)
			  return true;
		  else
		  {
			  counterExampleA=getCEX(A,M);
			  A=refine(counterExampleA,A);
		  }
		  resb=mmc.runMMCF(B);
		  if(resb)
			  return true;
		  else
		  {
			  counterExampleB=getCEX(B,M);
			  B=refine(counterExampleB,B);
		  }
	  }
  }
  
  private KripkeSt underApproximation(KripkeSt m)
  {
	  ArrayList<ComplexState> combinedStates;
	  ArrayList<ComplexState> destinationStates;
	  boolean isConnect = false;
	  try {
		  combinedStates = combineStates(m.getStates());
		  for(ComplexState complexStateSrc: combinedStates)
		  {
			  for(ComplexState complexStateDest: combinedStates)
			  {
				  for(State stateInSrc: complexStateSrc.getContainingStates())
				  {
					  ComplexState temp = new ComplexState(stateInSrc.getName());
					  destinationStates = m.getRk().get(temp); //list of all states that stateInSrc connect to
					  for(ComplexState stateInDest: destinationStates)
					  {
						  if(complexStateDest.getContainingStates().contains(stateInDest))
						  {
							  isConnect = true;
							  break;
						  }
					  }
					  if(!isConnect)
						  break;
				  }
			  }
		  }
	} catch (CloneNotSupportedException e) 
	  {
		e.printStackTrace();
		
	}

	  return null;  
  }
  
  private ArrayList<ComplexState> combineStates(ArrayList<ComplexState> states) throws CloneNotSupportedException
  {
	  ArrayList<ComplexState> resultStates = (ArrayList<ComplexState>) states.clone();
	  ComplexState state1;
	  for(int i=0; i<resultStates.size()-1; i++)
	  {
		  state1 = resultStates.get(i);
		  for(int j=i+1; j<resultStates.size(); j++)
		  {
			  if(state1.compareLabels(resultStates.get(j).getLabels()))
			  {
				  state1.addContainingStates(resultStates.get(j).getContainingStates());
				  state1.setName(state1.getName() + resultStates.get(j).getName());
				  resultStates.remove(resultStates.get(j));
			  }  
		  }
	  }
	  return resultStates;
  }
  
  public static void main(String[]args) 
  {
		State q1s = new State("q1"); 
		State q2s = new State("q2"); 
		State q3s = new State("q3"); 
		State q4s = new State("q4"); 
		ComplexState q1 = new ComplexState("q1");
		ComplexState q2 = new ComplexState("q2");
		ComplexState q3 = new ComplexState("q3");
		ComplexState q4 = new ComplexState("q4");
		q1.addContainingStates(q1s);
		q2.addContainingStates(q2s);
		q3.addContainingStates(q3s);
		q4.addContainingStates(q4s);
		ArrayList<ComplexState> states = new ArrayList<ComplexState> ();
		states.add(q1);
		states.add(q2);
		states.add(q3);
		states.add(q4);
		AtomicProp a = new AtomicProp("a");
		AtomicProp b = new AtomicProp("b");
		q1.addLabel(a);
		q1.addLabel(b);
		q2.addLabel(a);
		q3.addLabel(a);
		q3.addLabel(b);
		q4.addLabel(b);
		ArrayList<ComplexState> result = null;
		try {
			result = combineStates(states);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
  }
}
