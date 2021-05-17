package gui;
import enums.SCREENS;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


public class thirdPgCNT implements Initializable, Runnable
{	
	public thirdPgCNT()
	{
		
	}
    @FXML
    private Button resBTN;

    @FXML
    private ImageView backToSecondPageBTN;

    @FXML
    public Label resShow;
    private Node thisNode;

    @FXML
    public TextField timeArea;
    public static  double totalTime;
    public static String resultMP;
    public static Object lock=new Object();
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

    public void update()
    {
    	System.out.println("thirdpage update wait\nthread name1: "+Thread.currentThread().getName());
    	if(secondPgCNT.endTime==-1)
    	{
	    	synchronized (lock)
	    	{
	    		try {
					lock.wait();
			    	System.out.println("thirdpage update after wait \nthread name2: "+Thread.currentThread().getName());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
    	}
			totalTime=(double)(secondPgCNT.endTime-secondPgCNT.startTime)/ 1_000_000_000;
			System.out.println("total time is : "+totalTime); 
    	    resultMP= (secondPgCNT.result) ? "M |= P": "M |/= P";
	    	System.out.println("resultmp : "+resultMP);
	    	System.out.println("im stuck here 1");
	    	System.out.println("im stuck here 2\nthread name3: "+Thread.currentThread().getName());
	    	Platform.runLater(new Runnable() {
	    	      @Override public void run() {
	    	    	  resShow.setText(resultMP);
	    	    	  System.out.println("total time is : "+totalTime); 
	    	    	  timeArea.setText(""+totalTime+" sec");	    	      }
	    	});
	    	Thread.currentThread().interrupt();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		 Thread t = new Thread(this);
	     t.start();
	}

	@Override
	public void run() 
	{
             update();	 
	}

}
