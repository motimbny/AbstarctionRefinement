package gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.pdfMaker;
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
    	String[] content=new String[] {""};
    	content[0]="moti";
    	pdmkr.makePdf(content);
    }

    @FXML
    void backToFirstPage(MouseEvent event) {
    	thisNode = ((Node) event.getSource());
    	GuiManager.SwitchScene(SCREENS.firstp,(Stage)thisNode.getScene().getWindow()); 
    }

}
