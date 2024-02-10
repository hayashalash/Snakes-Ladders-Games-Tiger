package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Controller.ChoosePlayersController.ImageListCell;
import Model.Color;
import Model.Game;
import Model.Player;
import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EasyController implements Initializable{

	private static final String GREEN = "/img/icons/greenPlayer.png";
	private static final String BLUE = "/img/icons/bluePlayer.png";
	private static final String PINK = "/img/icons/pinkPlayer.png";
	private static final String RED = "/img/icons/redPlayer.png";
	private static final String PURPLE = "/img/icons/purplePlayer.png";
	private static final String YELLOW = "/img/icons/yellowPlayer.png";
	public static Game game;
	public Player currentTurn;
	
	@FXML
    private GridPane grid;

    @FXML
    private HBox playersStart;

    @FXML
    private HBox player1;

    @FXML
    private HBox player2;

    @FXML
    private HBox player3;

    @FXML
    private HBox player4;
    
    @FXML
    private Button exitButton;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public void showPlayers() {
		ObservableList<Image> playerIcons = FXCollections.observableArrayList();
		for (Player p : game.getPlayers()) {
			if (p.getPlayerColor() == Color.Green) {
				Image greenPlayer = new Image(getClass().getResource(GREEN).toExternalForm());
				playerIcons.add(greenPlayer);
				playersStart.getChildren().add(new ImageView());
			}
			else if (p.getPlayerColor() == Color.Blue) {
				Image bluePlayer = new Image(getClass().getResource(BLUE).toExternalForm());
				playerIcons.add(bluePlayer);
				playersStart.getChildren().add(new ImageView());
			}
			else if (p.getPlayerColor() == Color.Pink) {
				Image pinkPlayer = new Image(getClass().getResource(PINK).toExternalForm());
				playerIcons.add(pinkPlayer);
				playersStart.getChildren().add(new ImageView());
			}
			else if (p.getPlayerColor() == Color.Red) {
				Image redPlayer = new Image(getClass().getResource(RED).toExternalForm());
				playerIcons.add(redPlayer);
				playersStart.getChildren().add(new ImageView());
			}
			else if (p.getPlayerColor() == Color.Purple) {
				Image purplePlayer = new Image(getClass().getResource(PURPLE).toExternalForm());
				playerIcons.add(purplePlayer);
				playersStart.getChildren().add(new ImageView());
			}
			else if (p.getPlayerColor() == Color.Yellow) {
				Image yellowPlayer = new Image(getClass().getResource(YELLOW).toExternalForm());
				playerIcons.add(yellowPlayer);
				playersStart.getChildren().add(new ImageView());
			}

		}
		
	}
	
	@FXML
    void exit(ActionEvent event) {
		if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
}
