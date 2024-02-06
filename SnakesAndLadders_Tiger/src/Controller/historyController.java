package Controller;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.SysData;
import Model.Difficulty;
import Model.Game;
import Model.Player;
import Model.Question;

public class historyController implements Initializable{


    @FXML
    private TableView<Game> History;

    @FXML
    private TableColumn<Game, Integer> gameID;

    @FXML
    private TableColumn<Game, Difficulty> difficulty;

    @FXML
    private TableColumn<Game, Player> winner;

    @FXML
    private TableColumn<Game, Double> duration;
    
    @FXML
    private Button homeButton;

    @FXML
    private Button exitButton;
    
    @FXML
    private CheckBox winnerCheck;

    @FXML
    private CheckBox durationCheck;

    @FXML
    private Button topButton;

    @FXML
    void OrderDuration(ActionEvent event) {

    }

    @FXML
    void OrderWinner(ActionEvent event) {
    	ArrayList<Game> orderByWinner = new ArrayList<>(SysData.getInstance().getGames());

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
    void ShowTopThree(ActionEvent event) {

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
     ObservableList<Game> Gamesdata = FXCollections.observableArrayList(SysData.getInstance().getGames());
     
     gameID.setCellValueFactory(new PropertyValueFactory<Game, Integer>("GameID"));
     duration.setCellValueFactory(new PropertyValueFactory<Game, Double>("gameDuration"));
     winner.setCellValueFactory(new PropertyValueFactory<Game, Player>("winner"));
     difficulty.setCellValueFactory(new PropertyValueFactory<Game, Difficulty>("difficulty"));

     History.setItems(Gamesdata);
     
    }
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillHistoryTable();	}

}
