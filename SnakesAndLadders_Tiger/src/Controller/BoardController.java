package Controller;

import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Player;
import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public abstract class BoardController {	// Design Pattern #2 - Template Pattern

		// Common UI elements
	    @FXML
	    protected Button exitButton;

	    @FXML
	    protected Button home;

	    @FXML
	    protected Button info;

	    @FXML
	    protected Button update;

	    Methods methods = new Methods();

		@FXML
	    private GridPane grid;

		//template method, final so subclasses can't override

	    protected abstract void entered();

		public abstract void initializeMap();
		public abstract void initialize(URL location, ResourceBundle resources) ;
	    @FXML
	    public abstract void handleDiceClick(ActionEvent event) throws InterruptedException;
	    public  abstract void onFinished(Player currentPlayer, int lastResult) ;
	    public abstract double viewResultDice(Player currentPlayer,int diceResult);
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
