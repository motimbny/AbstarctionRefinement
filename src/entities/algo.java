package entities;
import entities.KripkeSt;
import gui.secondPgCNT;

import java.io.File;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
public class algo 
{
  private KripkeSt[] M;
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
}
