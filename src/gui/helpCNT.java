package gui;

import java.net.URL;
import java.util.ResourceBundle;

import enums.SCREENS;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class helpCNT  implements Initializable
{

    @FXML
    private Button continureBTN;

    @FXML
    private ImageView help1;

    @FXML
    private ImageView help2;

    @FXML
    private ImageView help3;

    @FXML
    private ImageView help4;
    private Node thisNode;
    /**
     * This function is used when user presses back button
     * it will switch back to previous scene
     * @param event
     */
    @FXML
    void continuePage(MouseEvent event) 
    {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(firstPgCNT.lastScreen,(Stage)thisNode.getScene().getWindow());
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		switch (firstPgCNT.lastScreen)
		{
			case firstp:
				help1.setVisible(true);
				break;
			case secondp:
				help2.setVisible(true);
				break;
			case thirdp:
				help3.setVisible(true);
				break;
			case fourthp:
				help4.setVisible(true);
				break;
				
		}
	}

}