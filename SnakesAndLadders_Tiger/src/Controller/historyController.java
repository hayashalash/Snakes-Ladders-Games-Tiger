package Controller;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import View.Alerts;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.SysData;
import Model.Difficulty;
import Model.GameHistory;
import Model.Player;
import Model.Question;

public class historyController implements Initializable{

    private HashMap<Player, Integer> playersWinningGames;//the value represent the number of games the player wins  
    @FXML
    private TableView<GameHistory> History;

    @FXML
    private TableColumn<GameHistory, Integer> gameID;

    @FXML
    private TableColumn<GameHistory, Difficulty> difficulty;

    @FXML
    private TableColumn<GameHistory, Player> winner;

    @FXML
    private TableColumn<GameHistory, String> duration;
    
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
    	   List<GameHistory> orderByWinner = new ArrayList<>(SysData.getInstance().getGames());

           // Sort the games based on the winner's name
           Collections.sort(orderByWinner, new Comparator<GameHistory>() {
               @Override
               public int compare(GameHistory w1, GameHistory w2) {
                   String winnerName1 = w1.getWinner().getPlayerName();
                   String winnerName2 = w2.getWinner().getPlayerName();
                   return winnerName1.compareTo(winnerName2);
               }
           });

    	// Retrieve the top three winners
    	List<GameHistory> topThreeWinners = orderByWinner.subList(0, Math.min(3, orderByWinner.size()));

    	// Display the top three winners in a TableView
    	ObservableList<GameHistory> dataQues = FXCollections.observableArrayList(topThreeWinners);
    	gameID.setCellValueFactory(new PropertyValueFactory<>("GameID"));
    	duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    	difficulty.setCellValueFactory(new PropertyValueFactory<>("type"));
    	winner.setCellValueFactory(new PropertyValueFactory<>("winner"));

    	ObservableList<GameHistory> temp = FXCollections.observableArrayList(dataQues);
    	History.setItems(temp);
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
     ObservableList<GameHistory> Gamesdata = FXCollections.observableArrayList(SysData.getInstance().getGames());
     
     gameID.setCellValueFactory(new PropertyValueFactory<GameHistory, Integer>("GameID"));
     duration.setCellValueFactory(new PropertyValueFactory<GameHistory, String>("gameDuration"));
     winner.setCellValueFactory(new PropertyValueFactory<GameHistory, Player>("winner"));
     difficulty.setCellValueFactory(new PropertyValueFactory<GameHistory, Difficulty>("difficulty"));

     History.setItems(Gamesdata);
     
    }
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillHistoryTable();	
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
