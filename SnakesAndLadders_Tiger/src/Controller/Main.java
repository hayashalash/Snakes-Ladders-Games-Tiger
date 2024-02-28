package Controller;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Main extends Application {
    public static AudioClip note;
    public static Stage mainWindow = null;

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.initStyle(StageStyle.UNDECORATED);

            // Load the splash screen
            Parent splashRoot = FXMLLoader.load(getClass().getResource("/View/SplashScreen.fxml"));
            Scene splashScene = new Scene(splashRoot);
            primaryStage.setScene(splashScene);
            primaryStage.show();

            // Play background music
            playBackgroundMusic();

            // Wait for 3 seconds before loading the home FXML
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> {
                try {
                    // Load the home FXML
                    Parent homeRoot = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
                    Scene homeScene = new Scene(homeRoot, 852, 595);
                    primaryStage.setScene(homeScene);
                    primaryStage.setResizable(false);
                    mainWindow = primaryStage;
                    primaryStage.resizableProperty().setValue(false);
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        launch(args);

        try {
            Model.SysData.getInstance().readFromJson();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic() {
        if (note != null) {
            note.play();
            note.setVolume(0.1);
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
