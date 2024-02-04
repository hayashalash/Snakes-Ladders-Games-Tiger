package Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class infoController implements Initializable {
	@FXML
    private Button exitButton;


    @FXML
    private Button homeButton;

    @FXML
    private void exit() {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
    

    @FXML
    private void goHome() {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
			Main.mainWindow.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}






