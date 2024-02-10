package Controller;

import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Callback;
import Model.SysData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
    private CheckBox checkDifficulty;

    @FXML
    private CheckBox checkOrder;
    
    public AudioClip note;

    @FXML
    private TextField searchField;
    
    @FXML
    private Button searchbutton;
    
    private ObservableList<Question> dataQues;
    private ObservableList<Question> dataQues2;
    

    private boolean isSorted = false;
    private List<Question> originalOrder;

    @FXML
    void OrderDifficulty(ActionEvent event) {
        if (!isSorted) {
            // Save the original order before sorting
            originalOrder = new ArrayList<>(SysData.getInstance().getQuestions());
            
            ArrayList<Question> sortDiff = new ArrayList<>(originalOrder);
            ArrayList<Question> sorted = new ArrayList<>();

            for (Question q : sortDiff) {
                if (q.getDifficulty().equals(Difficulty.Easy)) {
                    sorted.add(q);
                }
            }
            for (Question q : sortDiff) {
                if (q.getDifficulty().equals(Difficulty.Medium)) {
                    sorted.add(q);
                }
            }
            for (Question q : sortDiff) {
                if (q.getDifficulty().equals(Difficulty.Hard)) {
                    sorted.add(q);
                }
            }
            ObservableList<Question> dataQuestion = FXCollections.observableArrayList(sorted);
            question.setCellValueFactory(new PropertyValueFactory<>("question"));
            difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
            ObservableList<Question> temp = FXCollections.observableArrayList(dataQuestion);
            questionTable.setItems(temp);

            isSorted = true;
        } else {
            // Restore the original order
            ObservableList<Question> temp = FXCollections.observableArrayList(originalOrder);
            questionTable.setItems(temp);
            
            isSorted = false;
        }
    }


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

    	Question deletedQ = new Question(deletedAnswer1,deletedAnswer2,deletedAnswer3,deletedAnswer4,deletedQuestion
    			,deletedDifficulty,deletedCorrect);
    	
    	if (Alerts.delete(deletedQuestion) == 1) {
    		SysData.getInstance().deleteFromJson(deletedQ);
    		SysData.getInstance().getQuestions().remove(deletedQ);
    	}
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
    	Tooltip a = new Tooltip("Add Question");
        Tooltip.install(add, a);
        Tooltip ed = new Tooltip("Edit");
        Tooltip.install(edit, ed);
        Tooltip d = new Tooltip("Delete");
        Tooltip.install(delete, d);
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

    	dataQues = FXCollections.observableArrayList(SysData.getInstance().getQuestions());
  		question.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));
		difficulty.setCellValueFactory(new PropertyValueFactory<Question, Difficulty>("difficulty"));
		
		// answers column
		Callback<TableColumn<Question, Void>, TableCell<Question, Void>> cellFactory =
		    new Callback<TableColumn<Question, Void>, TableCell<Question, Void>>() {
		        @Override
		        public TableCell<Question, Void> call(final TableColumn<Question, Void> param) {
		            final TableCell<Question, Void> cell = new TableCell<Question, Void>() {

		                private final ComboBox<String> cb = new ComboBox<>();
		                private final VBox pane = new VBox(1, cb);
		           
		                {
		                    pane.setAlignment(Pos.CENTER);
		                    cb.setMaxWidth(150);
		                    cb.setPrefWidth(150);
		                    

		                    cb.addEventHandler(ComboBox.ON_SHOWING, event -> {
		                        Question q = getTableView().getItems().get(getIndex());
		                        ArrayList<String> answers = new ArrayList<>();
		                        String a1 = q.getAnswer1();
		                        String a2 = q.getAnswer2();
		                        String a3 = q.getAnswer3();
		                        String a4 = q.getAnswer4();
		                        answers.add(a1);
		                        answers.add(a2);
		                        answers.add(a3);
		                        answers.add(a4);
		                        ObservableList<String> data = FXCollections.observableArrayList(answers);

		                        cb.setItems(data);
		                        
//		                        if (item == null || empty) {
//		                            setText(null);
//		                            setStyle(null);
//		                        } else {
//		                            setText(item);
//
//		                            // Check if the item is the one you want to style differently
//		                            if ("right answer".equals(item)) {
//		                                // Apply the fixed style to this specific cell
//		                                setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
//		                            } else {
//		                                // Reset style for other cells
//		                                setStyle(null);
//		                            }
//		                        }
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
  		dataQues2 =  FXCollections.observableArrayList(arr);
  		questionTable.setItems(dataQues2);
  		
//  		FilteredList<Question> filteredData = new FilteredList<>(dataQues2, b -> ture);

//  		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//  		filteredData.setPredicate(Question -> {
//  		if (newValue.isEmpty() || newValue == null) {
//  			return true;
//  			}
//  		
//  		String searchKeyword = newValue.toLowerCase();
//  		if (Question.getQuestion().toLowerCase().indexOf(searchKeyword) != -1) {
//  			return true; // Means we found a match in ProductName
//  			} 
//  			else
//  				return false;
//  		});
//  		});
//  		
  		// System.out.println(arr);
  		

  	}
    
    @FXML
    void SearchForAQuestion(ActionEvent event) {

    	String lowerCaseFilter = searchField.getText().toLowerCase();
    	if(lowerCaseFilter.length()!=0) {
    		HashSet<Question> arr= new HashSet<>();
    		for(Question q: SysData.getInstance().getQuestions()) {
    			if(q.getQuestion().toLowerCase().contains(lowerCaseFilter))
    				arr.add(q);
    		}
    		questionTable.setItems(FXCollections.observableArrayList(arr));
    	}
    	else 
    		questionTable.setItems(dataQues2);
    }
    
    @FXML
    void pressed(MouseEvent event) {
    	searchbutton.setStyle("fx-background-color: #FFF ; -fx-background-radius: 5 ; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 3 , 0 )");
    	
    }
    	
}
   