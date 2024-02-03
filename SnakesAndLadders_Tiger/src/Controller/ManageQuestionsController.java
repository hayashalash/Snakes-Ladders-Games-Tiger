package Controller;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.sun.glass.ui.View;

import Model.Game;
import Model.Question;

public class ManageQuestionsController {
	
	@FXML
	 private TableView<Question> questionTable;

    @FXML
    private TableColumn<Question, String> question;

    @FXML
    private TableColumn<Question, String> answer1;

    @FXML
    private TableColumn<Question, String> answer2;

    @FXML
    private TableColumn<Question, String> answer3;

    @FXML
    private TableColumn<Question, String> answer4;

    @FXML
    private TableColumn<Question, String> difficulty;

    @FXML
    private Button delete;

    @FXML
    private Button edit;

    @FXML
    private Button add;

    @FXML
    private Button home;

    @FXML
    private Button exit;

    @FXML
    void addQuestion(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("addQuestion.fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    	
    }

    @FXML
    void deleteQuestion(ActionEvent event) throws IOException, ParseException {
    	if(questionTable.getSelectionModel().getSelectedIndex() == -1)
    		Alerts.message("Error","Please select a question to delete");
    		return;
    }
    String qustion = questionTable.getSelectionModel().getSelectedItem().getQuestion();
    
    
    @FXML
    void editQuestion(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("editQuestion.fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    }

    @FXML
    void exitGame(ActionEvent event) {

    }

    @FXML
    void returnHome(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    

    }

}
