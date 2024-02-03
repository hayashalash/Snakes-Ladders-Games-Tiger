package View;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class hardGameStage extends Application {

    private static final int S_WIDTH = 852; 
    private static final int S_HEIGHT = 595; 

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("hardGame");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Create a StackPane as the root node
        StackPane root = new StackPane();

        // Set the preferred size of the root node 
        root.setPrefSize(S_WIDTH, S_HEIGHT);

        // Load the image from the same package
        Image backgroundImage = new Image(getClass().getResource("/img/hardStageBack.jpg").toExternalForm());

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(backgroundImage);

        // Set the size of the image view to match the scene size
        imageView.setFitWidth(S_WIDTH);
        imageView.setFitHeight(S_HEIGHT);
        
        
        // Create a Pane for buttons
        Pane buttonsPane = new Pane();
        buttonsPane.setPrefSize(S_WIDTH, S_HEIGHT);
        
        // Load the icons from the same package
        Image closeIconImage = new Image(getClass().getResource("/img/X.png").toExternalForm());
        Image endGameIconImage = new Image(getClass().getResource("/img/exit.png").toExternalForm());


         // Create ImageViews to display the icons
        ImageView closeIconImageView = new ImageView(closeIconImage);
        ImageView endGameIconImageView = new ImageView(endGameIconImage);


        
        // Set the size of the icons
        closeIconImageView.setFitWidth(30);
        closeIconImageView.setFitHeight(30);
        endGameIconImageView.setFitWidth(40);
        endGameIconImageView.setFitHeight(40);

        
     // Create Close button with icon
        Button closeButton = new Button();
        closeButton.setGraphic(closeIconImageView);
        closeButton.setOnAction(e -> primaryStage.close());
        closeButton.setStyle("-fx-background-color: transparent;"); // Make the button transparent
        
        // Create buttons with icons

        Button endGameButton = createIconButton(endGameIconImageView);



        // Set the positions of buttons using coordinates within the Pane
        closeButton.setLayoutX(800);
        closeButton.setLayoutY(10);

        endGameButton.setLayoutX(700);
        endGameButton.setLayoutY(500);


        // Add buttons to the Pane
        buttonsPane.getChildren().addAll(closeButton, endGameButton);

        // Add the background image and buttonsPane to the StackPane
        root.getChildren().addAll(imageView, buttonsPane);

        endGameButton.setOnAction(e -> {
            // Close the current stage
            primaryStage.close();

            // Create an instance of the home class
            homeStage homeInstance = new homeStage();

            // Call the start method to initialize the new window
            try {
            	homeInstance.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace(); // Handle exceptions as needed
            }
        });

        // Create the Scene with the specified width and height
        Scene scene = new Scene(root, S_WIDTH, S_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to create icon button
    private Button createIconButton(ImageView iconImageView) {
        Button button = new Button();
        button.setGraphic(iconImageView);
        button.setStyle("-fx-background-color: transparent;");
        return button;
    }


    public static void main(String[] args) {
        launch(args);
    }
}