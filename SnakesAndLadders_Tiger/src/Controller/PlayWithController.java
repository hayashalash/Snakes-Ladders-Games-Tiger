package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class PlayWithController implements Initializable{

	Methods methods = new Methods();
	
    @FXML
    private Button musicIcon;

    @FXML
    private Button exitBtn;

    @FXML
    private Button homeButton;

    @FXML
    private Button friends;

    @FXML
    private Button computer;

    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.clickSound();
    	methods.turnOffOn(event, musicIcon);
    }

    @FXML
    void goHome(ActionEvent event) {
    	methods.newScreen("Home");
    }

    @FXML
    void entered(MouseEvent event) {
    	methods.entered(event);
    }

    @FXML
    void exitGame(ActionEvent event) {
    	methods.clickSound();
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void exited(MouseEvent event) {
    	methods.exited(event);
    }

    @FXML
    void playWithComputer(ActionEvent event) {
    	methods.clickSound();
    	DifficultyController.playWithSystem = true;
    	methods.newScreen("ChooseDifficulty");
    }

    @FXML
    void playWithFriends(ActionEvent event) {
    	methods.clickSound();
    	DifficultyController.playWithSystem = false;
    	methods.newScreen("ChooseDifficulty");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	if (Main.note.isPlaying()) {
    		musicIcon.setOpacity(1.0);
    	}
    	else {
    		musicIcon.setOpacity(0.5);
    	}
	}

}
