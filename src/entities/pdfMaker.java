package entities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

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
	/**
	 * this function is used in order to create a new pdf file
	 * @param content 
	 */
	public void makePdf(ArrayList<String> content)
	{
	    try 
	    {
		    Document document = new Document();
		    File f=new File(path);
			OutputStream outputStream = new FileOutputStream(f);
	 	    PdfWriter.getInstance(document, outputStream);
	 	    document.open();
	 	    for(String con: content)
	 	       document.add(new Paragraph(con));
	 	    document.close();
	        outputStream.close();
		    openPDF(f);
	    } 
	    catch (Exception e) 
	    {
		e.printStackTrace();
	    }
	  }
	/**
	 * This function is used in order to open a pdf file 
	 * @param file
	 */
	public void openPDF(File file)
	{
		if(!Desktop.isDesktopSupported())
		{
            System.out.println("Desktop is not supported");
            return;
        } 
        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
			try 
            {
				desktop.open(file);
			} catch (IOException e) 
            {
				e.printStackTrace();
			}

    }
}
