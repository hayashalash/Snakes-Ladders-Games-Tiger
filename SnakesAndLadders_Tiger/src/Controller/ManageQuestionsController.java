package Controller;

import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.SysData;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import com.sun.glass.ui.View;

import Model.Difficulty;
import Model.Game;
import Model.Question;

public class ManageQuestionsController implements Initializable {
	
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
    	newScreen("addQuestion");
    }

    @FXML
    void deleteQuestion(ActionEvent event) throws IOException, ParseException {
    	if(questionTable.getSelectionModel().getSelectedIndex() == -1)
    		Alerts.message("Error","Please select a question to delete");
    		return;
    }
    String qustion = questionTable.getSelectionModel().getSelectedItem().getQuestion();
    String ans1 = questionTable.getSelectionModel().getSelectedItem().getAnswer1();
    String ans2 = questionTable.getSelectionModel().getSelectedItem().getAnswer2();
    String ans3 = questionTable.getSelectionModel().getSelectedItem().getAnswer3();
    String ans4 = questionTable.getSelectionModel().getSelectedItem().getAnswer4();
    Difficulty diff = questionTable.getSelectionModel().getSelectedItem().getDifficulty();
	Integer corr = questionTable.getSelectionModel().getSelectedItem().getCorrectAnswer();
	
	Question newQues = new Question(ans1,ans2,ans3,ans4,question,diff,corr);



    
    
    @FXML
    void editQuestion(ActionEvent event) {
    	newScreen("editQuestion");
    }

    @FXML
    void exitGame(ActionEvent event) {
		if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void returnHome(ActionEvent event) {
    	newScreen("Home");
    }
    void newScreen(String path) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/"+path+".fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    }
    @Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			SysData.getInstance().importJson();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fill();
		questionTable.refresh();
		
	}
    public void fill() {
  		ObservableList<Question> dataQues = FXCollections.observableArrayList(SysData.getInstance().getQuestions());
  		question.setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));
  		diff.setCellValueFactory(new PropertyValueFactory<Question, String>("diffuclty"));
  		answer1.setCellValueFactory(new PropertyValueFactory<Question, String>("answer1"));
  		answer2.setCellValueFactory(new PropertyValueFactory<Question, String>("answer2"));
  		answer3.setCellValueFactory(new PropertyValueFactory<Question, String>("answer3"));
  		answer4.setCellValueFactory(new PropertyValueFactory<Question, String>("answer4"));
  		HashSet<Question> set = new HashSet<>();
  		set.addAll(dataQues);
  		ObservableList<Question>dataQues2 =  FXCollections.observableArrayList(set);
  		questionTable.setItems(dataQues2);
  	}

}
