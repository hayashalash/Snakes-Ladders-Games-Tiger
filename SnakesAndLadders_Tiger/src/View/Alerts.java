package View;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Alerts {
	
    private static final String ALERT_SOUND_FILE = "/img/wavs/AlertSound.wav";
    private static MediaPlayer alertSoundPlayer;

    static {
        // Load the alert sound
        Media sound = new Media(Alerts.class.getResource(ALERT_SOUND_FILE).toString());
        alertSoundPlayer = new MediaPlayer(sound);
    }
    
    // Method to play the alert sound
    private static void playAlertSound() {
        alertSoundPlayer.stop(); // Stop the sound in case it's already playing
        alertSoundPlayer.play();
    }
    
	/** delete alert
	 * after pressing on "delete", this alert will pop up. 
	 * @param content
	 * @return
	 */
	public static int delete(String content){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("You're about to delete the question: "+ "\"" + content +"\"");
		alert.setContentText("Are you sure you want to delete the selected question?");
		playAlertSound();
		if (alert.showAndWait().get() == ButtonType.OK)
			return 1;
		return -1;
	}
	
	public static int permanentDelete(String content){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("You're about to permanently delete the question: "+ "\"" + content +"\"");
		alert.setContentText("Are you sure you want to permanently delete the selected question?");
		playAlertSound();
		if (alert.showAndWait().get() == ButtonType.OK)
			return 1;
		return -1;
	}
	
	public static int restore(String content){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Restore");
		alert.setHeaderText("You're about to restore the question: "+ "\"" + content +"\"");
		alert.setContentText("Are you sure you want to restore the selected question?");
		playAlertSound();
		if (alert.showAndWait().get() == ButtonType.OK)
			return 1;
		return -1;
	}


	/** Update alert
	 * @return
	 */
	public static int edit() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Edit");
		alert.setHeaderText("Are you sure you want to edit the question?");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//		stage.getIcons().add(new Image("img/logo.png"));
		playAlertSound();
		stage.setAlwaysOnTop(true);
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == ButtonType.OK)
			return 1;
		return -1;

	}
	// Alert to make sure the user wants to completely exit the game
	public static int exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("You're about to exit the game!");
		alert.setContentText("Are you sure you want to exit?");
		playAlertSound();
		// Set a custom graphic (icon)
		Image customImage = new Image("/img/icons/logout.png");
	    ImageView exitAlert = new ImageView(customImage);
	    exitAlert.setFitHeight(50);
	    exitAlert.setFitWidth(50);
        alert.setGraphic(exitAlert);
        
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/img/icons/logout.png"));
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == ButtonType.OK)
			return 1;
		return -1;
    }
	/** Confirmation alert tells us that the item or question was added or edited successfully
	 * @param operation
	 */
	public static void confirmation(String message) {
		playAlertSound();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(message);

		// Set a custom graphic (icon)
		Image customImage = new Image("/img/icons/check.png");
	    ImageView warningAlert = new ImageView(customImage);
	    warningAlert.setFitHeight(50);
	    warningAlert.setFitWidth(50);
        alert.setGraphic(warningAlert);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/img/icons/check-mark.png"));
		stage.setAlwaysOnTop(true);
		alert.show();
	}

	/** Warning alert
	 * @param content
	 */
	public static void warning(String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText(content);
		playAlertSound();
		// Set a custom graphic (icon)
		Image customImage = new Image("/img/icons/warning.png");
	    ImageView warningAlert = new ImageView(customImage);
	    warningAlert.setFitHeight(50);
	    warningAlert.setFitWidth(50);
        alert.setGraphic(warningAlert);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/img/icons/alert.png"));
		stage.setAlwaysOnTop(true);
		alert.show();
	}

	/** message
	 * @param title
	 * @param content
	 */
	public static void message(String title,String content) {
		playAlertSound();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(content);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//		stage.getIcons().add(new Image("images/Success.png"));
		stage.setAlwaysOnTop(true);
		alert.show();

	}
	
	public static int retunHome() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("End Game");
		alert.setHeaderText("Go to homepage?");
		alert.setContentText("Are you sure you want to go to the homepage?\nPlease note this will result in ending the game!");
		playAlertSound();
		// Set a custom graphic (icon) 
		Image customImage = new Image("/img/icons/home.png");
	    ImageView home = new ImageView(customImage);
	    home.setFitHeight(50);
	    home.setFitWidth(50);
        alert.setGraphic(home);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/img/icons/alert.png"));
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == ButtonType.OK)
			return 1;
		return -1;
    }
	
	public static int restartGame() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Restart Game");
		alert.setHeaderText("Restart Game?");
		alert.setContentText("Are you sure you want to restart the game?");
		playAlertSound();
		// Set a custom graphic (icon) 
		Image customImage = new Image("/img/icons/PlayAgain.jpeg");
	    ImageView resart = new ImageView(customImage);
	    resart.setFitHeight(50);
	    resart.setFitWidth(50);
        alert.setGraphic(resart);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/img/icons/alert.png"));
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == ButtonType.OK)
			return 1;
		return -1;
    }
	
	}
