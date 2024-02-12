package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Model.Difficulty;
import Model.Question;
import Model.SysData;
import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class AddQuestionController implements Initializable{

	  @FXML
	    private TextField questionText;

	    @FXML
	    private TextField ans1Text;

	    @FXML
	    private TextField ans2Text;

	    @FXML
	    private TextField ans3Text;

	    @FXML
	    private TextField ans4Text;

    @FXML
    private ComboBox<Difficulty> difficulty;

    @FXML
    private ComboBox<Integer> correctAnswer;

    @FXML
    private Button add;

    @FXML
    private Button clear;


    @FXML
    private Button previousButton;

    @FXML
    private Button exitButton;

    @FXML
    boolean addQuestion(ActionEvent event) throws IOException, ParseException {
    	if(questionText.getText().length() == 0 || ans1Text.getText().length() == 0 ||
    			ans2Text.getText().length() == 0 || ans3Text.getText().length() == 0 ||
    			ans4Text.getText().length() == 0) {
		     //if there's one field empty the user gets a note : add is not applied!
			Alerts.message("Error", "Please fill all the fileds");
			return false;
		}
		//check if the combo box selected (filled)
		if(difficulty.getSelectionModel().getSelectedIndex() == -1 || correctAnswer.getSelectionModel().getSelectedIndex() == -1) {
			Alerts.message("Error", "Please fill all the fileds");
			return false;
		}
		String ques = questionText.getText();
		String ans1 = ans1Text.getText();
		String ans2 = ans2Text.getText();
		String ans3 = ans3Text.getText();
		String ans4 = ans4Text.getText();
		Difficulty diff ;
		int correct = 0 ;
		diff = difficulty.getSelectionModel().getSelectedItem();
		correct = correctAnswer.getSelectionModel().getSelectedItem();
//		System.out.println(diff);
//		System.out.println(correct);

		//creating the new question object
		Question question = new Question(ans1,ans2, ans3,ans4,ques,diff,correct);
//		System.out.println(question);
		//write our question to json and add to question hashSet
		SysData.getInstance().writeToJson(question);
		//clear fields
		clearFields();
		Alerts.message("Added", "Question has been added succesfully!");
		
//		Question.idCounter++;
		return true;
    }

    @FXML
    void clearFields() {
    	difficulty.getSelectionModel().clearSelection();
		correctAnswer.getSelectionModel().clearSelection();
		questionText.clear();
		ans1Text.clear();
		ans2Text.clear();
		ans3Text.clear();
		ans4Text.clear();

    }
    

    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void previous(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/manageQuestion.fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // fill the combobox
		// TODO Auto-generated method stub

		difficulty.getItems().clear();
		for (Difficulty d : Difficulty.values()) {
			difficulty.getItems().add(d);
		}
		correctAnswer.getItems().addAll(1, 2, 3, 4);
		
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
	

}
