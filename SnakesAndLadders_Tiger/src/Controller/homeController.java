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
    private Button exit;

    @FXML
    private Button info;

    @FXML
    private Button editbutton;

    @FXML
    private void showHistory() {
        openNewScene("/View/history.fxml");
    }

    @FXML
    private void showQuestion() {
        openNewScene("/View/questions.fxml");
    }

    @FXML
    private void exit() {
        // Handle the event when exit button is clicked
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showInfo() {
        openNewScene("/View/info.fxml");
    }

    @FXML
    private void editt() {
        // Handle the event when edit button is clicked
        // Your edit button logic here
    }

    public void openNewScene(String fxmlPath) {
        Stage currentStage = (Stage) exit.getScene().getWindow();

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
