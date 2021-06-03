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
		  Automata mi = m.get(i).convertToAutomata(m.get(i).getInitialStates().get(0));
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

	@Override
	public void run() 
	{
		secondPgCNT.result = runAbstraction();
		synchronized (thirdPgCNT.lock) {
			thirdPgCNT.lock.notifyAll();
    	    secondPgCNT.endTime = System.nanoTime(); 
		}
	}
}
