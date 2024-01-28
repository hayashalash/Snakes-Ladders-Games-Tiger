package View;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class difficultyStage extends Application {

    private static final int S_WIDTH = 852; 
    private static final int S_HEIGHT = 595; 

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Home");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Create a StackPane as the root node
        StackPane root = new StackPane();

        // Set the preferred size of the root node (optional)
        root.setPrefSize(S_WIDTH, S_HEIGHT);

        // Load the image from the same package
        Image backgroundImage = new Image(getClass().getResource("/View/difficultyBack.jpg").toExternalForm());

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(backgroundImage);

        // Set the size of the image view to match the scene size
        imageView.setFitWidth(S_WIDTH);
        imageView.setFitHeight(S_HEIGHT);
        
        
        // Create a Pane for buttons
        Pane buttonsPane = new Pane();
        buttonsPane.setPrefSize(S_WIDTH, S_HEIGHT);
        
        // Load the icons from the same package
        Image closeIconImage = new Image(getClass().getResource("/View/X.png").toExternalForm());
        Image homeIconImage = new Image(getClass().getResource("/View/home.png").toExternalForm());
        Image easyIconImage = new Image(getClass().getResource("/View/easy.png").toExternalForm());
        Image normalIconImage = new Image(getClass().getResource("/View/normal.png").toExternalForm());
        Image hardIconImage = new Image(getClass().getResource("/View/hard.png").toExternalForm());


         // Create ImageViews to display the icons
        ImageView closeIconImageView = new ImageView(closeIconImage);
        ImageView homeIconImageView = new ImageView(homeIconImage);
        ImageView easyIconImageView = new ImageView(easyIconImage);
        ImageView normalIconImageView = new ImageView(normalIconImage);
        ImageView hardIconImageView = new ImageView(hardIconImage);

        
        // Set the size of the icons
        closeIconImageView.setFitWidth(30);
        closeIconImageView.setFitHeight(30);
        homeIconImageView.setFitWidth(40);
        homeIconImageView.setFitHeight(40);
        easyIconImageView.setFitWidth(210);
        easyIconImageView.setFitHeight(140);
        normalIconImageView.setFitWidth(210);
        normalIconImageView.setFitHeight(140);
        hardIconImageView.setFitWidth(210);
        hardIconImageView.setFitHeight(140);
        
     // Create Close button with icon
        Button closeButton = new Button();
        closeButton.setGraphic(closeIconImageView);
        closeButton.setOnAction(e -> primaryStage.close());
        closeButton.setStyle("-fx-background-color: transparent;"); // Make the button transparent
        
        // Create buttons with icons
        Button homeButton = createIconButton(homeIconImageView);
        Button easyButton = createIconButton(easyIconImageView);
        Button normalButton = createIconButton(normalIconImageView);
        Button hardButton = createIconButton(hardIconImageView);


        // Set the positions of buttons using coordinates within the Pane
        closeButton.setLayoutX(800);
        closeButton.setLayoutY(10);

        homeButton.setLayoutX(700);
        homeButton.setLayoutY(100);

        easyButton.setLayoutX(100);
        easyButton.setLayoutY(230);

        normalButton.setLayoutX(320);
        normalButton.setLayoutY(230);

        hardButton.setLayoutX(540);
        hardButton.setLayoutY(230);

        // Add buttons to the Pane
        buttonsPane.getChildren().addAll(closeButton, homeButton, easyButton, normalButton, hardButton);

        // Add the background image and buttonsPane to the StackPane
        root.getChildren().addAll(imageView, buttonsPane);

        homeButton.setOnAction(e -> {
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

    // Helper method to create navigation buttons
    private Button createNavigationButton(String buttonText, String screenName) {
        Button button = new Button(buttonText);
        button.setOnAction(e -> navigateToScreen(screenName));
        return button;
    }

    // Helper method to handle button actions (navigation)
    private void navigateToScreen(String screenName) {
        // Implement navigation logic based on the screenName
        System.out.println("Navigating to screen: " + screenName);
        // You can add code here to switch to different screens
    }

    public static void main(String[] args) {
        launch(args);
    }
}