package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.parser.ParseException;

import java.util.PriorityQueue;
import java.util.ResourceBundle;

import View.Alerts;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import Model.SysData;
import Model.Difficulty;
import Model.Game;
import Model.Player;
import Model.Sort;

public class historyController implements Initializable{

	Methods methods = new Methods();
	
    private HashMap<Player, Integer> playersWinningGames;//the value represent the number of games the player wins  
    @FXML
    private TableView<Game> History;

    @FXML
    private TableColumn<Game, Integer> gameID;

    @FXML
    private TableColumn<Game, Difficulty> difficulty;

    @FXML
    private TableColumn<Game, Player> winner;

    @FXML
    private TableColumn<Game, String> duration;
    
    @FXML
    private TableColumn<Game, LocalDate> date;
    
    @FXML
    private Text player1;

	@FXML
    private Text player2;

    @FXML
    private Text player3;
    
    @FXML
    private Button homeButton;

    @FXML
    private Button exitButton;
    
    @FXML
    private CheckBox winnerCheck;

    @FXML
    private CheckBox durationCheck;
    
    @FXML
    private CheckBox DateCheck;

    @FXML
    private CheckBox DifficultyCheck;
    
    @FXML
    private Button topButton;
    
    @FXML
    private ComboBox<String> orderBox;

    
    private ObservableList<Game> Gamesdata;
    
    ArrayList<Game> sorted = new ArrayList<>(); // Arraylist to store the sorted Games
    private boolean isSorted = false;
    ArrayList<Game> sortedByDuration = new ArrayList<>(); // Arraylist to store the sorted Games by duration
    ArrayList<Game> sortedByDate = new ArrayList<>();// Arraylist to store the sorted Games by date
    ArrayList<Game> sortedByDifficulty = new ArrayList<>();// Arraylist to store the sorted Games by Difficulty
    HashSet<Game> originalOrder = new HashSet<>(SysData.getInstance().getGames()); // Save the original order to revert back to after sorting
	List<Entry<Player, Integer>> topPlayersList = calculateTopThree();

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			SysData.getInstance().ReadFromJsonGames();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderBox.getItems().addAll("Difficulty", "Date", "Duration");
		
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

