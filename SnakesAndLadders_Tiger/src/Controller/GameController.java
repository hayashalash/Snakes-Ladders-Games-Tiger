package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;

import Model.Dice;
import Model.Difficulty;
import Model.Question;
import Model.SysData;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.control.ButtonType;
import java.util.Optional;

 public class GameController implements Initializable {
	 @FXML
	    private HBox player1;

	    @FXML
	    private HBox player2;

	    @FXML
	    private HBox player3;

	    @FXML
	    private HBox player4;
	
	 void showQuestionPopup(Difficulty difficulty) {
	    Dialog<ButtonType> dialog = new Dialog<>();
	    dialog.setTitle("Question");
        Question q= returnQuestion(difficulty);//call for the random question according to the  difficulty
	    // Create  elements for the question and answer:
	    VBox vbox = new VBox();
	    Label questionLabel = new Label(q.getQuestion());
	    ToggleGroup answerGroup = new ToggleGroup();
	    RadioButton answer1 = new RadioButton(q.getAnswer1());
	    RadioButton answer2 = new RadioButton(q.getAnswer2());
	    RadioButton answer3 = new RadioButton(q.getAnswer3());
	    RadioButton answer4 = new RadioButton(q.getAnswer4());
	    TextField resultTextField = new TextField();
	    resultTextField.setEditable(false);
	    vbox.getChildren().addAll(questionLabel, answer1, answer2, answer3, answer4,resultTextField);
	    dialog.getDialogPane().setContent(vbox);	 // Set the content of the dialog
	    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
	    Optional<ButtonType> result = dialog.showAndWait(); // Show the dialog and wait for a button click

	    if (result.isPresent() && result.get() == ButtonType.OK) {  // Handle  button click
	        RadioButton selectedAnswer = (RadioButton) answerGroup.getSelectedToggle();	 // Check the selected answer
	        if (selectedAnswer != null) {
	            int selectedAnswerNumber = (int) selectedAnswer.getUserData();//get the number of seelcted answer
	            int correctAnswerNumber=q.getCorrectAnswer();
	            if(selectedAnswerNumber == correctAnswerNumber) {
	                resultTextField.setText("Your answer is right!");
	                selectedAnswer.setStyle("-fx-text-fill: green;");
	            }
	         else {
                resultTextField.setText("Wrong answer.");
                selectedAnswer.setStyle("-fx-text-fill: red;");
                switch (q.getCorrectAnswer()) {//mark the right answer in green
                case 1:
                    answer1.setStyle("-fx-text-fill: green;");
                    break;
                case 2:
                    answer2.setStyle("-fx-text-fill: green;");
                    break;
                case 3:
                    answer3.setStyle("-fx-text-fill: green;");
                    break;
                case 4:
                    answer4.setStyle("-fx-text-fill: green;");
                    break;
            }
	        }
	        }
	    }
	}

	
	public  Question returnQuestion(Difficulty difficulty) {//return a random question according to given difficulty
		HashSet<Question> questions= new HashSet();
		HashMap<Difficulty,ArrayList<Question>> questionMap = new HashMap();//define hashmap that store for each difficulty ,questions.
		 questions=SysData.getInstance().getQuestions();
		 for (Question question : questions) {
			 ArrayList<Question> q = new ArrayList();
			 Difficulty diff=question.getDifficulty();
			 q= questionMap.get(diff);
			 questionMap.put(difficulty, q);
		 }
		 Random random = new Random();
		 int r= random.nextInt(10);
		 while(questionMap.get(difficulty).get(r)==null) {
			 Random random2 = new Random();
			  r= random2.nextInt(10);
		 }
		 return questionMap.get(difficulty).get(r);
	
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

    
    

    private static void viewResultDise(int diceResult){//this for easy  difficulty only
    	if(diceResult<4) {
    		// function to move the player 
		}
    	if(diceResult==5) {//display easy question
    		
    	}
    	else { if(diceResult==6) {//display normal question 
    		
    	}
    	  else if(diceResult==7) {//display hard question 	
    	  }
    	}
	}

    public static void movePlayer(int n) {
    	
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
	
 
}
