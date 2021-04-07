package gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import entities.textFile;
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

    private Node thisNode;
    public static textFile MMfile;
    public static textFile MLfile;

    @FXML
    void continuePage(MouseEvent event) 
    {
    	  MMfile=new textFile(multiModelInput.getText());
    	  MLfile=new textFile(multiLTLInput.getText());
    	  thisNode = ((Node) event.getSource());
    	  GuiManager.SwitchScene(SCREENS.secondp,(Stage)thisNode.getScene().getWindow()); 
    }

    @FXML
    void openMultiModelChose(MouseEvent event) 
    {
    	String fileName = null;
    	JFileChooser chooser = new JFileChooser();
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files only","txt");
    	chooser.setFileFilter(filter);
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
    void openMultiltlChose(MouseEvent event) 
    {
    	String fileName = null;
    	JFileChooser chooser = new JFileChooser();
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files only","txt");
    	chooser.setFileFilter(filter);
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
        multiLTLInput.setText(fileName);
    }
    
}
