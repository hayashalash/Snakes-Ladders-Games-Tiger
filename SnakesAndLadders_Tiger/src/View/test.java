package View;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class test extends Application {

    private static final int S_WIDTH = 852;
    private static final int S_HEIGHT = 595;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("White Screen Test");

        // Create a StackPane with a white background
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: white;");
        root.setPrefSize(S_WIDTH, S_HEIGHT);

        // Create the Scene
        Scene scene = new Scene(root, S_WIDTH, S_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
