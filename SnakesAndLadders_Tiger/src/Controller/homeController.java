package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class homeController implements Initializable{

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

    public void initialize(URL location, ResourceBundle resources) {
    	
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
    void showHistory(ActionEvent event) throws IOException{
    	newScreen("GameHistory");

    }

    @FXML
    void showInfo(ActionEvent event) throws IOException{
    	newScreen("Info");

    }

    @FXML
    void showQuestion(ActionEvent event) throws IOException{
    	newScreen("manageQuestion");
    }

  
}
