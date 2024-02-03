package Controller;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChoosePlayersController implements Initializable{

    @FXML
    private Button exitBtn;

    @FXML
    private TextField player1txt;

    @FXML
    private ComboBox<?> player1clr;

    @FXML
    private TextField player2txt;

    @FXML
    private ComboBox<?> player2clr;

    @FXML
    private VBox player3box;

    @FXML
    private TextField player3txt;

    @FXML
    private ComboBox<?> player3clr;

    @FXML
    private VBox player4box;

    @FXML
    private TextField player4txt;

    @FXML
    private ComboBox<?> player4clr;

    @FXML
    private Button backBtn;

    @FXML
    private Button startGameBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//    	Media mediaFile = new Media(this.getClass().getResource(MEDIA_URL).toExternalForm());
//    	player = new MediaPlayer(mediaFile);
//    	media.setMediaPlayer(player);
//    	player.setAutoPlay(true);
//    	media.setVisible(true);
//    	player.setVolume(0.1);
//    	player.play();
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
