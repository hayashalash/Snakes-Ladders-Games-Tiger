package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class infoController implements Initializable {
	@FXML
    private Button home;

    @FXML
    private Button exit;
    
	void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
	void home(ActionEvent event) {
		homeController homeController = new homeController();
		homeController.newScreen("Home");
    }
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}