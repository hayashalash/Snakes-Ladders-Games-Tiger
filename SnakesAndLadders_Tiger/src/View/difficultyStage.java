package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javafx.application.Application;
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
    // Define a constant for difficulty
    private static final String DIFFICULTY_EASY = "easy";
    private static final String DIFFICULTY_NORMAL = "normal";
    private static final String DIFFICULTY_HARD = "hard";
    // Variable to store the selected difficulty
	private String selectedDifficulty;
    private Stage primaryStage; 

	
    // Public method to get the selected difficulty
    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }

    @Override
    public void start(Stage primaryStage) {
    	
    	this.primaryStage = primaryStage; 
        primaryStage.setTitle("Difficulty");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Create a StackPane as the root node
        StackPane root = new StackPane();

        // Set the preferred size of the root node
        root.setPrefSize(S_WIDTH, S_HEIGHT);

        // Load the image from the package
        Image backgroundImage = new Image(getClass().getResource("/img/screens/difficultyBack.jpg").toExternalForm());

        // Create an ImageView to display the image
        ImageView imageView = new ImageView(backgroundImage);

        // Set the size of the image view to match the scene size
        imageView.setFitWidth(S_WIDTH);
        imageView.setFitHeight(S_HEIGHT);
        
        
        // Create a Pane for buttons
        Pane buttonsPane = new Pane();
        buttonsPane.setPrefSize(S_WIDTH, S_HEIGHT);
        
        // Load the icons from the package
        Image closeIconImage = new Image(getClass().getResource("/img/icons/X.png").toExternalForm());
        Image homeIconImage = new Image(getClass().getResource("/img/icons/home.png").toExternalForm());
        Image easyIconImage = new Image(getClass().getResource("/img/icons/easy.png").toExternalForm());
        Image normalIconImage = new Image(getClass().getResource("/img/icons/normal.png").toExternalForm());
        Image hardIconImage = new Image(getClass().getResource("/img/icons/hard.png").toExternalForm());


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
        easyButton.setOnAction(e -> handleDifficultySelection(DIFFICULTY_EASY));

        Button normalButton = createIconButton(normalIconImageView);
        normalButton.setOnAction(e -> handleDifficultySelection(DIFFICULTY_NORMAL));

        Button hardButton = createIconButton(hardIconImageView);
        hardButton.setOnAction(e -> handleDifficultySelection(DIFFICULTY_HARD));


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
    
    private void handleDifficultySelection(String difficulty) {
        this.selectedDifficulty = difficulty;
        System.out.println("Selected Difficulty: " + selectedDifficulty);

        // Open choosePlayerStage when a difficulty button is pressed
        openChoosePlayerStage();
//        newScreen("ChoosePlayers");
    }
    
//    void newScreen(String path) { // open a new fxml screen
//    	// Close the current stage
//        primaryStage.close();
//    	try {
//			Parent root = FXMLLoader.load(getClass().getResource("/View/"+path+".fxml"));
//			Scene scene = new Scene(root);
//			Controller.main.mainWindow.setScene(scene);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}  	
//    }

    private void openChoosePlayerStage() {
        // Close the current stage
        primaryStage.close();

        // Create an instance of choosePlayerStage
        choosePlayersStage choosePlayerInstance = new choosePlayersStage();

        // Call the start method to initialize the new window
        try {
            choosePlayerInstance.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exceptions as needed
        }
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
    
    
    
    /*

     package View;

public class CallFromAnotherClass {

    public static void main(String[] args) {
        // Create an instance of difficultyStage
        difficultyStage difficultyInstance = new difficultyStage();

        // Call the start method to initialize the stage
        try {
            difficultyInstance.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // to retrieve the selected difficulty
        String selectedDifficulty = difficultyInstance.getSelectedDifficulty();
        System.out.println("Selected Difficulty from CallFromAnotherClass: " + selectedDifficulty);
    }
}

     */

    
}