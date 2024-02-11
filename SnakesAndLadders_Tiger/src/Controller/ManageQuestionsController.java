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
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import Model.SysData;
import javafx.application.Application;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

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
		 questionNum.setCellValueFactory(cellData -> {
	            int rowIndex = questionTable.getItems().indexOf(cellData.getValue()) + 1;
	            return javafx.beans.binding.Bindings.createObjectBinding(() -> rowIndex);
	        });
		 searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			 SearchForAQuestion(newValue);
	        });

		
		// answers column
		Callback<TableColumn<Question, Void>, TableCell<Question, Void>> cellFactory =
		    new Callback<TableColumn<Question, Void>, TableCell<Question, Void>>() {
		        @Override
		        public TableCell<Question, Void> call(final TableColumn<Question, Void> param) {
		            final TableCell<Question, Void> cell = new TableCell<Question, Void>() {

		                private final Button btn= new Button("View Answers");
		                {
		                btn.setStyle(
		                        "-fx-background-color: #FFFACD; " +  // Green background color
		                        "-fx-text-fill: Black; " +           
		                        "-fx-min-width: 80px; " +  // Set minimum width
		                        "-fx-min-height: 10px;"             // Padding
		                );
		                btn.setOnMouseEntered(event -> {
		                    ((Node) event.getSource()).setScaleX(1.1);
		                    ((Node) event.getSource()).setScaleY(1.1);
		                    ((Node) event.getSource()).setCursor(Cursor.HAND);

		                });

		                btn.setOnMouseExited(event -> {
		                    ((Node) event.getSource()).setScaleX(1);
		                    ((Node) event.getSource()).setScaleY(1);
		                    ((Node) event.getSource()).setCursor(Cursor.DEFAULT);

		                });}
		                private final VBox pane = new VBox(1, btn);
		                {
		                	pane.setAlignment(Pos.CENTER);
		                    btn.setMaxWidth(100);
		                    btn.setPrefWidth(100);
		                }
		                {
		                	btn.setOnAction(e -> {
		                        Question q = getTableView().getItems().get(getIndex());
		                        int correctAnswerIndex = q.getCorrectAnswer();
		                        String correctAnswer = "";

		                        switch (correctAnswerIndex) {
		                            case 1:
		                                correctAnswer = q.getAnswer1();
		                                break;
		                            case 2:
		                                correctAnswer = q.getAnswer2();
		                                break;
		                            case 3:
		                                correctAnswer = q.getAnswer3();
		                                break;
		                            case 4:
		                                correctAnswer = q.getAnswer4();
		                                break;
		                        }

		                        Dialog<Void> dialog = new Dialog<>();
		                        dialog.setTitle("Question & Answers");
		                        dialog.setHeaderText(q.getQuestion());

		                        TextFlow contentText = new TextFlow();

		                        Text answer1 = new Text("1. " + formatAnswer(1, q.getAnswer1(), correctAnswerIndex) + "\n\n");
		                        Text answer2 = new Text("2. " + formatAnswer(2, q.getAnswer2(), correctAnswerIndex) + "\n\n");
		                        Text answer3 = new Text("3. " + formatAnswer(3, q.getAnswer3(), correctAnswerIndex) + "\n\n");
		                        Text answer4 = new Text("4. " + formatAnswer(4, q.getAnswer4(), correctAnswerIndex));

		                        // Apply styling to the correct answer
		                        if (correctAnswerIndex == 1) {
		                            answer1.setStyle("-fx-font-weight: bold; -fx-fill: green;");
		                        } else if (correctAnswerIndex == 2) {
		                            answer2.setStyle("-fx-font-weight: bold; -fx-fill: green;");
		                        } else if (correctAnswerIndex == 3) {
		                            answer3.setStyle("-fx-font-weight: bold; -fx-fill: green;");
		                        } else if (correctAnswerIndex == 4) {
		                            answer4.setStyle("-fx-font-weight: bold; -fx-fill: green;");
		                        }

		                        contentText.getChildren().addAll(answer1, answer2, answer3, answer4);

		                        dialog.getDialogPane().setContent(contentText);

		                        ButtonType closeButton = new ButtonType("Close", ButtonData.OK_DONE);
		                        dialog.getDialogPane().getButtonTypes().add(closeButton);

		                        dialog.showAndWait();
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
  		

  	}
    
    
        void SearchForAQuestion(String filter) {
    	
        	 String lowerCaseFilter = filter.toLowerCase();

             if (lowerCaseFilter.length() != 0) {
                 ObservableList<Question> filteredList = FXCollections.observableArrayList();

                 for (Question q : SysData.getInstance().getQuestions()) {
                     if (q.getQuestion().toLowerCase().contains(lowerCaseFilter)) {
                         filteredList.add(q);
                     }
                 }

                 questionTable.setItems(filteredList);
             } else {
                 questionTable.setItems(dataQues2);
             }
         }
    
    @FXML
    void pressed(MouseEvent event) {
    	searchbutton.setStyle("fx-background-color: #FFF ; -fx-background-radius: 5 ; -fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 3 , 0 )");
    	
    }
    
    @FXML
    void entered(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1.1);
    	((Node)event.getSource()).setScaleY(1.1);
    }
    @FXML
    void exited(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1);
    	((Node)event.getSource()).setScaleY(1);
    }
    
    private String formatAnswer(int answerIndex, String answer, int correctAnswerIndex) {
        // Check if the answer is correct and apply styling
    	  return "   " + answer;
    }
}
   