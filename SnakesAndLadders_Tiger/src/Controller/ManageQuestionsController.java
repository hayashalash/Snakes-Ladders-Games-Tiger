package Controller;

import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import Model.SysData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private TableColumn<Question, Integer> questionNum;
    
    @FXML
    private TableColumn<Question, Void> answers;

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
    private Button sortDiffButton;

    @FXML
    private Button sortQuesButton;


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
//		Question.idCounter--;
		fill();
    	questionTable.getItems().clear();
		fill();
		
    }
    
    
    @FXML
    void editQuestion(ActionEvent event) {
 
    		if(questionTable.getSelectionModel().getSelectedIndex() == -1) {
    			Alerts.message("Error", "Please choose question that you want to edit.");
    			return;
    		}
    		//the question that we want to update is selected
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
		questionNum.setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionID"));
		
		// answers column
		Callback<TableColumn<Question, Void>, TableCell<Question, Void>> cellFactory = new Callback<TableColumn<Question, Void>, TableCell<Question, Void>>() {
			@Override
			public TableCell<Question, Void> call(final TableColumn<Question, Void> param) {
				final TableCell<Question, Void> cell = new TableCell<Question, Void>() {

					private final ComboBox <String> cb = new ComboBox<>();

					private final VBox pane = new VBox(1, cb);

					{
						pane.setAlignment(Pos.CENTER);
						cb.setMaxWidth(150);
						cb.setPrefWidth(150);
						cb.addEventHandler(ComboBox.ON_SHOWING, event -> {
							Question q = getTableView().getItems().get(getIndex());
							ArrayList<String> answers = new ArrayList();
							String a1 = q.getAnswer1();
							String a2 = q.getAnswer2();
							String a3 = q.getAnswer3();
							String a4 = q.getAnswer4();
							answers.add(a1);
							answers.add(a2);
							answers.add(a3);
							answers.add(a4);
							ObservableList<String> data;
							data = FXCollections.observableArrayList(answers);

							cb.setItems(data);
						});
					
					}
					@Override
					protected void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);

						setGraphic(empty ? null : pane);
						
					}
					
				};
				return cell;
			}
		};
		answers.setCellFactory(cellFactory);
		
		HashSet<Question> arr = new HashSet<>();
  		arr.addAll(dataQues);
  		ObservableList<Question>dataQues2 =  FXCollections.observableArrayList(arr);
  		questionTable.setItems(dataQues2);
  		// System.out.println(arr);

  	}
    
    @FXML
    void sortDifficulty(ActionEvent event) {
    	ArrayList<Question> sortDiff = new ArrayList<>(SysData.getInstance().getQuestions());
    	ArrayList<Question> sorted = new ArrayList<>();

    	for(Question q : sortDiff) {
    		if(q.getDifficulty().equals(Difficulty.Easy)) {
    			sorted.add(q);
    		}
    	}
    	for(Question q : sortDiff) {
    		if(q.getDifficulty().equals(Difficulty.Medium)){
    			sorted.add(q);
    		}
    	}
		for(Question q : sortDiff) {
    		if(q.getDifficulty().equals(Difficulty.Hard)) {
    			sorted.add(q);
    		}
		}
		ObservableList<Question> dataQuestion = FXCollections.observableArrayList(sorted);
		question.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));
		difficulty.setCellValueFactory(new PropertyValueFactory<Question, Difficulty>("difficulty"));
  		ObservableList<Question>temp =  FXCollections.observableArrayList(dataQuestion);
  		questionTable.setItems(temp);
    }

    @FXML
    void sortQuestion(ActionEvent event) {

    }
    


}
