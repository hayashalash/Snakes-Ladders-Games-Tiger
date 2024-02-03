package View;

import java.util.Optional;

import Controller.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Alerts {

	
	/** delete alert
	 * after pressing on "delete", this alert will pop up. 
	 * @param content
	 * @return
	 */
	public static int delete(String content){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		Character ch = content.charAt(0);
		if (ch.equals('a')||ch.equals('e')||ch.equals('o')||ch.equals('u')||ch.equals('i'))
			alert.setHeaderText("You're about to delete an "+content);
		else
			alert.setHeaderText("You're about to delete a "+content);
		alert.setContentText("Are you sure you want to delete the selected "+content+"?");
		
		if (alert.showAndWait().get() == ButtonType.OK)
			return 1;
		return -1;
	}

	/** Update alert
	 * @return
	 */
	public static int edit() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Edit");
		alert.setHeaderText("Are you sure want to edit?");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//		stage.getIcons().add(new Image("img/logo.png"));

		stage.setAlwaysOnTop(true);
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == ButtonType.OK)
			return 1;
		return -1;

	}
	// alert to make sure the user wants to completely exit the game
	public static int exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("You're about to exit!");
		alert.setContentText("Are you sure you want to exit?");
		
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == ButtonType.OK)
			return 1;
		return -1;
    }
	/** Confirmation alert tells us that the item or question was added or edited successfully
	 * @param operation
	 */
	public static void confirmation(String operation) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Item was " + operation + " successfully");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//		stage.getIcons().add(Constants.SUCCESS_IMAGE); // change
		stage.setAlwaysOnTop(true);
		alert.show();

	}

	/** Warning alert
	 * @param content
	 */
	public static void warning(String content) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText(content);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//		stage.getIcons().add(new Image("img/logo.png"));
		stage.setAlwaysOnTop(true);
		alert.show();

	}

	/** message
	 * @param title
	 * @param content
	 */
	public static void message(String title,String content) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(content);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//		stage.getIcons().add(new Image("images/Success.png"));
		stage.setAlwaysOnTop(true);
		alert.show();

	}

}