package Controller;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Color;
import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChoosePlayersController implements Initializable{

	@FXML
    private Button exitBtn;

    @FXML
    private TextField player1txt;

    @FXML
    private ChoiceBox<Color> player1clr;

    @FXML
    private TextField player2txt;

    @FXML
    private ChoiceBox<Color> player2clr;

    @FXML
    private VBox player3box;

    @FXML
    private TextField player3txt;

    @FXML
    private ChoiceBox<Color> player3clr;

    @FXML
    private VBox player4box;

    @FXML
    private TextField player4txt;

    @FXML
    private ChoiceBox<Color> player4clr;

    @FXML
    private Button backBtn;

    @FXML
    private Button startGameBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	intializeChoiceBox();
//    	Media mediaFile = new Media(this.getClass().getResource(MEDIA_URL).toExternalForm());
//    	player = new MediaPlayer(mediaFile);
//    	media.setMediaPlayer(player);
//    	player.setAutoPlay(true);
//    	media.setVisible(true);
//    	player.setVolume(0.1);
//    	player.play();
	}
    
    private void intializeChoiceBox() {
    	player1clr.getItems().addAll(Color.values());
    	player2clr.getItems().addAll(Color.values());
    	player3clr.getItems().addAll(Color.values());
    	player4clr.getItems().addAll(Color.values());
    	player1clr.getSelectionModel().select(0);
    	player2clr.getSelectionModel().select(1);
    	player3clr.getSelectionModel().select(2);
    	player4clr.getSelectionModel().select(3);
	}
    
    @FXML
    void backToDifficulty(ActionEvent event) {
    	newScreen("Difficulty");
    }

    @FXML
    void exitGame(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void startGame(ActionEvent event) {

    }
    
    void newScreen(String path) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/"+path+".fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    }
}
