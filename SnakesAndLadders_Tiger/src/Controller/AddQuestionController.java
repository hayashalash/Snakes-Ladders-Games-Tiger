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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AddQuestionController  implements Initializable{

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
    void addQuestion(ActionEvent event) throws IOException, ParseException {
    	String question = null;
		String first = null;
		String second = null;
		String third = null;
		String fourth = null;
		Difficulty diff = null;
		int correct = 0;

		try {
						//reading the fields from the screen.
			question = questionText.getText();
			first = ans1Text.getText();
			second = ans2Text.getText();
			third = ans3Text.getText();
			fourth = ans4Text.getText();

			diff = difficulty.getSelectionModel().getSelectedItem();
			correct = correctAnswer.getSelectionModel().getSelectedItem();

		} catch (NullPointerException e) {
			// TODO: handle exception
			Alerts.warning("Plesae Fill All Fields!");
		}
	
		//creating the new question object
		Question newQuestion = new Question(first,second,third,fourth,question,diff,correct);
		//write our question to json and add to question hashSet
		SysData.getInstance().writeJson(newQuestion);
		//clear fields
		clearFields();
		Alerts.message("Added", "Question has been added succesfully!");

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // fill the combobox
		// TODO Auto-generated method stub

		difficulty.getItems().clear();
		for (Difficulty d : Difficulty.values()) {
			difficulty.getItems().add(d);
		}
		correctAnswer.getItems().addAll(1, 2, 3, 4);
		
	}

}
