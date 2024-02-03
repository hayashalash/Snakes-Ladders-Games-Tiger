import Controller.InfoController;
import Controller.startController;
import View.homeStage;
import Model.SysData;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize instances of your Model, View, and Controller
        homeStage view = new homeStage();
         startControl controller = new startController(view, model);

        // Show the primary stage
        view.start(primaryStage);
    }
}