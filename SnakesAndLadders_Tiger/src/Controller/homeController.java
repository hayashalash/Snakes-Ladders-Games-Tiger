package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Model.Admin;
import Model.SysData;
import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class homeController implements Initializable{

	private static final String ADMIN = "/img/screens/admin.jpg";

	Methods methods = new Methods();
	
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
    
    //public AudioClip note = new AudioClip(this.getClass().getResource("/img/wavs/sound.mp3").toString());

    @FXML
    void TurnOffOn(ActionEvent event) {
    	if (Main.note.isPlaying()) {
    		turnOffIcon.setOpacity(0.0);
    		Main.stopBackgroundMusic();
    	}
    	else {
    		turnOffIcon.setOpacity(1.0);
    		Main.resumeBackgroundMusic();
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

        try {
			SysData.getInstance().readFromJson();
			SysData.getInstance().ReadFromJsonGames();

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
    	if (Main.note.isPlaying()) {
    		turnOffIcon.setOpacity(0.0);
    		//Main.stopBackgroundMusic();
    	}
    	else {
    		turnOffIcon.setOpacity(1.0);
    		//Main.resumeBackgroundMusic();
    	}
    }
    
    @FXML
    void start(ActionEvent event) {
    	methods.newScreen("ChooseDifficulty");
    }

    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void entered(MouseEvent event){
    	methods.entered(event);
    }
    @FXML
    void exited(MouseEvent event){
    	methods.exited(event);
    }
    
    @FXML
    void showHistory(ActionEvent event) throws IOException{
    	methods.newScreen("GameHistory");
    }

    @FXML
    void showInfo(ActionEvent event) throws IOException{
    	methods.newScreen("Info");
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
        logInButton.setStyle(methods.getButtonStyle()); // Drop shadow effect
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
        		methods.newScreen("manageQuestion");
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
