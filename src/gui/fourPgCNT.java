package gui;

import enums.SCREENS;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class fourPgCNT {

    @FXML
    private Button pdfBTN;

    @FXML
    private ImageView backToFirstPageBTN;

    @FXML
    private Label resTEXT;

    @FXML
    private Label timeTEXT;
    private Node thisNode;
    @FXML
    void ExportToPDF(MouseEvent event) {
    	
    }

    @FXML
    void backToFirstPage(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.firstp,(Stage)thisNode.getScene().getWindow()); 
    }

}
