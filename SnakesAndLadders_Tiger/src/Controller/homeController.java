package Controller;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class homeController {

    @FXML
    private Button history;

    @FXML
    private Button question;

    @FXML
    private Button exit;

    @FXML
    private Button info;
    
    @FXML
    private Button editbutton;

    @FXML
    void editt(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/manageQuestion.fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}    }

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
    void showHistory(ActionEvent event) {
    	newScreen("History");

    }

    @FXML
    void showInfo(ActionEvent event) {
    	newScreen("Info");

    }

    @FXML
    void showQuestion(ActionEvent event) {
    	newScreen("manageQuestion");
    }

  
}
