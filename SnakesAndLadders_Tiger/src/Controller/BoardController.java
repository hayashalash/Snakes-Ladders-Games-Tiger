package Controller;

import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;


import Model.Game;
import Model.Player;
import View.Alerts;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public abstract class BoardController {	// Design Pattern #2 - Template Pattern

	
	

		 // Common UI elements
	    @FXML
	    protected ImageView surpriseValue;

	    @FXML
	    protected ImageView surprise;

	    @FXML
	    protected Button exitButton;

	    @FXML
	    protected Button home;

	    @FXML
	    protected Button info;

	    @FXML
	    protected Button update;

	    @FXML
	    protected Label time;

	    @FXML
	    protected Timeline timer;
	    

	    @FXML
	    protected Duration gameDuration = Duration.ZERO;
	    Methods methods = new Methods();
		public static Game game;
		private GameController gameController;
		private static final String DEFAULT_DICE_IMAGE_PATH = "/img/icons/dice.png";
		ArrayList<Player> playersOutsideBoard = new ArrayList<>();

		@FXML
	    private GridPane grid;

	    @FXML
	    private HBox playersStart;

	    @FXML
	    private HBox player1;

	    @FXML
	    private HBox player2;

	    @FXML
	    private HBox player3;

	    @FXML
	    private HBox player4;  
	    
	    @FXML
	    private Button diceButton;
	     	    
	    
	    
	  //template method, final so subclasses can't override
		

	    protected abstract void entered();

		public abstract void initializeMap();
		public abstract void initialize(URL location, ResourceBundle resources) ;
	    @FXML
	    public abstract void handleDiceClick(ActionEvent event) throws InterruptedException;
	    public  abstract void onFinished(Player currentPlayer, int lastResult) ;
	    public abstract void viewResultDice(Player currentPlayer,int diceResult);
	    public abstract void updateDiceImage(String imagePath);//update the dice image 
	    public  abstract Player getNextPlayerToMove();
	    @FXML
		public abstract void updateBoard(ActionEvent event)throws IOException ;

	    @FXML
		void returnHome(ActionEvent event) {
			if (Alerts.retunHome() == 1)
				methods.newScreen("Home");
	    }
		
		@FXML
	    void exit(ActionEvent event) {
			if (Alerts.exit()==1)
				Main.mainWindow.close();
	    }
		
		@FXML
	    void entered(MouseEvent event){
			methods.entered(event);
	    }
		
	    @FXML
	    void exited(MouseEvent event){
	    	methods.exited(event);
	    }
	    
		// Template method
	    public final void boardControll() throws InterruptedException, IOException{
	       
		    initializeMap();
		    initialize(null, null) ;
			handleDiceClick(null);
			onFinished(null,0) ;
			viewResultDice(null,0);
			updateDiceImage(null);//update the dice image 
		    getNextPlayerToMove();
            updateBoard(null);
			returnHome(null) ;
		    exit(null);
			entered(null);
			exited(null);
		    
		    
		}
	  
	    
	      

	}


