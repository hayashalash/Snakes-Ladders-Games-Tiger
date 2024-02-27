package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import Model.SysData;
import Model.Difficulty;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.Sort;

public class historyController implements Initializable{

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

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			SysData.getInstance().ReadFromJsonGames();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		fillHistoryTable();	
		History.refresh();
	}
    
    @FXML
    void OrderDuration(ActionEvent event) {
    	if (!isSorted) {
            Sort sort = new Game();
            for (Object obj : sort.getSorted("Duration"))
                 sortedByDuration.add((Game) obj);
			System.out.println("by Duration:"+sortedByDuration);
            ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDuration);
            sort(dataGame);
    	}
    	else {
    		ObservableList<Game> temp = FXCollections.observableArrayList(originalOrder);
    	     History.setItems(temp);
    	     
    	     isSorted = false;
    	}
    }
   
    @FXML
    void OrderDate(ActionEvent event) {
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
    }

    @FXML
    void OrderDifficulty(ActionEvent event) {
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
    }
    
    void sort(ObservableList<Game> dataGame) {
		 if (!isSorted) {
		 ObservableList<Game> temp = FXCollections.observableArrayList(dataGame);
	     History.setItems(temp);
	     isSorted = true;
	     }
    }

    @FXML
    void OrderWinner(ActionEvent event) {
		List<Game> orderByWinner = new ArrayList<>(SysData.getInstance().getGames());
		
		// Sort the games based on the winner's name
		Collections.sort(orderByWinner, new Comparator<Game>() {
			@Override
			public int compare(Game w1, Game w2) {
				String winnerName1 = w1.getWinner().getPlayerName();
				String winnerName2 = w2.getWinner().getPlayerName();
				return winnerName1.compareTo(winnerName2);
			}
		});

    	// Retrieve the top three winners
    	List<Game> topThreeWinners = orderByWinner.subList(0, Math.min(3, orderByWinner.size()));

    	// Display the top three winners in a TableView
    	ObservableList<Game> dataQues = FXCollections.observableArrayList(topThreeWinners);
    	gameID.setCellValueFactory(new PropertyValueFactory<>("GameID"));
    	duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    	difficulty.setCellValueFactory(new PropertyValueFactory<>("type"));
    	winner.setCellValueFactory(new PropertyValueFactory<>("winner"));

    	ObservableList<Game> temp = FXCollections.observableArrayList(dataQues);
    	History.setItems(temp);
    }
    
    @FXML
    void OrderByBox(ActionEvent event) {

    }

    @FXML
    void ShowTopThree(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/topThree.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
			Main.mainWindow.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }    
   
    @FXML
    void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void home(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
			Main.mainWindow.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
    }

    public void fillHistoryTable() {
    	Gamesdata = FXCollections.observableArrayList(SysData.getInstance().getGames());
    	
    	// duration column
    	duration.setCellValueFactory(param -> {
    	    String gameDuration = param.getValue().getGameDuration();
    	    return new SimpleObjectProperty(gameDuration);
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
		 ((Node)event.getSource()).setScaleX(1.1);
		 ((Node)event.getSource()).setScaleY(1.1);
	 }
	 
	@FXML
	 void exited(MouseEvent event) {
	    ((Node)event.getSource()).setScaleX(1);
	    ((Node)event.getSource()).setScaleY(1);
	 }	
}
