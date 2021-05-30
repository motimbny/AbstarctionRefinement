package entities;

import java.io.File;


public class textFile
{
	public File myfile; 
	public String path;
    public textFile(String file)
    {
    	myfile=new File(file);
    	path = file;
    }
    /**
     * this function will return a string object
     * from the file provided to constructor.
     * @return String of total file content
     */
    public String fileToString()
    {
    	String data = "";
        try 
        {
            java.util.Scanner myReader = new java.util.Scanner(myfile);
            while (myReader.hasNextLine()) 
               data += myReader.nextLine()+"\n";
            myReader.close();
        } 
        catch (java.io.FileNotFoundException e) 
        {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
		return data;
      }
}
