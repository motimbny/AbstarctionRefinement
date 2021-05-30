package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import entities.pdfMaker;
import enums.SCREENS;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class fourPgCNT  implements Initializable {

    @FXML
    private Button pdfBTN;

    @FXML
    private ImageView backToFirstPageBTN;

    @FXML
    private Label resTEXT;

    @FXML
    private Label timeTEXT;
    @FXML
    private TextArea aview;
    @FXML
    private TextArea mView;
    @FXML
    private TextArea pview;
    private Node thisNode;
    private pdfMaker pdmkr;
    @FXML
    private ImageView helpBTN;
    /**
     * This function is called when mouse clicks on help button.
     * it will switch the screen to help screen and save current calling screen.
     * @param event
     */
    @FXML
    void helpScrn(MouseEvent event) {
    	firstPgCNT.lastScreen=SCREENS.fourthp;
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.help,(Stage)thisNode.getScene().getWindow());
    }
    /**
     * This function is called when user clicks on Export to pdf button.
     * this function will create a new pdf file with all the data from the last run.
     * this function uses pdfMaker class
     * @param event
     */
    @FXML
    void ExportToPDF(MouseEvent event)
    {
    	String one="",two="",three="";
		for(int i=0;i<secondPgCNT.multiModel.size();i++)
		{
			one+=secondPgCNT.multiModel.get(i).toString();
			one+="\n";
			two+=secondPgCNT.multiLTL.get(i).toString();
			two+="\n";
			three+=secondPgCNT.algorithm.getA().get(i).toString();
			three+="\n";
		}
		String dateName;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		dateName=dateFormat.format(date).toString();
		dateName+="_Abstarction_results.pdf";
    	pdmkr=new pdfMaker(dateName);
    	ArrayList<String> content=new ArrayList<String>();
    	content.add("-------------Abstarction run results---------\n");
    	content.add("Time:"+thirdPgCNT.totalTime+" sec\n");
    	content.add("Result:"+thirdPgCNT.resultMP+"\n");
    	content.add("Date:"+dateFormat.format(date).toString()+"\n");
    	content.add("Input m:\n "+one+"\n");
    	content.add("Input p:\n "+two+"\n");
    	content.add("Abstarction of m:\n "+three+"\n");
    	pdmkr.makePdf(content);
    }
    /**
     * This function is called when user pressed on back button
     * this will bring the user back to first page.
     * @param event
     */
    @FXML
    void backToFirstPage(MouseEvent event) {
    	
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.firstp,(Stage)thisNode.getScene().getWindow()); 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String one="",two="",three="";
		for(int i=0;i<secondPgCNT.multiModel.size();i++)
		{
			one+=secondPgCNT.multiModel.get(i).toString();
			one+="\n";
			two+=secondPgCNT.multiLTL.get(i).toString();
			two+="\n";
			three+=secondPgCNT.algorithm.getA().get(i).toString();
			three+="\n";
		}
		mView.setText(one);
		pview.setText(two);
		aview.setText(three);
		timeTEXT.setText(""+thirdPgCNT.totalTime+" sec");
		resTEXT.setText(thirdPgCNT.resultMP);
	}

}
