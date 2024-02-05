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


import Model.Difficulty;
import Model.Question;

public class ManageQuestionsController implements Initializable {
	
	@FXML
	 private TableView<Question> questionTable;

    @FXML
    private TableColumn<Question, String> question;

    @FXML
    private TableColumn<Question, Difficulty> difficulty;

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

	private static Question editSelection;

    @FXML
    void addQuestion(ActionEvent event) {
    	newScreen("AddQuestion");
    }

    @FXML
    void deleteQuestion(ActionEvent event) throws IOException, ParseException {
    	if(questionTable.getSelectionModel().getSelectedIndex() == -1) {
    		Alerts.message("Error","Please select a question to delete.");
    		return;
    	}
    	String deletedQuestion = questionTable.getSelectionModel().getSelectedItem().getQuestion();
    	Difficulty deletedDifficulty = questionTable.getSelectionModel().getSelectedItem().getDifficulty();
    	String deletedAnswer1 = questionTable.getSelectionModel().getSelectedItem().getAnswer1();
    	String deletedAnswer2 = questionTable.getSelectionModel().getSelectedItem().getAnswer2();
    	String deletedAnswer3 = questionTable.getSelectionModel().getSelectedItem().getAnswer3();
    	String deletedAnswer4 = questionTable.getSelectionModel().getSelectedItem().getAnswer4();
    	Integer deletedCorrect = questionTable.getSelectionModel().getSelectedItem().getCorrectAnswer();

    	Question deletedQuestions = new Question(deletedAnswer1,deletedAnswer2,deletedAnswer3,deletedAnswer4,deletedQuestion
    			,deletedDifficulty,deletedCorrect);
    	SysData.getInstance().deleteFromJson(deletedQuestions);
    	Alerts.delete(deletedQuestion);
		SysData.getInstance().getQuestions().removeAll(SysData.getInstance().deleted);
		fill();
    	questionTable.getItems().clear();
		fill();
		
    }
    
    
    @FXML
    void editQuestion(ActionEvent event) {
 
    		if(questionTable.getSelectionModel().getSelectedIndex() == -1) {
    			Alerts.message("Error", "Please choose question that you want to edit.");
    		}
    		//question to update is question selected
    		EditQuestionController.edited = questionTable.getSelectionModel().getSelectedItem();
    		newScreen("EditQuestion");
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
			SysData.getInstance().readFromJson();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fill();
		questionTable.refresh();
		
	}
    public void fill() {
    	
  		ObservableList<Question> dataQues = FXCollections.observableArrayList(SysData.getInstance().getQuestions());
  		question.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));
		difficulty.setCellValueFactory(new PropertyValueFactory<Question, Difficulty>("difficulty"));
		HashSet<Question> arr = new HashSet<>();
  		arr.addAll(dataQues);
  		ObservableList<Question>dataQues2 =  FXCollections.observableArrayList(arr);
  		questionTable.setItems(dataQues2);
  		// System.out.println(arr);

  	}
    


}
