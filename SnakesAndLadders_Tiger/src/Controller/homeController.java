package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.swing.SwingUtilities;

import Model.Admin;
import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class homeController implements Initializable{

	private static final String ADMIN = "/img/screens/admin.jpg";
	private static final String INFO_IMAGE_PATH = "/img/screens/blank.jpg";
	private final double ICON_SIZE = 35; // the moving icons on the board

	
	
    @FXML
    private Button history;

    @FXML
    private Button question;

    @FXML
    private Button exit;

    @FXML
    private Button info;
    
    @FXML
    private Button start;

    @FXML
    private ImageView turnOffIcon = new ImageView();

    @FXML
    private Button musicOff;
    
    public AudioClip note = new AudioClip(this.getClass().getResource("/img/wavs/sound.mp3").toString());

    @FXML
    void TurnOffOn(ActionEvent event) {
    	if (turnOffIcon.getOpacity() == 0.0) { // if music is on
			note.stop();
			turnOffIcon.setOpacity(1.0);
		}
    	else { // is music is off
    		note.play();
			note.setVolume(0.2);
    		turnOffIcon.setOpacity(0.0);
    	}
    }

    public void initialize(URL location, ResourceBundle resources) {
    	turnOffIcon.setMouseTransparent(true);
        Tooltip h = new Tooltip("History");
        Tooltip.install(history, h);
        Tooltip q = new Tooltip("Questions");
        Tooltip.install(question, q);
        Tooltip r = new Tooltip("Game Rules");
        Tooltip.install(info, r);

    	if (note.isPlaying()) {
    		turnOffIcon.setOpacity(0.0);
    		note.play();
    	}
    	else {
    		turnOffIcon.setOpacity(1.0);
        	note.stop();
    	}
    }
    
    @FXML
    void start(ActionEvent event) {
    	newScreen("ChooseDifficulty");
    }

    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
    
    void newScreen(String path) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/"+path+".fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
			Main.mainWindow.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    }

    @FXML
    void entered(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1.1);
    	((Node)event.getSource()).setScaleY(1.1);
    }
    @FXML
    void exited(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1);
    	((Node)event.getSource()).setScaleY(1);
    }
    
    @FXML
    void showHistory(ActionEvent event) throws IOException{
    	newScreen("GameHistory");
    }

    @FXML
    void showInfo(ActionEvent event) throws IOException{
    	Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Game Rules");
        dialog.setHeaderText("");
        dialog.setWidth(800);
        dialog.setHeight(500);
        Image info = new Image(getClass().getResource(INFO_IMAGE_PATH).toExternalForm());
		ImageView background = new ImageView(info);
		background.setFitHeight(dialog.getHeight());
		background.setFitWidth(dialog.getWidth());
		background.setVisible(true);
		ArrayList<Label> labels = new ArrayList<>();
		Label diceL = new Label("Roll the dice to determine your fate");
		labels.add(diceL);
		Label turnL = new Label("The green border indicates your turn");
		labels.add(turnL);
		Label snakeL = new Label("Encounter snakes and slide down");
		labels.add(snakeL);
		Label ladderL = new Label("Find ladders and climb up");
		labels.add(ladderL);
		Label questionL = new Label("Land on a question mark or get one on the dice roll and answer questions for your destiny");
		labels.add(questionL);
		Label surpriseL = new Label("Surprises can move you forward or backward");
		labels.add(surpriseL);
		Label winL = new Label("Be the first to reach the last tile to claim victory!");
		for (Label l :labels) {
			l.setStyle("-fx-font-family: Serif; -fx-font-size: 17px;");
			l.setPadding(new Insets(10,0,5,10)); // top right bottom left
		}
		winL.setStyle("-fx-font-family: Serif; -fx-font-size: 22px;");
		ArrayList<ImageView> imgs = new ArrayList<>();
		Image dice = new Image(getClass().getResource("/img/icons/dice.png").toExternalForm());
		ImageView diceIV = new ImageView(dice);
		imgs.add(diceIV);
		Image pawn = new Image(getClass().getResource("/img/icons/pawn.png").toExternalForm());
		ImageView pawnIV = new ImageView(pawn);
		Label name = new Label ("yourName");
		name.setPadding(new Insets(7,5,5,0));
		name.setStyle("-fx-font-size: 10px;");

		HBox pawnTurn = new HBox(pawnIV, name);
		pawnTurn.setStyle("-fx-border-color: #00FF00; -fx-border-radius: 10; -fx-border-width: 3;");
		imgs.add(pawnIV);
		Image snake = new Image(getClass().getResource("/img/icons/redSnake.png").toExternalForm());
		ImageView snakeIV = new ImageView(snake);
		imgs.add(snakeIV);
		Image ladder = new Image(getClass().getResource("/img/icons/ladderIcon.png").toExternalForm());
		ImageView ladderIV = new ImageView(ladder);
		imgs.add(ladderIV);
		Image q = new Image(getClass().getResource("/img/icons/question.png").toExternalForm());
		ImageView qIV = new ImageView(q);
		imgs.add(qIV);
		Image s = new Image(getClass().getResource("/img/icons/surprise.png").toExternalForm());
		ImageView sIV = new ImageView(s);
		imgs.add(sIV);
		
		for(ImageView iv : imgs) {
			iv.setFitHeight(ICON_SIZE);
			iv.setFitWidth(ICON_SIZE);
		}
		VBox vbox = new VBox(new HBox(diceIV, diceL), new HBox(pawnTurn, turnL), new HBox(snakeIV, snakeL), 
				new HBox(ladderIV, ladderL), new HBox(qIV, questionL), new HBox(sIV, surpriseL), winL);
		vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10); // Set spacing between the lines
        vbox.setPadding(new Insets(0, 20, 50, 80)); // top right bottom left
        StackPane content = new StackPane(background, vbox);
        dialog.getDialogPane().setContent(content);

        ButtonType closeButton = new ButtonType("Close", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);

        dialog.showAndWait();
    }

    @FXML
    void showQuestion(ActionEvent event) throws IOException{
    	openCustomDialog();
    }
    
    private void openCustomDialog() {
        // Create a custom dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Admin Access Only");
        ImageView imageView = new ImageView(new Image(ADMIN));
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);

        PasswordField textField = new PasswordField();
        textField.setPrefHeight(20);
        textField.setMaxWidth(150);
        textField.setPromptText("Enter Password");
        Button logInButton = new Button("Log In");
        logInButton.setPadding(new Insets(5, 5, 5, 5));
        // Apply CSS styles to the button
        logInButton.setStyle("-fx-background-color: #D2691E; " +  // Background color
                "-fx-text-fill: white; " +           // Text color
                "-fx-font-size: 14px; " +            // Font size
                "-fx-font-family: Serif; " +         // Font family
                "-fx-background-radius: 5px; " +     // Background radius
                "-fx-border-radius: 5px; " +         // Border radius
                "-fx-border-color: #DEB887;" +      // Border color
        		"-fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 3 , 0 );"); // Drop shadow effect
        logInButton.setOnMouseEntered(e -> entered(e));
        logInButton.setOnMouseExited(e -> exited(e));
        Label errorLabel = new Label("Incorrect password");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setOpacity(0);
        // Add the elements to an HBox layout
        VBox vbox = new VBox(textField, errorLabel, logInButton);

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5); // Set spacing between the text field and button
        vbox.setPadding(new Insets(140, 0, 0, 0)); // Set padding around the layout
        
        logInButton.setOnAction(e -> {
        	String p = textField.getText();
        	if (Admin.getInstance().checkPassword(p)) {
        		newScreen("manageQuestion");
        		dialog.close();
        	}
        	else {
        		textField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
        		// Show a label with red text underneath the text field to notify the user of a wrong password
                errorLabel.setOpacity(1);
        	}
        });
        logInButton.setDefaultButton(true);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
        	// If the user starts typing, remove the error label if it exists
            if (errorLabel.getOpacity() != 0) {
                errorLabel.setOpacity(0);
                // Reset the style of the text field to its default state
                textField.setStyle("");
            }
	    });
        // Add the background image and the elements to a layout
        StackPane content = new StackPane();
        content.getChildren().addAll(imageView, vbox);
        // Set the layout as the content of the dialog
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // Show the dialog
        dialog.showAndWait();
    }

  
}
