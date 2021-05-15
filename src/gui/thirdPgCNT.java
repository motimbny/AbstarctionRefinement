package gui;
import enums.SCREENS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import entities.Algo;

public class thirdPgCNT implements Initializable, Runnable
{	
    @FXML
    private Button resBTN;

    @FXML
    private ImageView backToSecondPageBTN;

    @FXML
    public static Label resShow;
    private Node thisNode;

    @FXML
    public static TextField timeArea;

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

    public synchronized void update()
    {
    	System.out.println("hi");
    	try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(secondPgCNT.result)
			resShow.setText("M |= P");
		else
			resShow.setText("M |/= P");
		System.out.println("time");
		timeArea.setText("5:40");
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("thirdPage.fxml"));
			Label lblData = (Label) root.lookup("#resShow");
			lblData.setText("bye");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		 Thread t = new Thread(this);
	     t.start();
	}

	@Override
	public void run() {
		update();
		
	}

}
