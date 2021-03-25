

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import enums.SCREENS;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GuiManager 
{
	@SuppressWarnings("serial")
	public static Map<SCREENS, String> availableFXML = new HashMap<SCREENS, String>()
	{
		{
			put(SCREENS.firstp, "/gui/FirstPage.fxml");
			put(SCREENS.secondp,"/gui/secondPage.fxml");
			put(SCREENS.thirdp,"/gui/thirdPage.fxml");
			put(SCREENS.thirdp,"/gui/fourthPage.fxml");
		}
	};
	
	public static void SwitchScene(SCREENS screenEnum, Stage SeondStage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(GuiManager.class.getResource(availableFXML.get(screenEnum)));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			SeondStage.setOnCloseRequest(evt -> Platform.exit());
			SeondStage.setScene(scene);
			SeondStage.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	public static void InitialPrimeryStage(SCREENS fxmlPath, Stage primaryStage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(GuiManager.class.getResource(availableFXML.get(fxmlPath)));
		Parent root = loader.load();
		Scene Scene = new Scene(root);
		primaryStage.setScene(Scene);
		primaryStage.setOnCloseRequest(evt -> Platform.exit());
		primaryStage.show();
	}
    
	public static void ShowMessagePopup(String msg)
	{
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Message");
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.showAndWait();
		});
	}
}
