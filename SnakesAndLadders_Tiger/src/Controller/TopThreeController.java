package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import View.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import Model.Game;
import Model.Player;
import Model.SysData;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;


public class TopThreeController implements Initializable {

	Methods methods = new Methods();
	
    @FXML
	private Button exitButton;
	 
    @FXML
    private Text player1;

    @FXML
    private Text player2;

    @FXML
    private Text player3;
    
    @FXML
    private Button previousButton;
    
    @FXML
    private Button musicIcon;
    
    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.turnOffOn(event, musicIcon);
    }


    @FXML
    void previous(ActionEvent event) {
    	methods.newScreen("GameHistory");
    }

    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
    
    @FXML
	 void entered(MouseEvent event) {
		 methods.entered(event);
	 }
	 
	@FXML
	 void exited(MouseEvent event) {
		methods.exited(event);
	 }	
	 
    
    private List<Entry<Player, Integer>> calculateTopThree() {//bring games and save for each players the number of winning games then sort them
        HashMap<Player, Integer> playersWinningGames = new HashMap<>();
        //save
    	HashSet<Game> games = new HashSet<>(SysData.getInstance().getGames());
        for (Game game : games) {
            Player winner = game.getWinner();
            playersWinningGames.put(winner, playersWinningGames.getOrDefault(winner, 0) + 1);
        }
        //sort
        PriorityQueue<HashMap.Entry<Player, Integer>> topPlayersQueue =
                new PriorityQueue<>(Comparator.comparingInt(HashMap.Entry::getValue));

        // Populate the priority queue with initial values
        for (HashMap.Entry<Player, Integer> entry : playersWinningGames.entrySet()) {
            topPlayersQueue.offer(entry);
            if (topPlayersQueue.size() > 3) {
                topPlayersQueue.poll(); // Remove the player with the lowest winning count
            }
        }

        // Retrieve the top 3 players from the priority queue
        List<HashMap.Entry<Player, Integer>> topPlayersList = new ArrayList<>(topPlayersQueue);
        Collections.reverse(topPlayersList); // Reverse to display in descending order

        return topPlayersList;
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Entry<Player, Integer>> topPlayersList = calculateTopThree();

	    // Check if there are at least 3 players 
	    if (topPlayersList.size() >= 3) {
	        // Display the names of the top 3 players
	        player1.setText(topPlayersList.get(0).getKey().getPlayerName());
	        player2.setText(topPlayersList.get(1).getKey().getPlayerName());
	        player3.setText(topPlayersList.get(2).getKey().getPlayerName());
	    } else {
	        //display default values just for test
	        player1.setText("Ruba");
	        player2.setText("Haya");
	        player3.setText("Bisan");


	    }
	}			
	}



