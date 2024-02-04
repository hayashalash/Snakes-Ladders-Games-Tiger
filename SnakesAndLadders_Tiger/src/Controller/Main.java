package Controller;

import View.homeStage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    // Using a static variable for the main window - be cautious with its usage
    public static Stage mainWindow = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.initStyle(StageStyle.UNDECORATED);
            mainWindow = primaryStage;

            // Create an instance of the homeController
            homeController homeController = new homeController(new homeStage());
            
            // Set the main window reference
            Controller.homeController.setMainWindow(mainWindow);

            // Start the application by displaying the home stage
            homeController.startApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter method for the main window
    public static Stage getMainWindow() {
        return mainWindow;
    }
}
