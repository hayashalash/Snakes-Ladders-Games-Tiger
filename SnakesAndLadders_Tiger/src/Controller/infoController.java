package Controller;
import java.net.URL;
import java.util.ResourceBundle;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class infoController implements Initializable {
	
	Methods methods = new Methods();
	
	@FXML
	private AnchorPane anchor;

	@FXML
	private Button closeButton;

    @FXML
    private Button homeButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Auto-generated method stub
		
	}
	
    @FXML
    void closeStage(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    public Button getHomeButton() {
		return homeButton;
	}

	public void setHomeButton(Button homeButton) {
		this.homeButton = homeButton;
	}

	@FXML
    private void goHome() {
		methods.newScreen("Home");
	}
	
	 @FXML
	 void entered(MouseEvent event){
		 methods.entered(event);
	 }
	 
	@FXML
	 void exited(MouseEvent event){
		methods.exited(event);
	 }	
	
}
