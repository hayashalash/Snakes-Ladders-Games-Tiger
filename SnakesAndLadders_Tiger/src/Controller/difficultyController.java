package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Difficulty;
import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class difficultyController implements Initializable {
	Methods methods = new Methods();
	
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
    private Button musicIcon;
    
    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.turnOffOn(event, musicIcon);
    }
    
    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void goHome(ActionEvent event) {
    	methods.newScreen("Home");
    }

    @FXML
    void hard(ActionEvent event) {
    	ChoosePlayersController.diff = Difficulty.Hard;
    	// TODO pass difficulty to next screen
    	methods.newScreen("ChoosePlayers");
    }

    @FXML
    void normal(ActionEvent event) {
    	ChoosePlayersController.diff = Difficulty.Medium;
    	// TODO pass difficulty to next screen
    	methods.newScreen("ChoosePlayers");
    }

    @FXML
    void easy(ActionEvent event) {
    	ChoosePlayersController.diff = Difficulty.Easy;
    	// TODO pass difficulty to next screen
    	methods.newScreen("ChoosePlayers");
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	if (Main.note.isPlaying()) {
    		musicIcon.setOpacity(1.0);
    	}
    	else {
    		musicIcon.setOpacity(0.5);
    	}
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
