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
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
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
import javafx.stage.StageStyle;

public class homeController implements Initializable{

	private static final String ADMIN = "/img/screens/AdminLogin.jpg";
	private static final String EXIT_ICON = "/img/icons/X.png";

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
    		turnOffIcon.setOpacity(1.0);
    		Main.stopBackgroundMusic();
    	}
    	else {
    		turnOffIcon.setOpacity(0.0);
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
    	openAdminDialog();
    }
    
    private void openAdminDialog() {
        // Create a custom dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Admin Access Only");
        dialog.initStyle(StageStyle.UNDECORATED);
        ImageView imageView = new ImageView(new Image(ADMIN));
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        dialog.getDialogPane().setPrefWidth(300);
        dialog.getDialogPane().setPrefHeight(300);

        PasswordField textField = new PasswordField();
        textField.setPrefHeight(40);
        textField.setMaxWidth(200);
        textField.setPromptText("Enter Password");
        textField.setStyle("-fx-border-radius: 10;");
        Button logInButton = new Button("Log In");
        logInButton.setPrefHeight(40);
        logInButton.setPrefWidth(200); // Set the same width as the text field
        logInButton.setPadding(new Insets(5, 5, 5, 5));
        logInButton.setStyle("-fx-border-radius: 20; -fx-background-color: #dfbc95; " + methods.getButtonStyle()); // Drop shadow effect
        logInButton.setOnMouseEntered(e -> entered(e));
        logInButton.setOnMouseExited(e -> exited(e));
        Label errorLabel = new Label("Incorrect Password");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setOpacity(0);
        // Add the elements to an HBox layout
        VBox vbox = new VBox(textField, errorLabel, logInButton);

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(2); // Set spacing between the text field and button
        vbox.setPadding(new Insets(180, 0, 0, 0)); // Set padding around the layout
        
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

     // Add cancel button
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);
        ImageView exitIcon = new ImageView(new Image(EXIT_ICON));
        exitIcon.setFitWidth(20);
        exitIcon.setFitHeight(20);
        cancelButton.setGraphic(exitIcon);
        cancelButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 0;");
        cancelButton.setCursor(Cursor.HAND); // Set cursor to hand
        StackPane.setAlignment(cancelButton, Pos.TOP_RIGHT);
        StackPane.setMargin(cancelButton, new Insets(10));

        content.setPadding(new Insets(0)); // Set padding of the StackPane to zero
        // Set the layout as the content of the dialog
        dialog.getDialogPane().setContent(content);

        // Show the dialog
        dialog.showAndWait();
    }

}
