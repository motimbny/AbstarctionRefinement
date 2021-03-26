package gui;
import enums.SCREENS;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class thirdPgCNT {

    @FXML
    private Button resBTN;

    @FXML
    private ImageView backToSecondPageBTN;

    @FXML
    private Label resShow;
    private Node thisNode;

    @FXML
    private TextField timeArea;

    @FXML
    void backToSecondPage(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.secondp,(Stage)thisNode.getScene().getWindow());    
    }

    @FXML
    void viewFinalRes(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.fourthp,(Stage)thisNode.getScene().getWindow());  

    }

}
