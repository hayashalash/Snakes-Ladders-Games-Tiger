package Controller;
import java.io.IOException;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class infoController {

    private Button closeButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button homeButton;

    @FXML
    private void closeStage() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
	void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    private void openInfo() {
    }

    @FXML
    private void goHome() {
        // Close the current stage
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.close();

        // Load and display the home.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Home.fxml"));
        try {
            Pane root = loader.load();
            Stage homeStage = new Stage();
            homeStage.setTitle("Home");
            homeStage.initStyle(StageStyle.UNDECORATED);
            homeStage.setScene(new Scene(root));
            homeStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions as needed
        }
    }
}






