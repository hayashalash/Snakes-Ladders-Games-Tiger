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

public class DifficultyController implements Initializable {
	Methods methods = new Methods();
	
	public static boolean playWithSystem;
	
    @FXML
    private Button easyButton;

    @FXML
    private Button normalButtom;

    @FXML
    private Button hardButton;

    @FXML
    private Button backBtn;
    
    @FXML
    private Button exitButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button musicIcon;
    
    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.clickSound();
    	methods.turnOffOn(event, musicIcon);
    }
    
    @FXML
    void exit(ActionEvent event) {
    	methods.clickSound();
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void goHome(ActionEvent event) {
    	methods.clickSound();
    	methods.newScreen("Home");
    }
    
    @FXML
    void backToFriendsOrSystem(ActionEvent event) {
    	methods.clickSound();
    	methods.newScreen("FriendsOrSystem");
    }

    @FXML
    void hard(ActionEvent event) {
    	methods.clickSound();
    	if (playWithSystem) {
    		PlayerDetailsController.diff = Difficulty.Hard;
    		methods.newScreen("PlayerDetails");
    	}
    	else {
        	ChoosePlayersController.diff = Difficulty.Hard;
        	methods.newScreen("ChoosePlayers");
    	}
    }

    @FXML
    void normal(ActionEvent event) {
    	methods.clickSound();
    	if (playWithSystem) {
    		PlayerDetailsController.diff = Difficulty.Medium;
    		methods.newScreen("PlayerDetails");    		
    	}
    	else {
    		ChoosePlayersController.diff = Difficulty.Medium;
        	methods.newScreen("ChoosePlayers");
    	}
    }

    @FXML
    void easy(ActionEvent event) {
    	methods.clickSound();
    	if (playWithSystem) {
    		PlayerDetailsController.diff = Difficulty.Easy;
    		methods.newScreen("PlayerDetails");    		
    	}
    	else {
        	ChoosePlayersController.diff = Difficulty.Easy;
        	methods.newScreen("ChoosePlayers");
    	}
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
