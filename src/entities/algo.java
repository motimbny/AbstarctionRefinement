package entities;
import entities.KripkeSt;
import enums.Quantifier;
import enums.kripkeType;
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
  
  private static KripkeSt underApproximation(KripkeSt m) throws CloneNotSupportedException
  {
	  ArrayList<ComplexState> combinedStates;
	  ArrayList<ComplexState> initialStates = new ArrayList<ComplexState>();
	  ArrayList<ComplexState> destinationStates;
	  KripkeSt copyOfkripke = m.getClone();
	  HashMap<ComplexState,ArrayList<ComplexState>> transitionRelation = new HashMap<ComplexState,ArrayList<ComplexState>>(); 
	  boolean isConnect = false;
	  try {
		  combinedStates = combineStates(copyOfkripke.getStates());
		  for(ComplexState initialState: copyOfkripke.getIk())
			  for(ComplexState complexState: combinedStates)
				  if(complexState.getContainingStates().contains(initialState.convertToState()))
					  initialStates.add(complexState);
		  for(ComplexState complexStateSrc: combinedStates)
		  {
			  for(ComplexState complexStateDest: combinedStates)
			  {
				  for(State stateInSrc: complexStateSrc.getContainingStates())
				  {
					  isConnect = false;
					  ComplexState temp = new ComplexState(stateInSrc.getName());
					  destinationStates = copyOfkripke.getRk().get(temp); //list of all states that stateInSrc connect to
					  if(destinationStates != null)
					  {
						  for(ComplexState stateInDest: destinationStates)
						  {
							  if(complexStateDest.getContainingStates().contains(stateInDest.convertToState()))
							  {
								  isConnect = true;
								  break;
							  }
						  }
					  }
					  if(!isConnect)
						  break;
				  }
				  if(isConnect)
				  {
					  if(transitionRelation.containsKey(complexStateSrc))
					  {
						  transitionRelation.get(complexStateSrc).add(complexStateDest);
					  }
					  else
					  {
						  ArrayList<ComplexState> destStates = new ArrayList<ComplexState>();
						  destStates.add(complexStateDest);
						  transitionRelation.put(complexStateSrc, destStates);
					  }
				  }
			  }
		  }
			return new KripkeSt(combinedStates, initialStates, transitionRelation, kripkeType.Under);

	} catch (CloneNotSupportedException e) 
	  {
		e.printStackTrace();
		
	}

	  return null;  
  }
  
  private static KripkeSt overApproximation(KripkeSt m) throws CloneNotSupportedException
  {
	  ArrayList<ComplexState> combinedStates;
	  ArrayList<ComplexState> initialStates = new ArrayList<ComplexState>();
	  ArrayList<ComplexState> destinationStates;
	  KripkeSt copyOfkripke = m.getClone();
	  HashMap<ComplexState,ArrayList<ComplexState>> transitionRelation = new HashMap<ComplexState,ArrayList<ComplexState>>(); 
	  boolean isConnect = false;
	  try {
		  combinedStates = combineStates(copyOfkripke.getStates());
		  for(ComplexState initialState: copyOfkripke.getIk())
			  for(ComplexState complexState: combinedStates)
				  if(complexState.getContainingStates().contains(initialState.convertToState()))
					  initialStates.add(complexState);
		  for(ComplexState complexStateSrc: combinedStates)
		  {
			  for(ComplexState complexStateDest: combinedStates)
			  {
				  for(State stateInSrc: complexStateSrc.getContainingStates())
				  {
					  isConnect = false;
					  ComplexState temp = new ComplexState(stateInSrc.getName());
					  destinationStates = copyOfkripke.getRk().get(temp); //list of all states that stateInSrc connect to
					  if(destinationStates != null)
					  {
						  for(ComplexState stateInDest: destinationStates)
						  {
							  if(complexStateDest.getContainingStates().contains(stateInDest.convertToState()))
							  {
								  isConnect = true;
								  break;
							  }
						  }
					  }
					  if(isConnect)
						  break;
				  }
				  if(isConnect)
				  {
					  if(transitionRelation.containsKey(complexStateSrc))
					  {
						  transitionRelation.get(complexStateSrc).add(complexStateDest);
					  }
					  else
					  {
						  ArrayList<ComplexState> destStates = new ArrayList<ComplexState>();
						  destStates.add(complexStateDest);
						  transitionRelation.put(complexStateSrc, destStates);
					  }
				  }
			  }
		  }
			return new KripkeSt(combinedStates, initialStates, transitionRelation, kripkeType.Under);

	} catch (CloneNotSupportedException e) 
	  {
		e.printStackTrace();
		
	}
	  return null;  
  }

  
  private static ArrayList<ComplexState> combineStates(ArrayList<ComplexState> states) throws CloneNotSupportedException
  {
	  ArrayList<ComplexState> resultStates = states;// (ArrayList<ComplexState>) states.clone();
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
				  j--;
			  }  
		  }
	  }
	  return resultStates;
  }
  
  public ArrayList<ArrayList<KripkeSt>> initialize(ArrayList<KripkeSt> m, ArrayList<Quantifier> p)
  {
	  ArrayList<ArrayList<KripkeSt>> output = new ArrayList<ArrayList<KripkeSt>>();
	  ArrayList<KripkeSt> a = new ArrayList<KripkeSt>();
	  ArrayList<KripkeSt> b = new ArrayList<KripkeSt>();
	  for(int i=0; i<m.size(); i++)
	  {
		  if(p.get(i) == Quantifier.exist)
		  {
			  try {
				a.add(underApproximation(m.get(i)));
				b.add(overApproximation(m.get(i)));
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		  }
		  else
		  {
			  try {
				b.add(underApproximation(m.get(i)));
				a.add(overApproximation(m.get(i)));
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		  }
	  }
	  output.add(a);
	  output.add(b);
	  return output;
  }
  
  public static void main(String[]args) throws CloneNotSupportedException 
  {
/*		State q1s = new State("q1"); 
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
		}*/
	    State q0s = new State("q0"); 
		State q1s = new State("q1"); 
		State q2s = new State("q2"); 
		State q3s = new State("q3"); 
		State q4s = new State("q4");
		State q5s = new State("q5"); 
		State q6s = new State("q6"); 		
		q3s.setAccept(true);
		ComplexState q0 = new ComplexState("q0");
		ComplexState q1 = new ComplexState("q1");
		ComplexState q2 = new ComplexState("q2");
		ComplexState q3 = new ComplexState("q3");
		ComplexState q4 = new ComplexState("q4");
		ComplexState q5 = new ComplexState("q5");
		ComplexState q6 = new ComplexState("q6");
		q0.addContainingStates(q0s);
		q1.addContainingStates(q1s);
		q2.addContainingStates(q2s);
		q3.addContainingStates(q3s);
		q4.addContainingStates(q4s);	
		q5.addContainingStates(q5s);
		q6.addContainingStates(q6s);
		ArrayList<ComplexState> states = new ArrayList<ComplexState> ();
		states.add(q0);
		states.add(q1);
		states.add(q2);
		states.add(q3);
		states.add(q4);
		states.add(q5);
		states.add(q6);
		ArrayList<ComplexState> initialStates = new ArrayList<ComplexState>();
		initialStates.add(q0);
		HashMap<ComplexState, ArrayList<ComplexState>> transitionRelation = new HashMap<ComplexState, ArrayList<ComplexState>>();
		AtomicProp a = new AtomicProp("a");
		AtomicProp b = new AtomicProp("b");
		AtomicProp c = new AtomicProp("c");
		q0.addLabel(a);
		q1.addLabel(a);
		q2.addLabel(b);
		q3.addLabel(b);
		q4.addLabel(b);
		q5.addLabel(c);
		q6.addLabel(c);
		KripkeSt kripke = new KripkeSt(states, initialStates, transitionRelation, kripkeType.Regular);
		kripke.addTransitionRelation(q0, q3);
		kripke.addTransitionRelation(q1, q4);
		kripke.addTransitionRelation(q2, q6);
		System.out.println(kripke.toString());
		KripkeSt under = overApproximation(kripke);
		System.out.println(under.toString());
  }
}
