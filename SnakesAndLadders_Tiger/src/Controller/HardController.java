package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import Controller.ChoosePlayersController.ImageListCell;
import Model.Board;
import Model.Color;
import Model.Dice;
import Model.Difficulty;
import Model.Game;
import Model.Ladder;
import Model.Player;
import Model.QuestionTile;
import Model.Snake;
import Model.SnakeColor;
import Model.Tile;
import View.Alerts;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HardController implements Initializable{

	private final double ICON_SIZE = 35; // the moving icons on the board
    private final double IMAGE_SIZE = 45; // the icons next to the names
    private final double QUESTION_SIZE = 30; // question icon size
    private final double SURPRISE_SIZE = 25; // surprise icon size
    private HashMap<Player, Image> icons = new HashMap<>();
    private HashMap<Player, ImageView> iconsOnBoard = new HashMap<>();
	private static final String GREEN = "/img/icons/greenPlayer.png";
	private static final String BLUE = "/img/icons/bluePlayer.png";
	private static final String PINK = "/img/icons/pinkPlayer.png";
	private static final String RED = "/img/icons/redPlayer.png";
	private static final String PURPLE = "/img/icons/purplePlayer.png";
	private static final String YELLOW = "/img/icons/yellowPlayer.png";	
	private static final String INFO_IMAGE_PATH = "/img/screens/blank.jpg";
	private static final String QUESTION_IMAGE_PATH = "/img/icons/question.png";
	private static final String SURPRISE_IMAGE_PATH = "/img/icons/surprise.png";
	
	private GameController gameController;
	public static Game game;
	Board board = new Board(game.getType());
	
	@FXML
	private AnchorPane rootAnchorPane;
	
	@FXML
    private Button temp;
	
	public Player currentTurn;
	
    @FXML
    private AnchorPane screen;
	
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

    @FXML
    private Button home;

    @FXML
    private Button info;
    
    @FXML
    private Label time;
    
    @FXML
    private Timeline timer;
    
    @FXML
    private Duration gameDuration = Duration.ZERO;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Tooltip r = new Tooltip("Game Rules");
        Tooltip.install(info, r);
		if(!board.createBoard())
    	{
    		Alerts.warning("An error occured while creating the board!");
    		return;
    	}
        startTimer();
		showPlayers();
	    gameController = new GameController(board, grid);
	    gameController.showSnakes();
	    gameController.showLadders();
		showQuestions();
		showSurprises();
		ensureExitButtonOnTop();
	//	Dice.RollingDiceStartingGame(game);
	}
	
	private void startTimer() {
		// Create a timeline for the game duration
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            gameDuration = gameDuration.add(Duration.seconds(1));
            long totalSeconds = (long) gameDuration.toSeconds();
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;
            // Convert long values to String with two digits
            String minutesString = String.format("%02d", minutes);
            String secondsString = String.format("%02d", seconds);
            String gameDuration = minutesString + " : " + secondsString;
            time.setText(gameDuration);
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

	@FXML
    void win(ActionEvent event) {
		newScreen("Winner");
    }

	public void showPlayers() {
		ObservableList<ImageView> playerIcons = FXCollections.observableArrayList();
		for (Player p : game.getPlayers()) {
			if (p.getPlayerColor() == Color.Green) {
				Image greenPlayer = new Image(getClass().getResource(GREEN).toExternalForm());
				ImageView greenImageView = new ImageView(greenPlayer);
				playerIcons.add(greenImageView);
				iconsOnBoard.put(p, greenImageView); // save players icons to control during the game
				icons.put(p, greenPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Blue) {
				Image bluePlayer = new Image(getClass().getResource(BLUE).toExternalForm());
				ImageView blueImageView = new ImageView(bluePlayer);
				playerIcons.add(blueImageView);
				iconsOnBoard.put(p, blueImageView); // save players icons to control during the game
				icons.put(p, bluePlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Pink) {
				Image pinkPlayer = new Image(getClass().getResource(PINK).toExternalForm());
				ImageView pinkImageView = new ImageView(pinkPlayer);
				playerIcons.add(pinkImageView);
				iconsOnBoard.put(p, pinkImageView); // save players icons to control during the game
				icons.put(p, pinkPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Red) {
				Image redPlayer = new Image(getClass().getResource(RED).toExternalForm());
				ImageView redImageView = new ImageView(redPlayer);
				playerIcons.add(redImageView);
				iconsOnBoard.put(p, redImageView); // save players icons to control during the game
				icons.put(p, redPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Purple) {
				Image purplePlayer = new Image(getClass().getResource(PURPLE).toExternalForm());
				ImageView purpleImageView = new ImageView(purplePlayer);
				playerIcons.add(purpleImageView);
				iconsOnBoard.put(p, purpleImageView); // save players icons to control during the game
				icons.put(p, purplePlayer); // associate created icon with the player
			}
			else if (p.getPlayerColor() == Color.Yellow) {
				Image yellowPlayer = new Image(getClass().getResource(YELLOW).toExternalForm());
				ImageView yellowImageView = new ImageView(yellowPlayer);
				playerIcons.add(yellowImageView);
				iconsOnBoard.put(p, yellowImageView); // save players icons to control during the game
				icons.put(p, yellowPlayer); // associate created icon with the player
			}

		}
		for (ImageView iv : playerIcons) {
			iv.setFitHeight(ICON_SIZE);
			iv.setFitWidth(ICON_SIZE);
			playersStart.getChildren().add(iv);
		}
		
		for (Player p : game.getPlayers()) {
			Label name = new Label(p.getPlayerName());
			name.setStyle("-fx-font-family: Serif; -fx-font-size: 20px;");
			name.setPadding(new Insets(10, 5, 10, 5));
			ImageView icon = new ImageView(icons.get(p));
			icon.setFitHeight(IMAGE_SIZE);
			icon.setFitWidth(IMAGE_SIZE);
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
	

	
	public void showOneCellIcon(Image img, int row, int col, double imgSize) {
		// Create ImageView for the icon
        ImageView iv = new ImageView(img);
        iv.setFitHeight(imgSize);
        iv.setFitWidth(imgSize);
        iv.setVisible(true);
        GridPane.setRowIndex(iv, row);
        GridPane.setColumnIndex(iv, col);
        // Add the icon to GridPane
        grid.getChildren().addAll(iv);
        GridPane.setHalignment(iv, javafx.geometry.HPos.CENTER); // Center horizontally
        GridPane.setValignment(iv, javafx.geometry.VPos.CENTER); // Center vertically
	}

	
	public void showQuestions() {
		for (QuestionTile qt : board.getQuestionTiles()) {
			Image questionImage = new Image(getClass().getResource(QUESTION_IMAGE_PATH).toExternalForm());
			int row = qt.getxCoord();
	        int column = qt.getyCoord();
	        showOneCellIcon(questionImage, row, column, QUESTION_SIZE);
		}
	}
	

	public void showSurprises() {
		for (Tile st : board.getSurpriseTiles()) {
			Image surpriseImage = new Image(getClass().getResource(SURPRISE_IMAGE_PATH).toExternalForm());
			int row = st.getxCoord();
	        int column = st.getyCoord();
	        showOneCellIcon(surpriseImage, row, column, SURPRISE_SIZE);
		}
	}
	
	@FXML
	void returnHome(ActionEvent event) {
		if (Alerts.retunHome() == 1)
			newScreen("Home");
    }
	
	@FXML
    void showInfo(ActionEvent event) throws IOException{
		Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Game Rules");
        dialog.setHeaderText("");
        dialog.setWidth(800);
        dialog.setHeight(500);
        Image info = new Image(getClass().getResource(INFO_IMAGE_PATH).toExternalForm());
		ImageView background = new ImageView(info);
		background.setFitHeight(dialog.getHeight());
		background.setFitWidth(dialog.getWidth());
		background.setVisible(true);
		ArrayList<Label> labels = new ArrayList<>();
		Label diceL = new Label("Roll the dice to determine your fate");
		labels.add(diceL);
		Label turnL = new Label("The green border indicates your turn");
		labels.add(turnL);
		Label snakeL = new Label("Encounter snakes and slide down");
		labels.add(snakeL);
		Label ladderL = new Label("Find ladders and climb up");
		labels.add(ladderL);
		Label questionL = new Label("Land on a question mark or get one on the dice roll and answer questions for your destiny");
		labels.add(questionL);
		Label surpriseL = new Label("Surprises can move you forward or backward");
		labels.add(surpriseL);
		Label winL = new Label("Be the first to reach the last tile to claim victory!");
		for (Label l :labels) {
			l.setStyle("-fx-font-family: Serif; -fx-font-size: 17px;");
			l.setPadding(new Insets(10,0,5,10)); // top right bottom left
		}
		winL.setStyle("-fx-font-family: Serif; -fx-font-size: 22px;");
		ArrayList<ImageView> imgs = new ArrayList<>();
		Image dice = new Image(getClass().getResource("/img/icons/dice.png").toExternalForm());
		ImageView diceIV = new ImageView(dice);
		imgs.add(diceIV);
		Image pawn = new Image(getClass().getResource("/img/icons/pawn.png").toExternalForm());
		ImageView pawnIV = new ImageView(pawn);
		Label name = new Label ("yourName");
		name.setPadding(new Insets(7,5,5,0));
		name.setStyle("-fx-font-size: 10px;");

		HBox pawnTurn = new HBox(pawnIV, name);
		pawnTurn.setStyle("-fx-border-color: #00FF00; -fx-border-radius: 10; -fx-border-width: 3;");
		imgs.add(pawnIV);
		Image snake = new Image(getClass().getResource("/img/icons/redSnake.png").toExternalForm());
		ImageView snakeIV = new ImageView(snake);
		imgs.add(snakeIV);
		Image ladder = new Image(getClass().getResource("/img/icons/ladderIcon.png").toExternalForm());
		ImageView ladderIV = new ImageView(ladder);
		imgs.add(ladderIV);
		Image q = new Image(getClass().getResource("/img/icons/question.png").toExternalForm());
		ImageView qIV = new ImageView(q);
		imgs.add(qIV);
		Image s = new Image(getClass().getResource("/img/icons/surprise.png").toExternalForm());
		ImageView sIV = new ImageView(s);
		imgs.add(sIV);
		
		for(ImageView iv : imgs) {
			iv.setFitHeight(ICON_SIZE);
			iv.setFitWidth(ICON_SIZE);
		}
		VBox vbox = new VBox(new HBox(diceIV, diceL), new HBox(pawnTurn, turnL), new HBox(snakeIV, snakeL), 
				new HBox(ladderIV, ladderL), new HBox(qIV, questionL), new HBox(sIV, surpriseL), winL);
		vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10); // Set spacing between the lines
        vbox.setPadding(new Insets(0, 20, 50, 80)); // top right bottom left
        StackPane content = new StackPane(background, vbox);
        dialog.getDialogPane().setContent(content);

        ButtonType closeButton = new ButtonType("Close", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);

        dialog.showAndWait();
    }
	
	@FXML
    void entered(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1.1);
    	((Node)event.getSource()).setScaleY(1.1);
    	((Node) event.getSource()).setCursor(Cursor.HAND);
    }
	
    @FXML
    void exited(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1);
    	((Node)event.getSource()).setScaleY(1);
    	((Node) event.getSource()).setCursor(Cursor.DEFAULT);
    }
    
	@FXML
    void exit(ActionEvent event) {
		if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
	
	void newScreen(String path) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/"+path+".fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    }
	
	
	private void ensureExitButtonOnTop() {
	    rootAnchorPane.getChildren().remove(exitButton); // Remove exitButton from AnchorPane
	    rootAnchorPane.getChildren().add(exitButton);    // Re-add exitButton to AnchorPane (on top)
	}

}
