package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;

import Model.Difficulty;
import Model.Game;
import Model.Player;

import View.Alerts;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class WinnerController implements Initializable{

	Methods methods = new Methods();
	
	public static Game game;
    @FXML
    private AnchorPane screen;

    @FXML
    private ImageView celebrate;

    @FXML
    private Label winnerLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button playAgainButton;

    @FXML
    private Button home;
    
    @FXML
    private TableView<Game> gameSummary;

    @FXML
    private TableColumn<Game, Player> winner;

    @FXML
    private TableColumn<Game, Difficulty> difficulty;

    @FXML
    private TableColumn<Game, String> duration;

    @FXML
    private TableColumn<Game, LocalDate> date;
    
    Difficulty diff = game.getDifficulty();

    @FXML
    private Button musicIcon;
    
    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.clickSound();
    	methods.turnOffOn(event, musicIcon);
    }
    @FXML
    void playAgain(ActionEvent event) {
    	methods.clickSound();
    	if (diff == Difficulty.Easy) {
    		resetGame(EasyController.game);
     		EasyController.game = new Game(diff, EasyController.game.getPlayers(), LocalDate.now());
     		methods.newScreen("easyBoard");
     	}
     	else if (diff == Difficulty.Medium) {
     		resetGame(NormalController.game);
     		NormalController.game = new Game(diff, NormalController.game.getPlayers(), LocalDate.now());
     		methods.newScreen("normalBoard");
     	}
     	else if (diff == Difficulty.Hard) {
     		resetGame(HardController.game);
     		HardController.game = new Game(diff, HardController.game.getPlayers(), LocalDate.now());
     		methods.newScreen("hardBoard");
     	}
    }
     
    void resetGame(Game game) {
    	game.setGameDuration(null);
    	game.setWinner(null);
    	game.getPlayersOrder().clear();
    	for (Player p : game.getPlayers()) {
    		p.setPlayerPlace(0);
    		p.setPlayerPrevPlace(0);
    		p.setNumberOrder(0);
    	}
    }
     
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillSummary();
		gameSummary.refresh();
        // Load the GIF file
    	Image i = new Image(getClass().getResourceAsStream("/img/icons/celebrating-tiger.gif"));
        // Set the loaded image to the existing ImageView (celebrate)
        celebrate.setImage(i);
        playAgainButton.setStyle(methods.getButtonStyle());
        System.out.println("Image width: " + i.getWidth());
        System.out.println("Image height: " + i.getHeight());
        String congrats = null;
        Player winner = null;
        if (diff == Difficulty.Easy)
        	winner = EasyController.game.getWinner();
     	else if (diff == Difficulty.Medium) 
     		winner = NormalController.game.getWinner();
     	else if (diff == Difficulty.Hard)
     		winner = HardController.game.getWinner();
        
     	if (winner == null) 
     		congrats ="Congrats Haya you won!";
     	else
     		congrats = "Congrats " + winner.getPlayerName() + " you won!";
        
        winnerLabel.setText(congrats);

        // Center the text in the screen
        winnerLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(winnerLabel, 0.0);
        AnchorPane.setRightAnchor(winnerLabel, 0.0);
        winnerLabel.setAlignment(Pos.CENTER);

        // Design the text
        winnerLabel.setStyle("-fx-text-fill: #cc8624; " +
                "-fx-font-size: 36px; " +
                "-fx-font-family: Broadway; ");
        
    	if (Main.note.isPlaying()) {
    		musicIcon.setOpacity(1.0);
    	}
    	else {
    		musicIcon.setOpacity(0.5);
    	}
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void fillSummary() {
		HashSet<Game> arr = new HashSet<>();
  		arr.add(game);
  		System.out.println("arr of game summary: "+arr);
		ObservableList<Game> Gamedata = FXCollections.observableArrayList(arr);
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

        gameSummary.setItems(Gamedata);
	}
	
    @FXML
    void returnHome(ActionEvent event) {
    	methods.clickSound();
    	methods.newScreen("Home");
    }

	 @FXML
    void entered(MouseEvent event){
		 methods.entered(event);
	 }
	 
    @FXML
    void exited(MouseEvent event){
    	methods.exited(event);
    }
	    
    @FXML
    void exit(ActionEvent event) {
    	methods.clickSound();
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
}
