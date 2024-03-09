package Controller;

import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import Model.SysData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Model.Admin;
import Model.Difficulty;
import Model.Question;
import Model.Sort;

public class ManageQuestionsController implements Initializable {
	
	Methods methods = new Methods();
	private static final String ADMIN = "/img/screens/pass2.png";
	private static final String EXIT_ICON = "/img/icons/X.png";
	
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
    private Button retrive;

    @FXML
    private TextField searchField;
    
    @FXML
    private Button searchbutton;
    
    @FXML
    private Button changePassBtn;
    
    private ObservableList<Question> dataQues;
    private ObservableList<Question> dataQues2;
    List<Question> originalOrder = new ArrayList<>(SysData.getInstance().getQuestions()); // Save the original order to revert back to after sorting
    ArrayList<Question> sorted = new ArrayList<>(); // Arraylist to store the sorted questions
    private boolean isSorted = false;
    
    @FXML
    private Button musicIcon;
    
    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.turnOffOn(event, musicIcon);
    }

    @Override
	public void initialize(URL location, ResourceBundle resources) {

    	Tooltip a = new Tooltip("Add Question");
        Tooltip.install(add, a);
        Tooltip ed = new Tooltip("Edit");
        Tooltip.install(edit, ed);
        Tooltip d = new Tooltip("Delete");
        Tooltip.install(delete, d);
        Tooltip r = new Tooltip("Restore Question");
        Tooltip.install(retrive, r);
        Tooltip cp = new Tooltip("Change Password");
        Tooltip.install(changePassBtn, cp);
    	if (Main.note.isPlaying()) {
    		musicIcon.setOpacity(1.0);
    	}
    	else {
    		musicIcon.setOpacity(0.5);
    	}
        fill();
        questionTable.refresh();
	}
    
    @FXML
    void OrderDifficulty(ActionEvent event) {
        if (!isSorted) {
            Sort sort = new Question();
            for (Object obj : sort.getSorted("difficulty"))
            	sorted.add((Question) obj);
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
    	methods.newScreen("AddQuestion");
    }

    @FXML
    void deleteQuestion(ActionEvent event) throws IOException, ParseException {
    	if(questionTable.getSelectionModel().getSelectedIndex() == -1) {
    		Alerts.warning("Please select a question to delete.");
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
		Alerts.confirmation("Question has been deleted succesfully!");
    }
    
    
    @FXML
    void editQuestion(ActionEvent event) {
 
    		if(questionTable.getSelectionModel().getSelectedIndex() == -1) {
    			Alerts.warning("Please choose question that you want to edit.");
    			return;
    		}
    		//the question that we want to update is selected
    		EditQuestionController.edited = questionTable.getSelectionModel().getSelectedItem();
    		methods.newScreen("EditQuestion");
    	}
    

    @FXML
    void retriveQuestion(ActionEvent event) {
    	methods.newScreen("RestoreQuestion");
    }

    @FXML
    void exitGame(ActionEvent event) {
		if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void returnHome(ActionEvent event) {
    	methods.newScreen("Home");
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
		                        @SuppressWarnings("unused")
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
  	//	System.out.println(arr);

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
    void changePassword(ActionEvent event) {
    	changePasswordDialog();
    }
    
    private void changePasswordDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Change Admin Password");
        dialog.initStyle(StageStyle.UNDECORATED);
        ImageView imageView = new ImageView(new Image(ADMIN));
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        dialog.getDialogPane().setPrefWidth(300);
        dialog.getDialogPane().setPrefHeight(300);

        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.setPrefHeight(40);
        currentPasswordField.setMaxWidth(200);
        currentPasswordField.setStyle("-fx-border-radius: 10;");
        currentPasswordField.setPromptText("Enter Current Password");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPrefHeight(40);
        newPasswordField.setMaxWidth(200);
        newPasswordField.setStyle("-fx-border-radius: 10;");
        newPasswordField.setPromptText("Enter New Password");

        PasswordField confirmNewPasswordField = new PasswordField();
        confirmNewPasswordField.setPrefHeight(40);
        confirmNewPasswordField.setMaxWidth(200);
        confirmNewPasswordField.setStyle("-fx-border-radius: 10;");
        confirmNewPasswordField.setPromptText("Confirm New Password");
        
        Label errorLabel = new Label("Current Password is incorrect!");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setOpacity(0);
        errorLabel.setPadding(new Insets(3,0,3,0)); // top right bottom left
        
        Label errorLabel2 = new Label();
        errorLabel2.setOpacity(0);
        errorLabel2.setPadding(new Insets(3,0,3,0)); // top right bottom left

        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setStyle("-fx-border-radius: 20; -fx-background-color: #dfbc95; " + methods.getButtonStyle());
        changePasswordButton.setOnMouseEntered(e -> entered(e));
        changePasswordButton.setOnMouseExited(e -> exited(e));
        changePasswordButton.setOnAction(e -> {
        	//I have to check if the user fill all the fields
        	if(currentPasswordField.getText().length() == 0 || newPasswordField.getText().length() == 0 ||
        			confirmNewPasswordField.getText().length() == 0) {
    		     //if there's one field empty the user gets a note : add is not applied!
        		currentPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
        		confirmNewPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                newPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                errorLabel2.setTextFill(Color.RED);
                errorLabel2.setText("Please fill all the fields!");	
    		}
        	else if (Admin.getInstance().checkPassword(currentPasswordField.getText()) && currentPasswordField.getText().length() != 0) {
        		if(!(newPasswordField.getText().equals(currentPasswordField.getText()))) {
            		if (newPasswordField.getText().equals(confirmNewPasswordField.getText())) {               
                    	
                    	Admin.getInstance().setPassword(newPasswordField.getText());
                    	Admin.saveAdminToFile("admin.ser");
                    	Alerts.confirmation("Password was changed successfully!");
                    	System.out.println("Password changed successfully!");
                        dialog.close();
                    } else {
                        System.out.println("Passwords do not match. Please try again.");
                		confirmNewPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                        newPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                        errorLabel2.setTextFill(Color.RED);
                        errorLabel2.setText("Passwords do not match!");
                        // Show a label with red text underneath the text field to notify the user of a wrong password
                		errorLabel2.setOpacity(1);
                    }
        		}
        		else {
                    System.out.println("Passwords are identical!");
            		currentPasswordField.setStyle("-fx-text-box-border: yellow ; -fx-focus-color: yellow ;");
            		confirmNewPasswordField.setStyle("-fx-text-box-border: yellow ; -fx-focus-color: yellow ;");
                    newPasswordField.setStyle("-fx-text-box-border: yellow ; -fx-focus-color: yellow ;");
                    errorLabel2.setTextFill(Color.YELLOW);
                    errorLabel2.setText("Passwords are identical!");
                    // Show a label with yellow text underneath the text field to notify the user of identical passwords
            		errorLabel2.setOpacity(1);

        		}

        	}
        	else {
        		System.out.println("The current password is incorrect.");
        		currentPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                // Show a label with red text underneath the text field to notify the user of a wrong password
        		errorLabel.setOpacity(1);
        	}

            
        });
        
        changePasswordButton.setDefaultButton(true);
        currentPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            // If the user starts typing, remove the error label if it exists
            if (errorLabel.getOpacity() != 0 || errorLabel2.getOpacity() != 0  ) {
                errorLabel.setOpacity(0);
                errorLabel2.setOpacity(0);
                // Reset the style of the text field to its default state
                currentPasswordField.setStyle("");
                newPasswordField.setStyle("");
                confirmNewPasswordField.setStyle("");
            }
        });
        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            // If the user starts typing, remove the error label if it exists
            if (errorLabel2.getOpacity() != 0) {
                errorLabel2.setOpacity(0);
                // Reset the style of the text field to its default state
                currentPasswordField.setStyle("");
                newPasswordField.setStyle("");
                confirmNewPasswordField.setStyle("");
            }
        });
        confirmNewPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            // If the user starts typing, remove the error label if it exists
            if (errorLabel2.getOpacity() != 0) {
                errorLabel2.setOpacity(0);
                // Reset the style of the text field to its default state
                currentPasswordField.setStyle("");
                newPasswordField.setStyle("");
                confirmNewPasswordField.setStyle("");
            }
        });
        

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
                currentPasswordField,
                errorLabel,
                newPasswordField,
                confirmNewPasswordField,
                errorLabel2,
                changePasswordButton
        );
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(2); // Set spacing between the text field and button
        // Add the background image and the elements to a layout
        StackPane content = new StackPane();
        content.getChildren().addAll(imageView, vbox);
        
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);
        ImageView exitIcon = new ImageView(new Image(EXIT_ICON));
        exitIcon.setFitWidth(20);
        exitIcon.setFitHeight(20);
        cancelButton.setGraphic(exitIcon);
        cancelButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 0;");
        cancelButton.setCursor(Cursor.HAND); // Set cursor to hand
        cancelButton.setOnMouseEntered(e -> entered(e));
        cancelButton.setOnMouseExited(e -> exited(e));
        StackPane.setAlignment(cancelButton, Pos.TOP_RIGHT);
        StackPane.setMargin(cancelButton, new Insets(10));


        content.setPadding(new Insets(0)); // Set padding of the StackPane to zero
        // Set the layout as the content of the dialog
        dialog.getDialogPane().setContent(content);
        dialog.showAndWait();
    }

    @FXML
    void entered(MouseEvent event){
    	methods.entered(event);
    }
    @FXML
    void exited(MouseEvent event){
    	methods.exited(event);
    }
    
    private String formatAnswer(int answerIndex, String answer, int correctAnswerIndex) {
        // Check if the answer is correct and apply styling
    	  return "   " + answer;
    }

}
   