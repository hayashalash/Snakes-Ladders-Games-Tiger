package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class WinnerController implements Initializable{

    @FXML
    private AnchorPane screen;

    @FXML
    private MediaView mediaView;

    @FXML
    private ImageView celebrate;

    @FXML
    private Label winnerLabel;

    private Media media;
 	private MediaPlayer backgroundvideo=null;
 
 	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if (backgroundvideo == null) {//starts the background video
			media = new Media(getClass().getResource("/img/wavs/winner.mp4").toExternalForm());

			backgroundvideo = new MediaPlayer(media);
			mediaView.setMediaPlayer(backgroundvideo);

			backgroundvideo.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					backgroundvideo.seek(Duration.ZERO);
					backgroundvideo.play();
				}
			});
			backgroundvideo.play();
		}
		mediaView.toBack();
		Image i = new Image(getClass().getResource("/img/icons/celebrating.gif").toExternalForm());
        celebrate.setImage(i);
		String congrats = "Congrats" + HardController.game.getWinner().getPlayerName() + "you won!";
		
		winnerLabel.setText(congrats);
		winnerLabel.setStyle( "-fx-text-fill: #cc8624; " +
							  "-fx-font-size: 36px; " +
				              "-fx-font-family: Broadway; ");
	}

}
