package Controller;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.SysData;
import Model.Game;

public class historyController implements Initializable{
	@FXML
    private Button home;
    @FXML
    private Button exit;
    @FXML
    private TableView<Game> historyGamesTable;
    @FXML
    private TableColumn<Game, Integer> games;
    @FXML
    private TableColumn<Game, String> duration;
    @FXML
    private TableColumn<Game, String> WinnerPlayerName;
    @FXML
    private TableColumn<Game, Date> date;

    public void fillHistoryTable() {
     ObservableList<Game> Gamesdata = FXCollections.observableArrayList(SysData.getInstance().getGames());
     games.setCellValueFactory(new PropertyValueFactory<Game, Integer>("GameID"));
     duration.setCellValueFactory(new PropertyValueFactory<Game, String>("gameDuration"));
     WinnerPlayerName.setCellValueFactory(new PropertyValueFactory<Game, String>("winner"));
     date.setCellValueFactory(new PropertyValueFactory<Game, Date>("date"));

     historyGamesTable.setItems(Gamesdata);
    }
    
	void exit(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
	
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillHistoryTable();	}

}
