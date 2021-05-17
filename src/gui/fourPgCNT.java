package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    void ExportToPDF(MouseEvent event)
    {
		String dateName;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		dateName=dateFormat.format(date).toString();
		dateName+="_Abstarction_results.pdf";
    	pdmkr=new pdfMaker(dateName);
    	String[] content=new String[] {"","","","","","",""};
    	content[0]="-------------Abstarction run results---------\n";
    	content[1]="Time:"+thirdPgCNT.totalTime+" sec\n";
    	content[2]="Result:"+thirdPgCNT.resultMP+"\n";
    	content[3]="Date:"+dateFormat.format(date).toString()+"\n";
    	content[4]="Input m:\n "+secondPgCNT.multiModel.get(0).toString()+"\n";
    	content[5]="Input p:\n "+secondPgCNT.multiLTL.get(0).toString()+"\n";
    	content[6]="Abstarction of m:\n "+secondPgCNT.algorithm.getA().get(0).toString()+"\n";
    	pdmkr.makePdf(content);
    }

    @FXML
    void backToFirstPage(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.firstp,(Stage)thisNode.getScene().getWindow()); 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mView.setText(secondPgCNT.multiModel.get(0).toString());
		pview.setText(secondPgCNT.multiLTL.get(0).toString());
		aview.setText(secondPgCNT.algorithm.getA().get(0).toString());
		timeTEXT.setText(""+thirdPgCNT.totalTime+" sec");
		resTEXT.setText(thirdPgCNT.resultMP);
	}

}
