package gui;


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
import javafx.stage.Stage;

public class GuiManager 
{
	/**
	 * This section is to create a map and provide the path to 
	 * load fxml page from fxlm dir. if you want to add new page, you need to add
	 * the path here.
	 */
	@SuppressWarnings("serial")
	public static Map<SCREENS, String> availableFXML = new HashMap<SCREENS, String>()
	{
		{
			put(SCREENS.firstp, "/gui/FirstPage.fxml");
			put(SCREENS.secondp,"/gui/secondPage.fxml");
			put(SCREENS.thirdp,"/gui/thirdPage.fxml");
			put(SCREENS.fourthp,"/gui/fourthPage.fxml");
			put(SCREENS.help,"/gui/helpPage.fxml");
		}
	};
	/**
	 * this function is called in order to switch scene.
	 * @param screenEnum
	 * @param SeondStage
	 * result is that scene is switched and GUI shows the new page
	 */
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
    /**
     * this function create a pop up message
     * we use this function when error message is need to be shown.
     * @param msg
     */
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
