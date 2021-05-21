package entities;
import enums.Quantifier;
import enums.kripkeType;
import gui.secondPgCNT;
import gui.thirdPgCNT;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
public class Algo  implements Runnable
{
  private ArrayList<KripkeSt> m;
  private ArrayList<KripkeSt> a;


private ArrayList<KripkeSt> b;
  private ArrayList<Quantifier> p;
  
  public Algo(ArrayList<KripkeSt> m, ArrayList<Quantifier> p)
  {
	  this.m=m;
	  this.p = p;
  }
  
  
  public boolean runAbstraction()
  {	  
	  boolean resa,resb;
	  ArrayList<WordRun> counterExampleA;
	  ArrayList<WordRun> counterExampleB;
	  MMC mmc=new MMC();
	  initialize();
	  while(true)
	  {
		  resa=mmc.runMMCF(a, p);
		  if(resa || checkKripkeSize(a))  // we use checkKripkeSize() because we don't have a real MMC result  
			  return true;
		  else
		  {
			  counterExampleA=getCEX(a,m);
			  refine(counterExampleA,a, m);
		  }
		  resb=mmc.runMMCF(b, p);
		  if(resb|| checkKripkeSize(b)) //we use checkKripkeSize() because we don't have a real MMC result  
			  return false;
		  else
		  {
			  counterExampleB=getCEX(b,m);
			  refine(counterExampleB,b, m);
		  }
	  }
  }
  
  private Boolean checkKripkeSize(ArrayList<KripkeSt> abstractionList)
  {
	for(int i=0; i<m.size(); i++)
		if(m.get(i).getStates().size() != abstractionList.get(i).getStates().size())
			return false;
	return true;
  }
  
