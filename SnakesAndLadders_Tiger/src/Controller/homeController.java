package Controller;

import View.difficultyStage;
import View.homeStage;
import View.infoStage;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class homeController {

    private homeStage view;
    private static Stage mainWindow;

    public homeController(homeStage view) {
        this.view = view;

        // Set up event handlers
        view.getCloseButton().setOnAction(this::handleCloseButton);
        view.getInfoButton().setOnAction(this::handleInfoButton);
        view.getHistoryButton().setOnAction(this::handleHistoryButton);
        view.getQaButton().setOnAction(this::handleQaButton);
        view.getStartButton().setOnAction(this::handleStartButton);
    }

    // Close the current stage when the close button is clicked
    private void handleCloseButton(ActionEvent event) {
        view.closeStage();
    }

    // Handle the Info button click
    private void handleInfoButton(ActionEvent event) {
        view.closeStage();
        // Create an instance of the difficulty stage
        infoStage infoInstance = new infoStage();

        // Set the main window reference
       // difficultyInstance.setMainWindow(getMainWindow());

        // Call the start method to initialize the new window
        try {
            infoInstance.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exceptions as needed
        }
    }

    // Handle the History button click
    private void handleHistoryButton(ActionEvent event) {
        view.closeStage();
        // TODO: Add logic for handling the History stage
    }

    // Handle the Q&A button click
    private void handleQaButton(ActionEvent event) {
        view.closeStage();
        // TODO: Add logic for handling the Questions stage
    }

    // Handle the Start button click
    private void handleStartButton(ActionEvent event) {
        view.closeStage();
        // TODO: Add logic for handling the Start button (possibly difficulty stage logic)
    }

    // Start the application by displaying the home stage
    public void startApplication() {
        view.start(new Stage());
    }

    // Setter method for the main window
    public static void setMainWindow(Stage mainWindow) {
        homeController.mainWindow = mainWindow;
    }

    // Getter method for the main window
    public static Stage getMainWindow() {
        return mainWindow;
    }
}
