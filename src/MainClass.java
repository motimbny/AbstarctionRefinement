import enums.SCREENS;
import gui.GuiManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application  
{
	/**
	 * this is the main where you need to start the program
	 * run this main in order to use the gui
	 * @param args
	 */
	public static void main(String[]args) 
	{
		launch(args);
	}
    /**
     * This function is used when using the main above.
     */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		try 
		{
			GuiManager.InitialPrimeryStage(SCREENS.firstp,primaryStage);			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
}