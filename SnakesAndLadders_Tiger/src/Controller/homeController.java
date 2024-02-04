package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class homeController {

    @FXML
    private Button history;

    @FXML
    private Button question;

    @FXML
    private Button close;

    @FXML
    private Button info;

    @FXML
    private Button start;

    @FXML
    private void showHistory() {
        openNewScene("/View/GameHistory.fxml");
    }

    @FXML
    private void showQuestion() {
        openNewScene("/View/manageQuestions.fxml");
    }

    @FXML
    private void exit() {
        // Handle the event when exit button is clicked
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showInfo() {
        openNewScene("/View/Info.fxml");
    }

    @FXML
    private void start() {
    	openNewScene("/View/difficulty.fxml");
    }

    private void openNewScene(String fxmlPath) {
        Stage currentStage = (Stage) close.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));

            // Access the controller of the loaded FXML
            // If your controller has a constructor that accepts parameters, pass them here
            // ControllerClass controller = loader.getController();

            currentStage.close();
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions as needed
        }
    }
}
