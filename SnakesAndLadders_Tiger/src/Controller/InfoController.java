package Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import Model.SysData;
import View.difficultyStage;


public class InfoController implements Initializable{
	   //buttons
    @FXML
    private Button closeButton; 
    @FXML
    private Button homeButton;
    @FXML
    private Button easyButton; 
    @FXML
    private Button normalButton;
    @FXML
    private Button hardButton;

	    public void chooseGame() {
	        // Get players name from the view 
	        try {
				String diff = View.difficultyStage.getSelectedDifficulty();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Create a new Player object with the provided name
	       // Player player = new Player(playerName);

	        // Initialize the game with the player's information
	       // initializeGame(player);
	    }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
}
