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
import java.util.ArrayList;
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

    @FXML
    void addQuestion(ActionEvent event) {
    	newScreen("AddQuestion");
    }

//    @FXML
//    void deleteQuestion(ActionEvent event) throws IOException, ParseException {
//    	if(questionTable.getSelectionModel().getSelectedIndex() == -1) {
//    		Alerts.message("Error","Please select a question to delete");
//    		return;
//    }
//    
//    }
    
    
    @FXML
    void editQuestion(ActionEvent event) {
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
  		question.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));
		difficulty.setCellValueFactory(new PropertyValueFactory<Question, Difficulty>("diffiuclty"));
  		ArrayList<Question> arr = new ArrayList<>();
  		arr.addAll(dataQues);
  		ObservableList<Question>dataQues2 =  FXCollections.observableArrayList(arr);
  		questionTable.setItems(dataQues2);
  	}

}
