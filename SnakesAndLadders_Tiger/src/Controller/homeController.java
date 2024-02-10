package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;


import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class homeController implements Initializable{

    @FXML
    private Button history;

    @FXML
    private Button question;

    @FXML
    private Button exit;

    @FXML
    private Button info;
    
    @FXML
    private Button start;

    @FXML
    private ImageView turnOffIcon = new ImageView();

    @FXML
    private Button musicOff;
    
    public AudioClip note = new AudioClip(this.getClass().getResource("/img/wavs/game.wav").toString());

    @FXML
    void TurnOffOn(ActionEvent event) {
    	if (turnOffIcon.getOpacity() == 0.0) { // if music is on
			note.stop();
			turnOffIcon.setOpacity(1.0);
		}
    	else { // is music is off
    		note.play();
			note.setVolume(0.5);
    		turnOffIcon.setOpacity(0.0);
    	}
    }

    public void initialize(URL location, ResourceBundle resources) {
    	turnOffIcon.setMouseTransparent(true);
        Tooltip h = new Tooltip("History");
        Tooltip.install(history, h);
        Tooltip q = new Tooltip("Questions");
        Tooltip.install(question, q);
        Tooltip r = new Tooltip("Game Rules");
        Tooltip.install(info, r);

    	if (note.isPlaying()) {
    		turnOffIcon.setOpacity(0.0);
    		note.play();
    	}
    	else {
    		turnOffIcon.setOpacity(1.0);
        	note.stop();
    	}
    }
    
    @FXML
    void start(ActionEvent event) {
    	newScreen("ChooseDifficulty");
    }

    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
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

    @FXML
    void showHistory(ActionEvent event) throws IOException{
    	newScreen("GameHistory");

    }

    @FXML
    void showInfo(ActionEvent event) throws IOException{
    	newScreen("Info");

    }

    @FXML
    void showQuestion(ActionEvent event) throws IOException{
    	newScreen("manageQuestion");
    }

  
}
