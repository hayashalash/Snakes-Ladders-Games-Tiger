package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;

public class homeStage extends Application {

    private Stage stage;
    private Button closeButton;
    private Button infoButton;
    private Button historyButton;
    private Button qaButton;
    private Button startButton;

    private static final int S_WIDTH = 852;
    private static final int S_HEIGHT = 595;



    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        stage.setTitle("Home");
        stage.initStyle(StageStyle.UNDECORATED);

        // Create a StackPane as the root node
        StackPane root = new StackPane();

        // Set the preferred size of the root node
        root.setPrefSize(S_WIDTH, S_HEIGHT);

        // Load the image from the same package
        Image backgroundImage = new Image(getClass().getResource("/img/screens/homeBack.jpg").toExternalForm());

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
        Image historyIconImage = new Image(getClass().getResource("/img/icons/history.png").toExternalForm());
        Image qaIconImage = new Image(getClass().getResource("/img/icons/Q&A.png").toExternalForm());
        Image startIconImage = new Image(getClass().getResource("/img/icons/start.png").toExternalForm());

        // Create ImageViews to display the icons
        ImageView closeIconImageView = new ImageView(closeIconImage);
        ImageView infoIconImageView = new ImageView(infoIconImage);
        ImageView historyIconImageView = new ImageView(historyIconImage);
        ImageView qaIconImageView = new ImageView(qaIconImage);
        ImageView startIconImageView = new ImageView(startIconImage);

        // Set the size of the icons
        closeIconImageView.setFitWidth(30);
        closeIconImageView.setFitHeight(30);
        infoIconImageView.setFitWidth(50);
        infoIconImageView.setFitHeight(50);
        historyIconImageView.setFitWidth(60);
        historyIconImageView.setFitHeight(60);
        qaIconImageView.setFitWidth(60);
        qaIconImageView.setFitHeight(60);
        startIconImageView.setFitWidth(130);
        startIconImageView.setFitHeight(62);


        // Create buttons with icons
        infoButton = createIconButton(infoIconImageView);
        historyButton = createIconButton(historyIconImageView);
        qaButton = createIconButton(qaIconImageView);
        startButton = createIconButton(startIconImageView);

        // Set the positions of buttons using coordinates within the Pane
        closeButton.setLayoutX(800);
        closeButton.setLayoutY(10);
        infoButton.setLayoutX(100);
        infoButton.setLayoutY(100);
        historyButton.setLayoutX(620);
        historyButton.setLayoutY(100);
        qaButton.setLayoutX(700);
        qaButton.setLayoutY(100);
        startButton.setLayoutX(360);
        startButton.setLayoutY(350);

        // Add buttons to the Pane
        buttonsPane.getChildren().addAll(closeButton, infoButton, historyButton, qaButton, startButton);

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


	public void closeStage() {
		stage.close();
		
	}

	public Button getCloseButton() {
        return closeButton;
    }

    public Button getInfoButton() {
        return infoButton;
    }

    public Button getHistoryButton() {
        return historyButton;
    }

    public Button getQaButton() {
        return qaButton;
    }

    public Button getStartButton() {
        return startButton;
    }

}
