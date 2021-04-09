package entities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class pdfMaker 
{
	private String path;
	public pdfMaker(String path)
	{
		this.path=path;
	}
	public void makePdf(String[] content)
	{
	    try 
	    {
		    Document document = new Document();
			OutputStream outputStream = new FileOutputStream(new File(path));
	 	    PdfWriter.getInstance(document, outputStream);
	 	    document.open();
	 	    for(String con: content)
	 	       document.add(new Paragraph(con));
	 	    document.close();
	        outputStream.close();
	    } 
	    catch (Exception e) 
	    {
		e.printStackTrace();
	    }
	  }
}
