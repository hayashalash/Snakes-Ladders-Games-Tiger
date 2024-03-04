package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.json.simple.parser.ParseException;
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
    private Button homeButton;

    @FXML
    private Button exitButton;
    
//    @FXML
//    private CheckBox winnerCheck;
//
//    @FXML
//    private CheckBox durationCheck;
//    
//    @FXML
//    private CheckBox DateCheck;
//
//    @FXML
//    private CheckBox DifficultyCheck;
      
    @FXML
    private ComboBox<String> orderBox;
    
    @FXML
    private Button resetbutton;

    @FXML
    private Button topthree;
    
    private ObservableList<Game> Gamesdata;
    
    @FXML
    private Button musicIcon;
    
    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.turnOffOn(event, musicIcon);
    }
    
    ArrayList<Game> sorted = new ArrayList<>(); // Arraylist to store the sorted Games
    ArrayList<Game> sortedByDuration = new ArrayList<>(); // Arraylist to store the sorted Games by duration
    ArrayList<Game> sortedByDate = new ArrayList<>();// Arraylist to store the sorted Games by date
    ArrayList<Game> sortedByDifficulty = new ArrayList<>();// Arraylist to store the sorted Games by Difficulty
    HashSet<Game> originalOrder = new HashSet<>(SysData.getInstance().getGames()); // Save the original order to revert back to after sorting
	//List<Entry<Player, Integer>> topPlayersList = calculateTopThree();

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	resetbutton.setStyle(methods.getButtonStyle());
    	topthree.setStyle(methods.getButtonStyle());
    	if (Main.note.isPlaying()) {
    		musicIcon.setOpacity(1.0);
    	}
    	else {
    		musicIcon.setOpacity(0.5);
    	}
    	
		try {
			SysData.getInstance().ReadFromJsonGames();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderBox.getItems().addAll("Difficulty", "Date", "Duration");
		
		fillHistoryTable();	
		History.refresh();
	}
    
    void sort(ObservableList<Game> dataGame) {
		 ObservableList<Game> temp = FXCollections.observableArrayList(dataGame);
	     History.setItems(temp);
    }

    @FXML
    void OrderByBox(ActionEvent event) {
        // Get the selected sorting option from the ComboBox
        String selectedSortOption = orderBox.getValue();
        // Apply sorting based on the selected option
        switch (selectedSortOption) {
            case "Difficulty":
	           	 if (sortedByDifficulty.isEmpty()) {
	           		 Sort sort = new Game();
	  				 for (Object obj : sort.getSorted("Difficulty"))
	  					sortedByDifficulty.add((Game) obj);
		  			 System.out.println("by diff:"+sortedByDifficulty);
		             ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDifficulty);
		             sort(dataGame);   
	           	 }
	              else {
	                  // show the already sorted list
	                  ObservableList<Game> temp = FXCollections.observableArrayList(sortedByDifficulty);
	                  History.setItems(temp);
	              }
                break;
            case "Date":
	            if (sortedByDate.isEmpty()) {
	            	Sort sort = new Game();
		  			for (Object obj : sort.getSorted("Date"))
		                   sortedByDate.add((Game) obj);
		  			  System.out.println("by date:"+sortedByDate);
		              ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDate);
		              sort(dataGame); 	
	            }
		      	else {
		      		ObservableList<Game> temp = FXCollections.observableArrayList(sortedByDate);
		      	     History.setItems(temp);
		      	}
                break;
            case "Duration":
            	
                if (sortedByDuration.isEmpty()) {
                    Sort sort = new Game();
                    for (Object obj : sort.getSorted("Duration")) {
                        sortedByDuration.add((Game) obj);
                    }
                    System.out.println("by Duration:" + sortedByDuration);
                    ObservableList<Game> dataGame = FXCollections.observableArrayList(sortedByDuration);
                    sort(dataGame);
                } 
                else {
                    ObservableList<Game> temp = FXCollections.observableArrayList(sortedByDuration);
                    History.setItems(temp);
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
    void reset(ActionEvent event) {
    	ObservableList<Game> dataGame = FXCollections.observableArrayList(originalOrder);
        sort(dataGame);
        orderBox.setValue(null);
    }
    
	 @FXML
	 void entered(MouseEvent event) {
		 methods.entered(event);
	 }
	 
	@FXML
	 void exited(MouseEvent event) {
		methods.exited(event);
	 }	
	
	@FXML
	   void showTopThree(ActionEvent event){
	    	methods.newScreen("TopThree");
	}
}
