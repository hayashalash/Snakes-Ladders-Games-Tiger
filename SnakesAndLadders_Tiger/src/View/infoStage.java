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


public class infoStage extends Application {

    private static final int S_WIDTH = 852; 
    private static final int S_HEIGHT = 595; 

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Information");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Create a StackPane as the root node
        StackPane root = new StackPane();

        // Set the preferred size of the root node 
        root.setPrefSize(S_WIDTH, S_HEIGHT);

        // Load the image from the same package
        Image backgroundImage = new Image(getClass().getResource("/img/screens/infoBack.jpg").toExternalForm());

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(backgroundImage);

        // Set the size of the image view to match the scene size
        imageView.setFitWidth(S_WIDTH);
        imageView.setFitHeight(S_HEIGHT);
        
        
        // Create a Pane for buttons
        Pane buttonsPane = new Pane();
        buttonsPane.setPrefSize(S_WIDTH, S_HEIGHT);
        
        // Load the icons from the same package
        Image closeIconImage = new Image(getClass().getResource("/img/icons/X.png").toExternalForm());
        Image infoIconImage = new Image(getClass().getResource("/img/icons/info.png").toExternalForm());
        Image homeIconImage = new Image(getClass().getResource("/img/icons/home.png").toExternalForm());


         // Create ImageViews to display the icons
        ImageView closeIconImageView = new ImageView(closeIconImage);
        ImageView infoIconImageView = new ImageView(infoIconImage);
        ImageView homeIconImageView = new ImageView(homeIconImage);


        
        // Set the size of the icons
        closeIconImageView.setFitWidth(30);
        closeIconImageView.setFitHeight(30);
        infoIconImageView.setFitWidth(50);
        infoIconImageView.setFitHeight(50);
        homeIconImageView.setFitWidth(40);
        homeIconImageView.setFitHeight(40);

        
     // Create Close button with icon
        Button closeButton = new Button();
        closeButton.setGraphic(closeIconImageView);
        closeButton.setOnAction(e -> primaryStage.close());
        closeButton.setStyle("-fx-background-color: transparent;"); // Make the button transparent
        
        // Create buttons with icons
        Button infoButton = createIconButton(infoIconImageView);
        Button homeButton = createIconButton(homeIconImageView);



        // Set the positions of buttons using coordinates within the Pane
        closeButton.setLayoutX(800);
        closeButton.setLayoutY(10);

        infoButton.setLayoutX(100);
        infoButton.setLayoutY(100);

        homeButton.setLayoutX(700);
        homeButton.setLayoutY(100);


        // Add buttons to the Pane
        buttonsPane.getChildren().addAll(closeButton, infoButton, homeButton);

        // Add the background image and buttonsPane to the StackPane
        root.getChildren().addAll(imageView, buttonsPane);


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