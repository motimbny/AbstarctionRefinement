package gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import enums.SCREENS;

public class firstPgCNT {

    @FXML
    private Button continureBTN;

    @FXML
    private TextField multiModelInput;

    @FXML
    private TextField multiLTLInput;

    @FXML
    private ImageView multiModelClip;

    @FXML
    private ImageView multiLTLClip;

    @FXML
    private Button multiModelClipB;

    @FXML
    private Button multiLTLClipB;
    private Node thisNode;

    @FXML
    void continuePage(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.secondp,(Stage)thisNode.getScene().getWindow());    	
    }

    @FXML
    void openMultiModelChose(MouseEvent event) {
    	String fileName = null;
    	System.out.println("i can add");
    	JFileChooser chooser = new JFileChooser();
        int status = chooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) 
        {
            File file = chooser.getSelectedFile();
            if (file == null)
            {
                return;
            }
            fileName = chooser.getSelectedFile().getAbsolutePath();
        }
        multiModelInput.setText(fileName);

    }

    @FXML
    void openMultiModelChoseB(MouseEvent event) {

    }

    @FXML
    void openMultiltlChose(MouseEvent event) {

    }

    @FXML
    void openMultiltlChoseB(MouseEvent event) {

    }

}
