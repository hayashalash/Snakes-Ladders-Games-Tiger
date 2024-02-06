package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Difficulty;
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
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void goHome(ActionEvent event) {
    	newScreen("Home");
    }

    @FXML
    void hard(ActionEvent event) {
    	ChoosePlayersController.diff = Difficulty.Hard;
    	// TODO pass difficulty to next screen
    	newScreen("ChoosePlayers");
    }

    @FXML
    void normal(ActionEvent event) {
    	ChoosePlayersController.diff = Difficulty.Medium;
    	// TODO pass difficulty to next screen
    	newScreen("ChoosePlayers");
    }

    @FXML
    void easy(ActionEvent event) {
    	ChoosePlayersController.diff = Difficulty.Easy;
    	// TODO pass difficulty to next screen
    	newScreen("ChoosePlayers");
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	void newScreen(String path) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/"+path+".fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
			Main.mainWindow.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    }

}
