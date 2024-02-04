package Controller;

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

public class difficultyController implements Initializable {

    @FXML
    private Button easyButton;

    @FXML
    private Button normalButtom;

    @FXML
    private Button hardButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button homeButton;

    @FXML
    void easy(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void goHome(ActionEvent event) {
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

    @FXML
    void hard(ActionEvent event) {

    }

    @FXML
    void normal(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
