package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Controller.ChoosePlayersController.ImageListCell;
import Model.Board;
import Model.Color;
import Model.Game;
import Model.Player;
import Model.Snake;
import Model.Tile;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EasyController implements Initializable{

	private final double ICON_WIDTH = 35;
    private final double ICON_HEIGHT = 35;
    private HashMap<Player, Image> icons = new HashMap<>();
	private static final String GREEN = "/img/icons/greenPlayer.png";
	private static final String BLUE = "/img/icons/bluePlayer.png";
	private static final String PINK = "/img/icons/pinkPlayer.png";
	private static final String RED = "/img/icons/redPlayer.png";
	private static final String PURPLE = "/img/icons/purplePlayer.png";
	private static final String YELLOW = "/img/icons/yellowPlayer.png";
	
	private static final String SNAKE_IMAGE_PATH = "/img/icons/snake.png"; 
	public static Game game;
	Board board = new Board(game.getType());
	public Player currentTurn;
	
	@FXML
    private GridPane grid;

	@FXML
    private VBox playerNamesVbox;
	
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
		if(!board.createBoard())
    	{
    		Alerts.warning("An error occured while creating the board!");
    		return;
    	}
		showPlayers();
		showSnakes();
	}

	public void showPlayers() {
		ObservableList<ImageView> playerIcons = FXCollections.observableArrayList();
		for (Player p : game.getPlayers()) {
			if (p.getPlayerColor() == Color.Green) {
				Image greenPlayer = new Image(getClass().getResource(GREEN).toExternalForm());
				ImageView greenImageView = new ImageView(greenPlayer);
				playerIcons.add(greenImageView);
				icons.put(p, greenPlayer); // associate created icon with the player
			}
			else if (p.getPlayerColor() == Color.Blue) {
				Image bluePlayer = new Image(getClass().getResource(BLUE).toExternalForm());
				ImageView blueImageView = new ImageView(bluePlayer);
				playerIcons.add(blueImageView);
				icons.put(p, bluePlayer); // associate created icon with the player
			}
			else if (p.getPlayerColor() == Color.Pink) {
				Image pinkPlayer = new Image(getClass().getResource(PINK).toExternalForm());
				ImageView pinkImageView = new ImageView(pinkPlayer);
				playerIcons.add(pinkImageView);
				icons.put(p, pinkPlayer); // associate created icon with the player
			}
			else if (p.getPlayerColor() == Color.Red) {
				Image redPlayer = new Image(getClass().getResource(RED).toExternalForm());
				ImageView redImageView = new ImageView(redPlayer);
				playerIcons.add(redImageView);
				icons.put(p, redPlayer); // associate created icon with the player
			}
			else if (p.getPlayerColor() == Color.Purple) {
				Image purplePlayer = new Image(getClass().getResource(PURPLE).toExternalForm());
				ImageView purpleImageView = new ImageView(purplePlayer);
				playerIcons.add(purpleImageView);
				icons.put(p, purplePlayer); // associate created icon with the player
			}
			else if (p.getPlayerColor() == Color.Yellow) {
				Image yellowPlayer = new Image(getClass().getResource(YELLOW).toExternalForm());
				ImageView yellowImageView = new ImageView(yellowPlayer);
				playerIcons.add(yellowImageView);
				icons.put(p, yellowPlayer); // associate created icon with the player
			}

		}
		for (ImageView iv : playerIcons) {
			iv.setFitHeight(ICON_HEIGHT);
			iv.setFitWidth(ICON_WIDTH);
			playersStart.getChildren().add(iv);
		}
		
		for (Player p : game.getPlayers()) {
			Label name = new Label(p.getPlayerName());
			name.setStyle("-fx-font-family: Serif; -fx-font-size: 20px;");
			ImageView icon = new ImageView(icons.get(p));
			icon.setFitHeight(ICON_HEIGHT);
			icon.setFitWidth(ICON_WIDTH);
			if (player1.getChildren().isEmpty())
				player1.getChildren().addAll(icon, name);
			else if (player2.getChildren().isEmpty())
				player2.getChildren().addAll(icon, name);
			else if (player3.getChildren().isEmpty())
				player3.getChildren().addAll(icon, name);
			else // player4 is empty
				player4.getChildren().addAll(icon, name);
		}
		
	}
	
	public void showSnakes() {
		ArrayList<Snake> snakes = new ArrayList();
		for (HashMap.Entry<Integer, Snake> s : board.getSnakes().entrySet()) { // get the board snakes
            snakes.add(s.getValue());
        }
		for (Snake s : snakes) {
			Image snakeImage = new Image(getClass().getResource(SNAKE_IMAGE_PATH).toExternalForm());
			int headRow = board.getTile(s.getSnakeHead()).getxCoord();
	        int headColumn = board.getTile(s.getSnakeHead()).getyCoord();
	        int tailRow = board.getTile(s.getSnakeTail()).getxCoord();
	        int tailColumn = board.getTile(s.getSnakeTail()).getyCoord();
	        // Create ImageView for snake head
	        ImageView snakeHead = new ImageView(snakeImage);
	        snakeHead.setVisible(true);
	        GridPane.setRowIndex(snakeHead, headRow);
	        GridPane.setColumnIndex(snakeHead, headColumn);

	        // Create ImageView for snake tail
	        ImageView snakeTail = new ImageView(snakeImage);
	        snakeTail.setVisible(true);
	        GridPane.setRowIndex(snakeTail, tailRow);
	        GridPane.setColumnIndex(snakeTail, tailColumn);
	        // Add snake head and tail to GridPane
	        grid.getChildren().addAll(snakeHead, snakeTail);
		}
	}
	
	@FXML
    void exit(ActionEvent event) {
		if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
}