  public ArrayList<KripkeSt> getA() {
	return a;
}
  public boolean findResultMMC(String path)
  {
	boolean answer=false;
	try 
	{     
	    PdfReader reader = new PdfReader(path);
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
  
  public static ArrayList<WordRun> getCEX(ArrayList<KripkeSt> a, ArrayList<KripkeSt> m)
  {
	  ArrayList<LinkedList<State>> bFSpath;
	  WordRun wi = null;
	  Automata intersrctionAutomata;
	  ArrayList<WordRun> counterExamples = new ArrayList<WordRun>();
	  for(int i=0; i<m.size(); i++)
	  {
		  if(m.get(i).getStates().size() == a.get(i).getStates().size())
		  {
			counterExamples.add(null);
			continue;  
		  }
		  Automata ai = a.get(i).convertToAutomata(a.get(i).getInitialStates().get(0));
		  System.out.println("\nautomat ai:\n"+ai.toString());		   
		  Automata mi = m.get(i).convertToAutomata(m.get(i).getInitialStates().get(0));
		  System.out.println("\nautomat mi:\n"+mi.toString()); 
		  ai = ai.convertToDeterministic();
		  mi = mi.convertToDeterministic();
		  if(a.get(i).getType() == kripkeType.Over)
		  {
			  ai = ai.getComplementAutomata();
			  intersrctionAutomata = ai.getIntersection(mi);
			  bFSpath = intersrctionAutomata.getPath();
			  if(!bFSpath.isEmpty())
				  wi = new WordRun(findMaxList(bFSpath), kripkeType.Over);
		  }
		  else
		  {
			  mi = mi.getComplementAutomata();
			  intersrctionAutomata = mi.getIntersection(ai);
			  bFSpath = intersrctionAutomata.getPath();
			  if(!bFSpath.isEmpty())
				  wi = new WordRun(findMaxList(bFSpath), kripkeType.Under);	  
		  }
		  counterExamples.add(wi);
		  wi = null;
	  }
	return counterExamples;
  }
  
  private static LinkedList<State> findMaxList(ArrayList<LinkedList<State>> lists)
  {
	  LinkedList<State> max = null;
	  for(LinkedList<State> list: lists)
	  {
		  if( max == null || list.size()>max.size())
			  max = list;
	  }
	return max;
  }
  
  private static void refine(ArrayList<WordRun> counterExamples, ArrayList<KripkeSt> a,  ArrayList<KripkeSt> m)
  {
	  ArrayList<ComplexState> states;
	  ComplexState prevState, stateToSplit, followState;
	  for(int i=0; i<a.size(); i++)
	  {
		  if(counterExamples.get(i)==null)
              continue;
		  states = findStateToSplit(counterExamples.get(i), a.get(i));
		  prevState = states.get(0);
		  stateToSplit = states.get(1);
		  followState = states.get(2);
		  split(a.get(i), stateToSplit, followState, prevState, m.get(i));
	  }
  }
  
  private static KripkeSt split(KripkeSt a, ComplexState stateToSplit, ComplexState followState, ComplexState prevState, KripkeSt m)
  {
	  Boolean isConnect = false;
	  ArrayList<ComplexState> previousStates;
	  ComplexState newComplexState = new ComplexState("");
	  if(stateToSplit.equals(followState))
	  { //over

		  for(int i=0; i<stateToSplit.getContainingStates().size(); i++)
		  {
			  State state = stateToSplit.getContainingStates().get(i);
			  if(m.getRk().containsKey(state.convertToComplexState()))
			  {
				  for(State s: followState.getContainingStates())
				  {
					  if(m.getRk().get(state.convertToComplexState()).contains(s.convertToComplexState()))
					  {
						  isConnect = true;
						  break;
					  }
				  }
			  }
			  if(!isConnect)
			  {
				  newComplexState.addContainingStates(state);
				  newComplexState.setName(newComplexState.getName()+state.getName());
				  newComplexState.setLabels(stateToSplit.getLabels());
				  stateToSplit.getContainingStates().remove(state);
				  stateToSplit.setName(stateToSplit.getName().replace(state.getName(), ""));
			  }
		  }
		  a.getStates().add(newComplexState);
		  previousStates = a.getPreviousStates(stateToSplit);
		  for(ComplexState state: previousStates)
			  a.addTransitionRelation(state, newComplexState);
		  if(prevState != null)
			  a.removeTransitionRelation(prevState, stateToSplit);
	  }
	  else //under
	  {
		  for(int i=0; i<stateToSplit.getContainingStates().size(); i++)
		  {
			  State state = stateToSplit.getContainingStates().get(i);
			  if(m.getRk().containsKey(state.convertToComplexState()))
			  {
				  for(State s: followState.getContainingStates())
				  {
					  if(m.getRk().get(state.convertToComplexState()).contains(s.convertToComplexState()))
					  {
						  newComplexState.addContainingStates(state);
						  newComplexState.setName(newComplexState.getName()+state.getName());
						  newComplexState.setLabels(stateToSplit.getLabels());
						  stateToSplit.getContainingStates().remove(state);
						  stateToSplit.setName(stateToSplit.getName().replace(state.getName(), ""));
						  break;  
					  }  
				  }
			  }
		  }
		  a.getStates().add(newComplexState);
		  a.addTransitionRelation(newComplexState, followState);
		  previousStates = a.getPreviousStates(stateToSplit);
		  connectInboundEdgesUnder(previousStates, a, stateToSplit);
		  previousStates = a.getPreviousStates(newComplexState);
		  connectInboundEdgesUnder(previousStates, a, newComplexState);
	 }
	return a;
  }
  
  private static void connectInboundEdgesUnder(ArrayList<ComplexState> previousStates, KripkeSt a, ComplexState state)
  {
	  Boolean isConnect = false;
	  for(ComplexState prev: previousStates)  //connect back transition
	  {
		  for(State stateInPrevState: prev.getContainingStates())
		  {
			  isConnect = false;
			  ArrayList<ComplexState> destinationStates = a.getRk().get(stateInPrevState.convertToComplexState()); //list of all states that stateInSrc connect to
			  if(destinationStates != null)
			  {
				  for(ComplexState stateInDest: destinationStates)
				  {
					  if(state.getContainingStates().contains(stateInDest.convertToState()))
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
			  if(a.getRk().containsKey(prev))
			  {
				  a.getRk().get(prev).add(state);
			  }
			  else
			  {
				  ArrayList<ComplexState> destStates = new ArrayList<ComplexState>();
				  destStates.add(state);
				  a.getRk().put(state, destStates);
			  }
		  }
	  }		
	  
  }
  
  private static ArrayList<ComplexState> findStateToSplit(WordRun counterExample, KripkeSt a)
  {
	  ArrayList<ComplexState> states = new ArrayList<ComplexState>();
	  ComplexState statePrev = null, state1, state2 = null;
	  State toFind;
	  int j=0;
		  toFind = new State(counterExample.getRun().get(j));
		   state1 = findState(toFind, a.getStates());
		  while(j < counterExample.getRun().size()-1)
		  {
			  j++;
			  if(counterExample.getRun().get(j).equals("r"))
				  break;
			  toFind = new State(counterExample.getRun().get(j));
			   state2 = findState(toFind, a.getStates());
			   if(!a.getRk().containsKey(state1) || !a.getRk().get(state1).contains(state2))
				   break;
			   statePrev = state1;
			   state1 = state2;
			   
		  }
		  states.add(statePrev);
		  states.add(state1);
		  states.add(state2);
		  return states;
  }
  
  private static ComplexState findState(State toFind, ArrayList<ComplexState> states)
  {
	  for(ComplexState cmplxState: states)
		  if(cmplxState.getContainingStates().contains(toFind))
			  return cmplxState;
	  return null;
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
		  for(ComplexState initialState: copyOfkripke.getInitialStates())
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
		  for(ComplexState initialState: copyOfkripke.getInitialStates())
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
			return new KripkeSt(combinedStates, initialStates, transitionRelation, kripkeType.Over);

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
  
  public void initialize()
  {
	  a = new ArrayList<KripkeSt>();
	  b = new ArrayList<KripkeSt>();
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
		State q7s = new State("q7"); 
		State q8s = new State("q8"); 
		q0s.setAccept(true);
		q1s.setAccept(true);
		q2s.setAccept(true);
		q3s.setAccept(true);
		q4s.setAccept(true);
		q5s.setAccept(true);
		q6s.setAccept(true);
		q7s.setAccept(true);
		q8s.setAccept(true);
		ComplexState q0 = new ComplexState("q0");
		ComplexState q1 = new ComplexState("q1");
		ComplexState q2 = new ComplexState("q2");
		ComplexState q3 = new ComplexState("q3");
		ComplexState q4 = new ComplexState("q4");
		ComplexState q5 = new ComplexState("q5");
		ComplexState q6 = new ComplexState("q6");
		ComplexState q7 = new ComplexState("q7");
		ComplexState q8 = new ComplexState("q8");
		q0.addContainingStates(q0s);
		q1.addContainingStates(q1s);
		q2.addContainingStates(q2s);
		q3.addContainingStates(q3s);
		q4.addContainingStates(q4s);	
		q5.addContainingStates(q5s);
		q6.addContainingStates(q6s);
		q7.addContainingStates(q7s);
		q8.addContainingStates(q8s);
		ArrayList<ComplexState> states = new ArrayList<ComplexState> ();
		states.add(q0);
		states.add(q1);
		states.add(q2);
		states.add(q3);
		states.add(q4);
		states.add(q5);
		states.add(q6);
		states.add(q7);
		states.add(q8);
		ArrayList<ComplexState> initialStates = new ArrayList<ComplexState>();
		initialStates.add(q0);
		HashMap<ComplexState, ArrayList<ComplexState>> transitionRelation = new HashMap<ComplexState, ArrayList<ComplexState>>();
		AtomicProp a = new AtomicProp("a");
		AtomicProp b = new AtomicProp("b");
		AtomicProp c = new AtomicProp("c");
		AtomicProp d = new AtomicProp("d");
		q0.addLabel(a);
		q1.addLabel(a);
		q2.addLabel(b);
		q3.addLabel(b);
		q4.addLabel(b);
		q5.addLabel(c);
		q6.addLabel(c);
		q7.addLabel(d);
		q8.addLabel(d);		
		KripkeSt kripke = new KripkeSt(states, initialStates, transitionRelation, kripkeType.Regular);
		kripke.addTransitionRelation(q0, q3);
		kripke.addTransitionRelation(q1, q4);
		kripke.addTransitionRelation(q2, q5);
		kripke.addTransitionRelation(q3, q5);
		kripke.addTransitionRelation(q4, q6);
		kripke.addTransitionRelation(q5, q8);		
//		kripke.addTransitionRelation(q6, q8);
		System.out.println("\nregular kripke\n" + kripke.toString());
		
		ArrayList<KripkeSt> mList = new ArrayList<KripkeSt>();
		mList.add(kripke);		
		ArrayList<Quantifier> p = new ArrayList<Quantifier>();
		p.add(Quantifier.forEach);
		Algo alg = new Algo(mList, p);
		alg.runAbstraction();		
		
		//Parser par = new Parser("C:\\Users\\Adi Alaluf\\git\\AbstarctionRefinement");
	//	par.parseNFA(underApproximation(kripke).convertToAutomata(new ComplexState("q0q1")));
  }

	@Override
	public void run() 
	{
		secondPgCNT.result = runAbstraction();
		System.out.println("i finished and result "+secondPgCNT.result +"\nthread name algo: " + Thread.currentThread().getName());
		synchronized (thirdPgCNT.lock) {
			thirdPgCNT.lock.notifyAll();
    	    secondPgCNT.endTime = System.nanoTime(); 
		}
	}
}
