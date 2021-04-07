package gui;

import java.net.URL;
import java.util.ResourceBundle;

import enums.SCREENS;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class secondPgCNT implements Initializable
{

    @FXML
    private Button runBTN;

    @FXML
    private TextArea multiModelPlace;

    @FXML
    private TextArea multiLTLPlace;


    @FXML
    private ImageView backToFirstPageBTN;
    private Node thisNode;

    @FXML
    void backToFirstPage(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.firstp,(Stage)thisNode.getScene().getWindow()); 
    }

    @FXML
    void runTheAlgo(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.thirdp,(Stage)thisNode.getScene().getWindow()); 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		multiModelPlace.setText(firstPgCNT.MMfile.fileToString());
		multiLTLPlace.setText(firstPgCNT.MLfile.fileToString());
	}

}
