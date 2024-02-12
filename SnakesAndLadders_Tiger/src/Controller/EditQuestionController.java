package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Model.Difficulty;
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
import javafx.scene.input.MouseEvent;
import Model.Question;
import Model.SysData;

public class EditQuestionController implements Initializable {
    public static Question edited;


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
    private Button edit;

    @FXML
    private Button previousButton;

    @FXML
    private Button exitButton;

    
    @FXML
    void editQuestion(ActionEvent event) throws IOException, ParseException {
    	if(questionText.getText().length() == 0 || ans1Text.getText().length() == 0 || 
    			ans2Text.getText().length() == 0 || ans3Text.getText().length() == 0 ||
    			ans4Text.getText().length() == 0 ||correctAnswer.getSelectionModel().getSelectedIndex() == -1||
    			difficulty.getSelectionModel().getSelectedIndex() == -1){
    			Alerts.message("Error","Please fill all the fields");
    	}
    	if (Alerts.edit() == 1) {
	    	String quesEdit= questionText.getText();
	    	String ans1Edit= ans1Text.getText();
	    	String ans2Edit= ans2Text.getText();
	    	String ans3Edit= ans3Text.getText();
	    	String ans4Edit= ans4Text.getText();
	    	Difficulty di = difficulty.getSelectionModel().getSelectedItem();
	    	Integer corr = correctAnswer.getSelectionModel().getSelectedIndex()+1;
	    			
	    	Question ques = new Question(ans1Edit,ans2Edit,ans3Edit,ans4Edit,quesEdit,di,corr); 	
//	    	SysData.getInstance().deleteFromJson(edited);
//	    	SysData.getInstance().writeToJson(ques);
	    	SysData.getInstance().updateInJson(edited, ques);
	    	return;

    	}
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
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		fill();
		
	}
	
	public void fill() {
		questionText.setText(edited.getQuestion());
		ans1Text.setText(edited.getAnswer1());
		ans2Text.setText(edited.getAnswer2());
		ans3Text.setText(edited.getAnswer3());
		ans4Text.setText(edited.getAnswer4());

		correctAnswer.getItems().addAll(1,2,3,4);
		correctAnswer.getSelectionModel().select(edited.getCorrectAnswer()-1);

		for (Difficulty d : Difficulty.values()) {
			difficulty.getItems().add(d);
		}
		
		difficulty.getSelectionModel().select(edited.getDifficulty());
		//String correct = Integer.toString(edited.getCorrectAnswer());
//		correctAnswer.getSelectionModel().select(correct);
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
