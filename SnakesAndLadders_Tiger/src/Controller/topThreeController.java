package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import Model.GameHistory;
import Model.Player;
import Model.SysData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
public class  topThreeController implements Initializable{
   // private HashMap<Player, Integer> playersWinningGames;//the value represent the number of games the player wins i think we should save it in sysdata  

	    @FXML
        private Text player1;

		@FXML
	    private Text player2;

	    @FXML
	    private Text player3;

        @FXML
	    private Button exit;
	    
	    @FXML
	    void exit(ActionEvent event) {
	    	try {
				Parent root = FXMLLoader.load(getClass().getResource("/View/GameHistory.fxml"));
				Scene scene = new Scene(root);
				Main.mainWindow.setScene(scene);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}  	
	    }

	    
	    private List<Entry<Player, Integer>> calculateTopThree() {//bring games and save for each players the number of winning games then sort them
	        HashMap<Player, Integer> playersWinningGames = new HashMap<>();
            //save
	    	List<GameHistory> games = new ArrayList<>(SysData.getInstance().getGames());
	        for (GameHistory game : games) {
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
		        player2.setText("haya");
		        player3.setText("bisan");


		    }
		}			
		}


	

	