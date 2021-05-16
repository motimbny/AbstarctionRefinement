package gui;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import enums.Quantifier;
import enums.SCREENS;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.TxtInput;

import java.util.concurrent.TimeUnit;
import entities.Algo;
import entities.KripkeSt;

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
    public static long startTime;
    private TxtInput parserMultiModel;
    private TxtInput parserMultiLTL;
    public static ArrayList<KripkeSt> multiModel;
    public static ArrayList<Quantifier> multiLTL;
    public static Algo algorithm;
    public static boolean result;
    public static boolean finish;

    @FXML
    void backToFirstPage(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.firstp,(Stage)thisNode.getScene().getWindow()); 
    }

    @FXML
    void runTheAlgo(MouseEvent event) 
    {
        startTime = System.nanoTime();
		 Thread t = new Thread(secondPgCNT.algorithm);
	     t.start();
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.thirdp,(Stage)thisNode.getScene().getWindow()); 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		parserMultiModel = new TxtInput(firstPgCNT.MMfile.path);
		try {
			multiModel = parserMultiModel.getMultiModel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		parserMultiLTL = new TxtInput(firstPgCNT.MLfile.path);
		try {
			multiLTL = parserMultiLTL.getMultiLTL();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		algorithm = new Algo(multiModel, multiLTL);
		System.out.println(multiModel.get(0).toString());
		System.out.println(multiLTL.get(0).toString());
		multiModelPlace.setText(multiModel.get(0).toString());
		multiLTLPlace.setText(multiLTL.get(0).toString());
	}
}
