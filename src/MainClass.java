import enums.SCREENS;
import gui.GuiManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application  
{
	
	public static void main(String[]args) 
	{
		launch(args);
	}

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