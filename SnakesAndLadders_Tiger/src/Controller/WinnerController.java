package Controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Difficulty;
import Model.Game;
import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class WinnerController implements Initializable{

    @FXML
    private AnchorPane screen;

    @FXML
    private ImageView celebrate;

    @FXML
    private Label winnerLabel;

    @FXML
    private Button exitButton;

//    private Media media;
// 	private MediaPlayer backgroundvideo=null;
// 
 	 @FXML
     private Button play;

     @FXML
     void playAgain(ActionEvent event) {
    	 Difficulty diff = HardController.game.getType();
    	 if (diff == Difficulty.Easy) {
     		EasyController.game = new Game(diff, EasyController.game.getPlayers());
     		newScreen("easyBoard");
     	}
     	else if (diff == Difficulty.Medium) {
     		NormalController.game = new Game(diff, NormalController.game.getPlayers());
     		newScreen("normalBoard");
     	}
     	else if (diff == Difficulty.Hard) {
     		HardController.game = new Game(diff, HardController.game.getPlayers());
     		newScreen("hardBoard");
     	}
     }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// Get the screen dimensions
//		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

//		if (backgroundvideo == null) {
//		    media = new Media(getClass().getResource("/img/wavs/winner.mp4").toExternalForm());
//
//		    backgroundvideo = new MediaPlayer(media);
//		    mediaView.setMediaPlayer(backgroundvideo);
//
//		    backgroundvideo.setOnEndOfMedia(new Runnable() {
//		        @Override
//		        public void run() {
//		            backgroundvideo.seek(Duration.ZERO);
//		            backgroundvideo.play();
//		        }
//		    });
//		    backgroundvideo.play();
//		}

		// Set the mediaView size to fill the screen
//		mediaView.setFitWidth(primaryScreenBounds.getWidth());
//		mediaView.setFitHeight(primaryScreenBounds.getHeight());
//
//		// Move the mediaView to the back
//		mediaView.toBack();
		
//		Image i = new Image(getClass().getResource("/img/icons/celebrating.gif").toExternalForm());
//		celebrate.setImage(i);
		try {
			String resource = "/img/icons/celebrating.gif";
			InputStream stream = getClass().getResourceAsStream(resource);
	        if (stream == null) {
	            System.out.println("Unable to find resource: "+resource);
	            // Handle resource not found error
	            return;
	        }
	        Image i = new Image(stream);
	        if (i.isError()) {
	            System.out.println("Error loading image: " + i.getException().getMessage());
	            // Handle error condition, such as displaying an alternative image
	        } else {
	            celebrate.setImage(i);
	        }
	    } catch (Exception e) {
	        System.out.println("Exception loading image: " + e.getMessage());
	        // Handle exception condition, such as displaying a placeholder image or logging the error
	    }
//		String congrats = "Congrats" + HardController.game.getWinner().getPlayerName() + "you won!";
		String congrats = "Congrats " + "haya" + " you won!";

		winnerLabel.setText(congrats);
		// Center the text in the screen
		winnerLabel.setMaxWidth(Double.MAX_VALUE);
		AnchorPane.setLeftAnchor(winnerLabel, 0.0);
		AnchorPane.setRightAnchor(winnerLabel, 0.0);
		winnerLabel.setAlignment(Pos.CENTER);
		/// Design the text
		winnerLabel.setStyle( "-fx-text-fill: #cc8624; " +
							  "-fx-font-size: 36px; " +
				              "-fx-font-family: Broadway; ");
	}
	@FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
	@FXML
    void entered(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1.1);
    	((Node)event.getSource()).setScaleY(1.1);
    	((Node) event.getSource()).setCursor(Cursor.HAND);
    }
	
    @FXML
    void exited(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1);
    	((Node)event.getSource()).setScaleY(1);
    	((Node) event.getSource()).setCursor(Cursor.DEFAULT);
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
