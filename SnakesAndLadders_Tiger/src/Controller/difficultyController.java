package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class difficultyController implements Initializable{

    @FXML
    private StackPane rootPane;

    @FXML
    private Button closeButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button easyButton;

    @FXML
    private Button normalButton;

    @FXML
    private Button hardButton;

    private String selectedDifficulty;

    @FXML
    private void initialize() {
        // Set up the root pane
//        rootPane.setPrefSize(852, 595);
//        loadFXML("/View/difficulty.fxml");
    }

    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newRoot = loader.load();

            // Clear existing content and add new content
            rootPane.getChildren().clear();
            rootPane.getChildren().add(newRoot);

            // Pass the controller to the loaded FXML
            difficultyController controller = loader.getController();
            controller.setRootPane(rootPane);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions as needed
        }
    }

    public void setRootPane(StackPane rootPane) {
        this.rootPane = rootPane;
    }

    @FXML
    private void closeStage() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goHome() {
        loadFXML("/View/Home.fxml");
    }

    @FXML
    private void selectEasy() {
        handleDifficultySelection("easy");
    }

    @FXML
    private void selectNormal() {
        handleDifficultySelection("normal");
    }

    @FXML
    private void selectHard() {
        handleDifficultySelection("hard");
    }

    private void handleDifficultySelection(String difficulty) {
        this.selectedDifficulty = difficulty;
        System.out.println("Selected Difficulty: " + selectedDifficulty);
        // Open choosePlayers.fxml when a difficulty button is pressed
        loadFXML("/View/choosePlayers.fxml");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
