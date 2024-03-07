package Controller;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Main extends Application {
    public static AudioClip note;
//	public static MediaPlayer note;
    public static Stage mainWindow = null;

    public static boolean playing = true;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.initStyle(StageStyle.UNDECORATED);

            // Load the icon
            Image tiger = new Image(getClass().getResourceAsStream("/img/icons/tiger.png"));
            // Set the icon for the primary stage
            primaryStage.getIcons().add(tiger);
            
            // Load the splash screen
            Parent splashScreenFXML = FXMLLoader.load(getClass().getResource("/View/SplashScreen.fxml"));
            Parent homeFXML = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));

            // StackPane to overlay the splash screen on top of the home screen
            StackPane root = new StackPane();
            root.getChildren().addAll(homeFXML, splashScreenFXML);
            
            Scene scene = new Scene(root, 852, 595);
            primaryStage.setScene(scene);
            primaryStage.show();
            mainWindow = primaryStage;

            // Play background music
            playBackgroundMusic();

            // Delay before starting the fade-out transition
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> {
            	// Fade out transition for the splash screen
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), splashScreenFXML);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(e -> {
                    // Remove the splash screen from the stack pane after fading out
                    root.getChildren().remove(splashScreenFXML);
                });
                fadeOut.play();
            });
            delay.play();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            note = new AudioClip(Main.class.getResource("/img/wavs/gameSound.mp3").toString());
        } catch (NullPointerException e) {
            System.err.println("Error: Audio file not found or path is incorrect.");
            e.printStackTrace();
        }

        // Start playing the background sound
        note.setCycleCount(AudioClip.INDEFINITE); // Set cycle count to indefinite for continuous playback
//        note.setVolume(0.1);
//        note.play();

        launch(args);

        try {
            Model.SysData.getInstance().readFromJson();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic() {
        if (note != null) {
        	note.setVolume(0.5);
        	note.play();
        }
    }

    public static void stopBackgroundMusic() {
        if (note != null && note.isPlaying()) {
            note.stop();
        }
    }

    public static void resumeBackgroundMusic() {
        if (note != null && !note.isPlaying()) {
            note.play();
        }
    }
	
}