		fillHistoryTable();	
		History.refresh();
	}
    
    private List<Entry<Player, Integer>> calculateTopThree() {//bring games and save for each players the number of winning games then sort them
        HashMap<Player, Integer> playersWinningGames = new HashMap<>();
        //save
    	List<Game> games = new ArrayList<>(SysData.getInstance().getGames());
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
    @FXML
    void OrderDuration(ActionEvent event) {
//    	if (!isSorted) {
//            Sort sort = new Game();
//            for (Object obj : sort.getSorted("Duration"))
//                 sortedByDuration.add((Game) obj);
//			System.out.println("by Duration:"+sortedByDuration);
//            ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDuration);
//            sort(dataGame);
//    	}
//    	else {
//    		ObservableList<Game> temp = FXCollections.observableArrayList(originalOrder);
//    	     History.setItems(temp);
//    	     
//    	     isSorted = false;
//    	}
    }
//   
    @FXML
    void OrderDate(ActionEvent event) {
//    	if (!isSorted) {
//            Sort sort = new Game();
//			for (Object obj : sort.getSorted("Date"))
//                 sortedByDate.add((Game) obj);
//			System.out.println("by date:"+sortedByDate);
//            ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDate);
//            sort(dataGame); 	
//    	}
//    	else {
//    		ObservableList<Game> temp = FXCollections.observableArrayList(originalOrder);
//    	     History.setItems(temp);
//    	     
//    	     isSorted = false;
//    	}
    }
//
    @FXML
    void OrderDifficulty(ActionEvent event) {
//    	 if (!isSorted) {
//            Sort sort = new Game();
//			for (Object obj : sort.getSorted("Difficulty"))
//                 sortedByDifficulty.add((Game) obj);
//			System.out.println("by diff:"+sortedByDifficulty);
//            ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDifficulty);
//            sort(dataGame);   
//    	 }
//            else {
//                // Restore the original order
//                ObservableList<Game> temp = FXCollections.observableArrayList(originalOrder);
//                History.setItems(temp);
//                
//                isSorted = false;
//                }
    }
//    
    void sort(ObservableList<Game> dataGame) {
		 if (!isSorted) {
		 ObservableList<Game> temp = FXCollections.observableArrayList(dataGame);
	     History.setItems(temp);
	     isSorted = true;
	     }
    }

    @FXML
    void OrderByBox(ActionEvent event) {
        // Get the selected sorting option from the ComboBox
        String selectedSortOption = orderBox.getValue();

        // Apply sorting based on the selected option
        switch (selectedSortOption) {
            case "Difficulty":
	           	 if (!isSorted) {
	           		 Sort sort = new Game();
	  				 for (Object obj : sort.getSorted("Difficulty"))
	  					sortedByDifficulty.add((Game) obj);
		  			 System.out.println("by diff:"+sortedByDifficulty);
		             ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDifficulty);
		             sort(dataGame);   
	           	 }
	              else {
	                  // Restore the original order
	                  ObservableList<Game> temp = FXCollections.observableArrayList(originalOrder);
	                  History.setItems(temp);
	                  isSorted = false;
	              }
                break;
            case "Date":
	            if (!isSorted) {
	            	Sort sort = new Game();
		  			for (Object obj : sort.getSorted("Date"))
		                   sortedByDate.add((Game) obj);
		  			  System.out.println("by date:"+sortedByDate);
		              ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDate);
		              sort(dataGame); 	
	            }
		      	else {
		      		ObservableList<Game> temp = FXCollections.observableArrayList(originalOrder);
		      	     History.setItems(temp);
		      	     
		      	     isSorted = false;
		      	}
                break;
            case "Duration":
            	
                if (!isSorted) {
                    Sort sort = new Game();
                    for (Object obj : sort.getSorted("Duration")) {
                        sortedByDuration.add((Game) obj);
                    }
                    System.out.println("by Duration:" + sortedByDuration);
                    ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDuration);
                    sort(dataGame);
                    isSorted = true;
                } 
                else {
                    ObservableList<Game> temp = FXCollections.observableArrayList(originalOrder);
                    History.setItems(temp);
                    isSorted = false;
                }
        }
    }

    // Update your table with the sorted data
    private void updateTable(List<Game> sortedList) {
        Gamesdata.clear();
        Gamesdata.addAll(sortedList);
        History.refresh();
    }

    @FXML
    void ShowTopThree(ActionEvent event) {
    	methods.newScreen("topThree");
    }    
   
    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void home(ActionEvent event) {
    	methods.newScreen("Home");
    }

    public void fillHistoryTable() {
    	Gamesdata = FXCollections.observableArrayList(SysData.getInstance().getGames());
    	
    	// duration column
    	duration.setCellValueFactory(param -> {
    	    String gameDuration = param.getValue().getGameDuration();
    	    return new SimpleObjectProperty<>(gameDuration);
    	});
    	
		// Set cell value factory to display winner's name
        winner.setCellValueFactory(new Callback<CellDataFeatures<Game, Player>, ObservableValue<Player>>() {
            @Override
            public ObservableValue<Player> call(CellDataFeatures<Game, Player> param) {
                Player winner = param.getValue().getWinner();
                String winnerName = (winner != null) ? winner.getPlayerName() : "";
                return new SimpleObjectProperty(winnerName);
            }
        });
        // difficulty column
		difficulty.setCellValueFactory(new PropertyValueFactory<Game, Difficulty>("difficulty"));
		// date column
        date.setCellValueFactory(param -> {
		    LocalDate gameDate = param.getValue().getDate();
		    return new SimpleObjectProperty<>(gameDate);
		});
        // game counter column
		gameID.setCellValueFactory(cellData -> {
            int rowIndex = History.getItems().indexOf(cellData.getValue()) + 1;
            return javafx.beans.binding.Bindings.createObjectBinding(() -> rowIndex);
        });
		
		History.setItems(Gamesdata);
    }
	
	 @FXML
	 void entered(MouseEvent event) {
		 methods.entered(event);
	 }
	 
	@FXML
	 void exited(MouseEvent event) {
		methods.exited(event);
	 }	
}
