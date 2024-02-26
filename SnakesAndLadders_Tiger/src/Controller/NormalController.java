package Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;

import Model.Board;
import Model.Color;
import Model.Dice;
import Model.Difficulty;
import Model.Game;
import Model.Ladder;
import Model.LadderTile;
import Model.Player;
import Model.Question;
import Model.QuestionFactory;
import Model.QuestionTile;
import Model.Snake;
import Model.SnakeColor;
import Model.SnakeTile;
import Model.SysData;
import Model.Tile;
import Model.TileType;
import View.Alerts;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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


public class NormalController implements Initializable{
    //Sizes
	private final double ICON_SIZE = 35; // the moving icons on the board
    private final double IMAGE_WIDTH = 45; // the icons next to the names
    private final double IMAGE_HEIGHT = 45;
    private final double QUESTION_SIZE = 30; // question icon size
    private final double SURPRISE_SIZE = 35; // surprise icon size
	private final double TOKEN_SIZE = 35;
    //Lists
    private HashMap<Player, Image> icons = new HashMap<>();
    private HashMap<Player, ImageView> iconsOnBoard = new HashMap<>();
    //Path
    private static final String ARROW = "/img/icons/arrow3.gif"; //Player's turn arrow path
	private static final String GREEN = "/img/icons/greenPlayer.png"; //Player Token path
	private static final String BLUE = "/img/icons/bluePlayer.png"; //Player Token path
	private static final String PINK = "/img/icons/pinkPlayer.png"; //Player Token path
	private static final String RED = "/img/icons/redPlayer.png"; //Player Token path
	private static final String PURPLE = "/img/icons/purplePlayer.png"; //Player Token path
	private static final String YELLOW = "/img/icons/yellowPlayer.png";	//Player Token path
	private static final String INFO_IMAGE_PATH = "/img/screens/blank.jpg";
	private static final String QUESTION_IMAGE_PATH = "/img/icons/question.png";
	private static final String SURPRISE_IMAGE_PATH = "/img/icons/surprise.png";
	private static final String DEFAULT_DICE_IMAGE_PATH = "/img/icons/dice.png";
	private static final String SURPRISE_GIF_PATH =  "/img/icons/surpriseGIF.gif";
	private static final String SURPRISE_PLUS_PATH = "/img/icons/surprisePlus.png"; 
	private static final String SURPRISE_MINUS_PATH = "/img/icons/surpriseMinus.png"; 	
	private static final int VISIBLE_DURATION_MS = 4500; // 10 seconds
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	ArrayList<Player> playersOutsideBoard = new ArrayList<>();
	boolean correct = false; // checks if answer is correct
    int returnVal = 0; // returns the number of steps the player should move based on their answer
	private GameController gameController;
	private int currentPlayerIndex = 0;
	public static Game game;
	Board board = new Board(game.getDifficulty());
	public Player currentTurn;
	
    @FXML
    private ImageView surpriseValue;
	
    @FXML
    private ImageView surprise;	
	
	@FXML
	private AnchorPane rootAnchorPane;

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
    
    @FXML
    private Button diceButton;
    
    @FXML
    private ImageView diceImage;
    
    @FXML
    private ImageView diceResult;
    
    private HashMap<Integer, String> diceImageMap;

    private ArrayList<Player> originalOrder = new ArrayList<>();
    public void initializeMap() {
        // Initialize the mapping between dice numbers and image paths of it 
        diceImageMap = new HashMap<>();
        diceImageMap.put(0, "/img/icons/dice0.png");
        diceImageMap.put(1, "/img/icons/dice1.png");
        diceImageMap.put(2, "/img/icons/dice2.png");
        diceImageMap.put(3, "/img/icons/dice3.png");
        diceImageMap.put(4, "/img/icons/dice4.png");
        diceImageMap.put(5, "/img/icons/dice5.png");
        diceImageMap.put(6, "/img/icons/dice6.png");
        diceImageMap.put(7, "/img/icons/diceQ.png");
        diceImageMap.put(8, "/img/icons/diceQ.png");
        diceImageMap.put(9, "/img/icons/diceQ.png");
        diceImageMap.put(10, "/img/icons/diceQ.png");
        diceImageMap.put(11, "/img/icons/diceQ.png");
        diceImageMap.put(12, "/img/icons/diceQ.png");
    }
       
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image defaultImage = new Image(getClass().getResource(DEFAULT_DICE_IMAGE_PATH).toExternalForm());
	    diceResult.setImage(defaultImage);
		Tooltip r = new Tooltip("Game Rules");
        Tooltip.install(info, r);
		if(!board.createBoard())
    	{
    		Alerts.warning("An error occured while creating the board!");
    		return;
    	}
        startTimer();
        Dice.RollingDiceStartingGame(game); // fills the queue randomly to determine the order of player turns
		showPlayers();
	    gameController = new GameController(board, grid);
	    gameController.showLadders();
	    gameController.showSnakes();
		showQuestions();
		showSurprises();
		ensureExitButtonOnTop();
	}
	
	private void startTimer() {
		// Create a timeline for the game duration
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            gameDuration = gameDuration.add(Duration.seconds(1));
            updateTimeLabel();
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }
	
	// Method to update the time label on the screen
	private void updateTimeLabel() {
	    long totalSeconds = (long) gameDuration.toSeconds();
	    long minutes = totalSeconds / 60;
	    long seconds = totalSeconds % 60;
	    // Convert long values to String with two digits
	    String minutesString = String.format("%02d", minutes);
	    String secondsString = String.format("%02d", seconds);
	    String formattedTime = minutesString + " : " + secondsString;
	    time.setText(formattedTime);
	}
	
	// Method to stop the timer and return the duration of the game
	private Duration stopTimer() {
	    timer.stop();
	    return gameDuration;
	}

	// Method to show the player names and associated colors according to their randomly chosen order of play
	// and additionally show the player tokens that will be moving on the board during the game
	public void showPlayers() {
		ObservableList<ImageView> playerIcons = FXCollections.observableArrayList();
		for (Player p : game.getPlayersOrder()) {
			if (p.getPlayerColor() == Color.Green) {
				Image greenPlayer = new Image(getClass().getResource(GREEN).toExternalForm());
				ImageView greenImageView = new ImageView(greenPlayer);
				playerIcons.add(greenImageView);
				iconsOnBoard.put(p, greenImageView); // save players icons to control during the game
				greenImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, greenPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Blue) {
				Image bluePlayer = new Image(getClass().getResource(BLUE).toExternalForm());
				ImageView blueImageView = new ImageView(bluePlayer);
				playerIcons.add(blueImageView);
				iconsOnBoard.put(p, blueImageView); // save players icons to control during the game
				blueImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, bluePlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Pink) {
				Image pinkPlayer = new Image(getClass().getResource(PINK).toExternalForm());
				ImageView pinkImageView = new ImageView(pinkPlayer);
				playerIcons.add(pinkImageView);
				iconsOnBoard.put(p, pinkImageView); // save players icons to control during the game
				pinkImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, pinkPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Red) {
				Image redPlayer = new Image(getClass().getResource(RED).toExternalForm());
				ImageView redImageView = new ImageView(redPlayer);
				playerIcons.add(redImageView);
				iconsOnBoard.put(p, redImageView); // save players icons to control during the game
				redImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, redPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Purple) {
				Image purplePlayer = new Image(getClass().getResource(PURPLE).toExternalForm());
				ImageView purpleImageView = new ImageView(purplePlayer);
				playerIcons.add(purpleImageView);
				iconsOnBoard.put(p, purpleImageView); // save players icons to control during the game
				purpleImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, purplePlayer); // associate created icon with the player
			}
			else if (p.getPlayerColor() == Color.Yellow) {
				Image yellowPlayer = new Image(getClass().getResource(YELLOW).toExternalForm());
				ImageView yellowImageView = new ImageView(yellowPlayer);
				playerIcons.add(yellowImageView);
				iconsOnBoard.put(p, yellowImageView); // save players icons to control during the game
				yellowImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, yellowPlayer); // associate created icon with the player
			}
			playersOutsideBoard.add(p);
		}
		for (ImageView iv : playerIcons) { // the player icons that will move on the board
			iv.setFitHeight(ICON_SIZE);
			iv.setFitWidth(ICON_SIZE);
			iv.setStyle("-fx-effect:  dropshadow(one-pass-box , black , 8 , 0.0 , 0 , 3);");
			playersStart.getChildren().add(iv);
		}
		
		for (Player p : game.getPlayersOrder()) { // the players names and assigned colors
			originalOrder.add(p);
			Image arrow = new Image(getClass().getResourceAsStream(ARROW));
			ImageView arrowIV = new ImageView();
			arrowIV.setImage(arrow);
			//arrowIV.setOpacity(0);
			arrowIV.setFitHeight(20);
			arrowIV.setPreserveRatio(true);
			StackPane arrowPane = new StackPane(arrowIV);
			arrowPane.setPadding(new Insets(0,0,20,0)); // top right bottom left
			arrowPane.setOpacity(0);
			Label name = new Label(p.getPlayerName());
			name.setStyle("-fx-font-family: Serif; -fx-font-size: 20px;");
			name.setPadding(new Insets(10,5,10,5)); // top right bottom left
			ImageView icon = new ImageView(icons.get(p));
			icon.setFitHeight(IMAGE_HEIGHT);
			icon.setFitWidth(IMAGE_WIDTH);
			icon.setVisible(true);
			if (player1.getChildren().isEmpty())
				player1.getChildren().addAll(arrowPane, icon, name);
			else if (player2.getChildren().isEmpty())
				player2.getChildren().addAll(arrowPane, icon, name);
			else if (player3.getChildren().isEmpty())
				player3.getChildren().addAll(arrowPane, icon, name);
			else if (player4.getChildren().isEmpty())
				player4.getChildren().addAll(arrowPane, icon, name);
		}
	    // Point the arrow to the first player to indicate their turn
		player1.getChildren().get(0).setOpacity(1);
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
    void handleDiceClick(ActionEvent event) throws InterruptedException {
    	// Enable the button after animation completes
        diceButton.setDisable(true);
        diceButton.setOpacity(1.0);
        diceButton.setStyle("-fx-background-color: transparent;");
        initializeMap();
        int[] lastDiceResult = new int[1]; // Array to hold the result
        Player currentPlayer = getNextPlayerToMove();
        Duration duration = Duration.millis(1000);
        int numFrames = 20;
        Duration frameInterval = duration.divide(numFrames);
        Timeline timeline = new Timeline();
        // Add keyframes to the timeline
        for (int i = 0; i < numFrames; i++) {
        	int result = Dice.RandomNumberGenerator(Difficulty.Medium);
            lastDiceResult[0] = result; // Save the result into the array
            String imagePath = diceImageMap.get(result);

            // Create a keyframe for each image of the dice
            KeyFrame keyFrame = new KeyFrame(frameInterval.multiply(i),
                    e -> updateDiceImage(imagePath));

            timeline.getKeyFrames().add(keyFrame);
        }
        
        timeline.setCycleCount(1); // Set the timeline to one cycle
        timeline.setOnFinished(e -> {
            
            // After 5 seconds, reset the dice image to the default
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
            pauseTransition.setOnFinished(event1 -> onFinished(currentPlayer, lastDiceResult[0]));
            pauseTransition.play();
        });
        System.out.println("Dice result: "+lastDiceResult[0]);
        // Display the dice result

        timeline.play(); // Start the animation
    }

    public void onFinished(Player currentPlayer, int lastResult) {
    	updateDiceImage(DEFAULT_DICE_IMAGE_PATH); // Reset dice image to original
        diceButton.setDisable(false); // Enable the button after animation completes
        // Move the current player based on the dice result after animation completes
        viewResultDice(currentPlayer, lastResult);
    }
    
    private Player getNextPlayerToMove() {
        Player nextPlayer = game.getPlayersOrder().poll(); // Take the player out of the queue to start their turn
        game.getPlayersOrder().offer(nextPlayer); // Return the player to the end of the queue
        return nextPlayer;
    }

    private void viewResultDice(Player currentPlayer,int diceResult) {//this for easy difficulty only
    	if(diceResult <= 6) {
    		move(currentPlayer, diceResult);
		}
    	else if(diceResult == 7 || diceResult == 8) {
    		//display easy question 
    		Platform.runLater(() -> {
    		    // Place your UI-related operation here
    			int steps = showQuestionPopup(Difficulty.Easy);
    			System.out.println("steps to move after question are: "+steps);
        		move(currentPlayer, steps); 
    		});
//    		move(currentPlayer, 20); // TODO this is temporary for testing purposes, revert back when done
    	}
    	else if(diceResult == 9 || diceResult == 10) {
    		//display normal question 
    		Platform.runLater(() -> {
    		    // Place your UI-related operation here
    			int steps = showQuestionPopup(Difficulty.Medium);
    			System.out.println("steps to move after question are: "+steps);
        		move(currentPlayer, steps); 
    		});
//    		move(currentPlayer, 20); // TODO this is temporary for testing purposes, revert back when done
    	}
    	else if(diceResult == 11 || diceResult == 12) {
    		//display hard question 	
    		Platform.runLater(() -> {
    		    // Place your UI-related operation here
    			int steps = showQuestionPopup(Difficulty.Hard);
    			System.out.println("steps to move after question are: "+steps);
        		move(currentPlayer, steps); 
    		});
//    		move(currentPlayer, 20); // TODO this is temporary for testing purposes, revert back when done
        }	
	}

	private void updateDiceImage(String imagePath) {//update the dice image 
    	 Image image = new Image(getClass().getResource(imagePath).toExternalForm());
    	 diceResult.setImage(image);
    	 if (imagePath.equals(DEFAULT_DICE_IMAGE_PATH)) {
    		 diceResult.setStyle("-fx-effect:  dropshadow(one-pass-box , black , 8 , 0.0 , 2 , 4);");
    	 }
    	 else {
    		 diceResult.setStyle("-fx-effect:  dropshadow(one-pass-box , black , 8 , 0.0 , 0 , 0);");
    	 }
    }
	
	public void win(int currentRow, int currentColumn, Player p, int newPosition) {
		p.setPlayerPlace(newPosition);
		hidePlayerToken(p);
        displayPlayerToken(currentRow, currentColumn, p, newPosition); // display the winner at the last tile
        game.setWinner(p);
        String durationGame = Game.formatDuration(stopTimer());
        game.setGameDuration(durationGame);
        WinnerController.game = game;
        try {
			SysData.getInstance().writeToJsonGames(game);
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
        newScreen("Winner");
        System.out.println(p.getPlayerName() + " is the WINNER!");
	}
    
	void move(Player player, int steps) {	    
	    int currentPosition = player.getPlayerPlace();
	    int currentRow;
	    int currentColumn;
	    if (currentPosition > 0) {
	    	Tile pos = board.getTile(currentPosition);
		    currentRow = pos.getxCoord();
		    currentColumn = pos.getyCoord();
	    }
	    else {
	    	currentRow = board.getBoardLen()-1;
	    	currentColumn = 0;
	    }
	    System.out.println("current player position: "+currentPosition);
	    if (steps != 0) {
		    
		    int nextPos = currentPosition + steps;
		    int newPosition = NextMove(currentRow, currentColumn, currentPosition, steps, player);
		    if (nextPos < board.getBoardSize() && nextPos >= 1) {
		    	player.setPlayerPrevPlace(currentPosition);
			    hidePlayerToken(player);
		    	Tile nextTile = board.getTile(nextPos);
		    	// if the next tile is a snake head or ladder bottom,
		    	// move the player to that tile before moving them to the snake tail / ladder top
		    	if (nextTile.gettType().equals(TileType.LadderBottom) || nextTile.gettType().equals(TileType.SnakeHead)) {
		    		int newRow = nextTile.getxCoord();
				    int newColumn = nextTile.getyCoord();
				    Platform.runLater(() -> {
				    	displayPlayerToken(currentRow, currentColumn, player, nextPos);
			    		hidePlayerToken(player);
				    });
//		    		scheduleTask(newRow, newColumn, player, newPosition);
		    		Platform.runLater(() -> {
		    			player.setPlayerPlace(newPosition);
					    displayPlayerToken(newRow, newColumn, player, newPosition);
		    		});
		    	}
		    	else {
			    	// Set player's new position
				    player.setPlayerPlace(newPosition);
				    displayPlayerToken(currentRow, currentColumn, player, newPosition);
			    }
		   }
		    // Check if player reached the last tile and end the game
		    if (newPosition == board.getBoardSize()) {
		        win(currentRow, currentColumn, player, newPosition);
		    } 
	    }
	    
	    // clear arrow once the player finishes their turn
	    if (player.equals(originalOrder.get(0))) {
	    		player1.getChildren().get(0).setOpacity(0);
        }
        else if (player.equals(originalOrder.get(1))) {
	    		player2.getChildren().get(0).setOpacity(0);
        }
        else if (player.equals(originalOrder.get(2))) {
        		player3.getChildren().get(0).setOpacity(0);
        }
        else if (player.equals(originalOrder.get(3))) {
        		player4.getChildren().get(0).setOpacity(0);
        }
	    Player nextPlayer = game.getPlayersOrder().peek();
	    // Point the arrow to the next player to indicate their turn
        if (nextPlayer.equals(originalOrder.get(0))) {
        	player1.getChildren().get(0).setOpacity(1);
        }
        else if (nextPlayer.equals(originalOrder.get(1))) {
        	player2.getChildren().get(0).setOpacity(1);
        }
        else if (nextPlayer.equals(originalOrder.get(2))) {
        		player3.getChildren().get(0).setOpacity(1);
        }
        else if (nextPlayer.equals(originalOrder.get(3))) {
        		player4.getChildren().get(0).setOpacity(1);
        }
	}
	
	private void displayPlayerToken(int currentR, int currentC, Player player, int newPosition) {
	    Image tokenImage = new Image(getClass().getResource(getTokenImagePath(player)).toExternalForm());
	    ImageView token = iconsOnBoard.get(player);
	    if (token == null) {
	        token = new ImageView(tokenImage);
	        iconsOnBoard.put(player, token);
	    }
	    token.setId(String.valueOf(player.getPlayerID()));
	    token.setFitHeight(TOKEN_SIZE);
	    token.setFitWidth(TOKEN_SIZE);
	    token.setStyle("-fx-effect:  dropshadow(one-pass-box , black , 8 , 0.0 , 0 , 3);");
	    token.setVisible(true);
	    if(newPosition!=0) {
	    	// If the token is not already in the grid, add it
		    if (!grid.getChildren().contains(iconsOnBoard.get(player))) {
		        grid.getChildren().add(iconsOnBoard.get(player));
		    }
		    Tile pos = board.getTile(newPosition);
		    int row = pos.getxCoord();
		    int column = pos.getyCoord();
		    moveTokenToCell(currentR, currentC, row, column, token);		    

//		    GridPane.setRowIndex(iconsOnBoard.get(player), row);
//		    GridPane.setColumnIndex(iconsOnBoard.get(player), column);
		    
		    //check if other players are already on this tile to avoid covering each other's tokens
		    ArrayList<Player> otherPlayers = new ArrayList<>(); // ArrayList for the other players
		    for (Player p : game.getPlayers())
		    	otherPlayers.add(p);
		    otherPlayers.remove(player); // remove the current player playing from this list
		    
		    ArrayList<Integer> enteredTileOrder = new ArrayList<>(); // ArrayList for how the other players entered this tile
		    // A player can be positioned on the tile in 4 different places in order to avoid covering each other
		    for (Player p : otherPlayers) { // loop over the other players
		    	if (p.getPlayerPlace() == newPosition) { // if the other player is on this tile i'm heading to
		    		enteredTileOrder.add(p.getEnteredTile()); // save where they are positioned on the tile so I don't cover them
		    	}
		    }
		    if (!enteredTileOrder.contains(1)) { // if no other player is in position number 1 on the tile
		    	GridPane.setHalignment(iconsOnBoard.get(player), javafx.geometry.HPos.CENTER); // Center horizontally
		    	GridPane.setValignment(iconsOnBoard.get(player), javafx.geometry.VPos.CENTER); // Center vertically
		    	player.setEnteredTile(1); // I entered in the first position
		    }
		    else if (!enteredTileOrder.contains(2)) { // if no other player is in position number 2 on the tile
		    	GridPane.setHalignment(iconsOnBoard.get(player), javafx.geometry.HPos.LEFT); // set player at the cell's left
		        GridPane.setValignment(iconsOnBoard.get(player), javafx.geometry.VPos.CENTER);
		        player.setEnteredTile(2); // I entered in the second position
		    }
		    else if (!enteredTileOrder.contains(3)) { // if no other player is in position number 3 on the tile
		    	GridPane.setHalignment(iconsOnBoard.get(player), javafx.geometry.HPos.RIGHT); // set player at the cell's right
	    		GridPane.setValignment(iconsOnBoard.get(player), javafx.geometry.VPos.CENTER);
	    		player.setEnteredTile(3); // I entered in the third position
		    }
		    else if (!enteredTileOrder.contains(4)) { // if no other player is in position number 4 on the tile
		    	GridPane.setHalignment(iconsOnBoard.get(player), javafx.geometry.HPos.CENTER);
	    		GridPane.setValignment(iconsOnBoard.get(player), javafx.geometry.VPos.BOTTOM); // set player at the cell's bottom
	    		player.setEnteredTile(4); // I entered in the fourth position
		    }
		    else { // no players on this tile, I'm the first player on this tile
		    	GridPane.setHalignment(iconsOnBoard.get(player), javafx.geometry.HPos.CENTER); // Center horizontally
		    	GridPane.setValignment(iconsOnBoard.get(player), javafx.geometry.VPos.TOP); // Top vertically
		    	player.setEnteredTile(1);
		    }
	    }
	    else { // if the new position is 0
	    	if (!playersStart.getChildren().contains(iconsOnBoard.get(player))) { // if the player is not outside the board
	    		playersStart.getChildren().add(token); // place the player outside the board = position 0
	    	}
	    }
	}
	
	private void moveTokenToCell(int currentRow, int currentColumn, int targetRow, int targetColumn, ImageView token) {
		double tileWidth = grid.getPrefWidth()/board.getBoardLen();
		double tileHeight = grid.getPrefHeight()/board.getBoardLen();

		double width = tileWidth * (targetColumn-currentColumn); // move along the x axis
    	double height = tileHeight * (targetRow-currentRow); // move along the y axis   

        // Create TranslateTransition to move the ImageView to the target position
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), token);
        translateTransition.setToX(width);
        translateTransition.setToY(height);
        
        // Play the TranslateTransition
        translateTransition.play();
        
        // Update the row and column index of the token --> this is where the animation will start from
        GridPane.setRowIndex(token, currentRow);
        GridPane.setColumnIndex(token, currentColumn);
    }

	private void hidePlayerToken(Player p) {
	    ImageView token = iconsOnBoard.get(p);
	    if (token != null) {
	        grid.getChildren().remove(token);
	    }
	    // If player token is still outside the board, remove it from there
	    for (Iterator<Node> iterator = playersStart.getChildren().iterator(); iterator.hasNext();) { // Iterate over tokens outside the board
	        Node node = iterator.next();
	        if (node instanceof ImageView && ((ImageView) node).getId().equals(String.valueOf(p.getPlayerID()))) { // If token is this player
	            iterator.remove(); // Remove the token from the HBox outside the board
	            break; // Exit the loop once the token is removed
	        }
	    }
	}

    int NextMove(int currentRow, int currentColumn, int currPosition, int steps, Player p) {
    	System.out.println("steps to move in NextMove are: " + steps);
	    int nextPos = currPosition + steps;

	    if (nextPos > board.getBoardSize()) {
	    	System.out.println("next position is "+ nextPos +" and is larger than the board size os smaller than 1.");
	        return currPosition; // Ensure next position is within the board boundaries
	    }
	    
	    if (nextPos < 1) // Ensure next position is within the board boundaries
	    	return 0;

	    Tile nextTile = board.getTile(nextPos);
	    if (nextTile == null) {
	    	System.out.println("next position is "+ nextPos +" and that tile is null.");
	        return currPosition; // Handle case where tile is null
	    }
	    
	    switch (nextTile.gettType()) {
	        case SnakeHead:
	            SnakeTile snakeT = (SnakeTile) nextTile;
	            Snake snake = snakeT.getSnake();
//	            displayPlayerToken(currentRow, currentColumn, p, nextPos);
//				hidePlayerToken(p);
	            if (snake.getColor() == SnakeColor.Red) {
	                System.out.println("Next step will be: 1");
	                return 1;
	            } else {
	                System.out.println("Next step will be: " + snake.getSnakeTail());
	                return snake.getSnakeTail();
	            }
	            
	        case LadderBottom:
	            LadderTile ladderT = (LadderTile) nextTile;
	            Ladder ladder = ladderT.getLadder();
	            System.out.println("Next step will be: " + ladder.getLadderTop());
//	            displayPlayerToken(currentRow, currentColumn, p, nextPos);

	            return ladder.getLadderTop();
	            
	        case Surprise:
	            System.out.println("Yaaaay you got a gift!");
	            int surpriseSteps = handleSurpriseTileReached();
	            return nextPos+surpriseSteps;
	            
	        case Question:
	            System.out.println("I have a question for you");
	            int newPosition = nextPos;
	    	    p.setPlayerPrevPlace(currPosition);
				hidePlayerToken(p);
	    	    System.out.println("new player position: "+newPosition);
	    	    // Set player's new position
	    	    p.setPlayerPlace(newPosition);
	    	    displayPlayerToken(currentRow, currentColumn, p, newPosition);
	    	    System.out.println("current player position on question tile: "+newPosition);
	    	    Platform.runLater(() -> {
	    	    	QuestionTile qt = (QuestionTile) board.getTile(nextPos);
	    	    	int newSteps = showQuestionPopup(qt.getQuestionDiff());
		    		move(p, newSteps);
	    	    });
	            return p.getPlayerPlace();
	        default: // Handle the rest of the tile types which do not require special treatment
	        	System.out.println("Next step will be: " + nextPos);
	            return nextPos;
	    }
	}
    
    
    public int displaySurprise() {
    	// Load the surprise value image
    	Image p = new Image(getClass().getResourceAsStream(SURPRISE_PLUS_PATH));
    	Image m = new Image(getClass().getResourceAsStream(SURPRISE_MINUS_PATH));
        int[] possibleValues = {-10, 10};
        Random random = new Random();
        int index = random.nextInt(possibleValues.length);
        int surpriseResult = possibleValues[index];
        
        if(surpriseResult == -10)
        	surpriseValue.setImage(m);
        else
    	    surpriseValue.setImage(p);

    	// Load the surprise GIF image
    	Image i = new Image(getClass().getResourceAsStream(SURPRISE_GIF_PATH));
    	surprise.setImage(i);

    	// Create a timeline to hide the images after the specified duration
    	Timeline timeline = new Timeline(
    	    new KeyFrame(Duration.millis(VISIBLE_DURATION_MS), event -> {
    	        // Hide the images
    	        surpriseValue.setVisible(false);
    	        surprise.setVisible(false);
    	    })
    	);
    	timeline.play();
    	return surpriseResult;
    }
    
    // Wherever you handle the player reaching a surprise tile
    public int handleSurpriseTileReached() {
        // Show the surprise box and its images
        surpriseValue.setVisible(true);
        surprise.setVisible(true);

        // Call the function to display the surprise and return [-10/+10] steps for the player to move
        return displaySurprise();
    }

    
	public int showQuestionPopup(Difficulty difficulty) { // view the question  dialog  and return the number of steps to move
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Question");
		
			QuestionFactory qf = new QuestionFactory();
			Question q = qf.returnQuestion(difficulty);
			System.out.println(q);
		
		// Create elements for the question and answers
		VBox vbox = new VBox();
		String s = "Question Difficulty: " + difficulty.toString();
		Label diff = new Label(s);
		diff.setPadding(new Insets(0,0,5,0));
		Label questionLabel = new Label(q.getQuestion());
		questionLabel.setPadding(new Insets(5, 0, 5, 0)); // top right bottom left
		questionLabel.setStyle("-fx-font-size: 15px;");
		ToggleGroup answerGroup = new ToggleGroup();
		RadioButton answer1 = new RadioButton(q.getAnswer1());
		RadioButton answer2 = new RadioButton(q.getAnswer2());
		RadioButton answer3 = new RadioButton(q.getAnswer3());
		RadioButton answer4 = new RadioButton(q.getAnswer4());
		answer1.setToggleGroup(answerGroup);
		answer2.setToggleGroup(answerGroup);
		answer3.setToggleGroup(answerGroup);
		answer4.setToggleGroup(answerGroup);
		// setting indexes to the answers in order to easily retrieve them later
		answer1.setUserData(1);
		answer2.setUserData(2);
		answer3.setUserData(3);
		answer4.setUserData(4);
		Label resultText = new Label(); // text that will tell the player whether he was correct or wrong
		resultText.setPadding(new Insets(10, 0, 10, 0)); // top right bottom left
		resultText.setStyle("-fx-font-size: 12px; -fx-font-weight: bolder;");
		Button submit = new Button("Submit Answer");
		submit.setPadding(new Insets(5, 5, 5, 5));// top right bottom left
		// Apply CSS styles to the button
		String btnStyle = "-fx-background-color: #D2691E; " +  // Background color
							"-fx-text-fill: white; " +           // Text color
							"-fx-font-size: 14px; " +            // Font size
							"-fx-font-family: Serif; " +         // Font family
							"-fx-background-radius: 5px; " +     // Background radius
							"-fx-border-radius: 5px; " +         // Border radius
							"-fx-border-color: #DEB887;" +      // Border color
							"-fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 3 , 0 );";
		submit.setStyle(btnStyle);
		submit.setOnMouseEntered(e -> entered(e));
		submit.setOnMouseExited(e -> exited(e));
		submit.setDefaultButton(true); // can press submit by hitting the enter key
		submit.setDisable(true); // Initially disabled until an answer is selected
		      
		HBox submitHB = new HBox(submit);
		submitHB.setPadding(new Insets(15,0,0,5)); // top right bottom left
		
		vbox.getChildren().addAll(diff, questionLabel, answer1, answer2, answer3, answer4, resultText, submitHB);
		dialog.getDialogPane().setContent(vbox); // Set the content of the dialog
		
		ButtonType close = new ButtonType("Close", ButtonData.OK_DONE); // button to close the dialog
		dialog.getDialogPane().getButtonTypes().add(close);
		Node okButton = dialog.getDialogPane().lookupButton(close);
		okButton.setDisable(true); //cannot close the dialog without answering the question first
		  
		StackPane content = new StackPane(vbox);
		dialog.getDialogPane().setContent(content);
		
		answerGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
		    if (newToggle != null) // Enable submit button only when a radio button is selected
		        submit.setDisable(false);
//		    if (answerGroup.getUserData() != null) { // Check if the user has already submitted their answer
//		        answerGroup.selectToggle(oldToggle); // If the user has submitted their answer, deselect the new selection
//		        submit.setDisable(true);  // do not allow the player to submit a different answer
//		    }
		});
		returnVal = 0; // initialize the return value to a  default: 0
		submit.setOnAction(e -> {
			okButton.setDisable(false); // enable closing the dialog after the answer has been submitted
			RadioButton selectedAnswer = (RadioButton) answerGroup.getSelectedToggle(); // Get the selected answer
//			if (selectedAnswer != null) {
//		        answerGroup.setUserData(selectedAnswer); // set this as the user data an prevent changing it later
//		    }
			int selectedAnswerNumber = (int) selectedAnswer.getUserData();// Get the number of selected answer
			int correctAnswerNumber=q.getCorrectAnswer();
			if(selectedAnswerNumber == correctAnswerNumber) {
				selectedAnswer.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
			    if(difficulty==Difficulty.Easy || difficulty==Difficulty.Medium) {
			    	resultText.setText("Your answer is correct! You will stay in place!");
					returnVal = 0;
				}
				else {
					resultText.setText("Your answer is correct! You will move one step forward!");
				    returnVal = 1;
				}
			}
			else {
			      selectedAnswer.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
				switch (q.getCorrectAnswer()) {//mark the right answer in green
				case 1:
					answer1.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
					break;
				case 2:
					answer2.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
					break;
				case 3:
					answer3.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
					break;
				case 4:
					answer4.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
					break;
				}
				if(difficulty==Difficulty.Easy) {
					resultText.setText("Your answer is wrong! You will move one step backward!");
					returnVal = -1;
				}
				if(difficulty==Difficulty.Medium) {
					resultText.setText("Your answer is wrong! You will move two steps backward!");
					returnVal = -2;
				}
				if(difficulty==Difficulty.Hard) {
					resultText.setText("Your answer is wrong! You will move three steps backward!");
					returnVal = -3;
				}
			}
		});
      
      dialog.showAndWait();
      return returnVal;
	}
	
  	private String getTokenImagePath(Player player) {
	    switch (player.getPlayerColor()) {
	        case Red:
	            return RED;
	        case Green:
	            return GREEN;
	        case Yellow:
	            return YELLOW;
	        case Blue:
	            return BLUE;
	        case Pink:
	            return PINK;
	        case Purple:
	            return PURPLE;
	        default:
	            return null;
	    }
	}
	
	private void ensureExitButtonOnTop() {
	    rootAnchorPane.getChildren().remove(exitButton); // Remove exitButton from AnchorPane
	    rootAnchorPane.getChildren().add(exitButton);    // Re-add exitButton to AnchorPane (on top)
	}
	

    @FXML
    void showInfo(ActionEvent event) throws IOException{
//    	Dialog<Void> dialog = new Dialog<>();
//        dialog.setTitle("Game Rules");
//        dialog.setHeaderText("");
//        dialog.setWidth(800);
//        dialog.setHeight(500);
//        Image info = new Image(getClass().getResource(INFO_IMAGE_PATH).toExternalForm());
//		ImageView background = new ImageView(info);
//		background.setFitHeight(dialog.getHeight());
//		background.setFitWidth(dialog.getWidth());
//		background.setVisible(true);
//		ArrayList<Label> labels = new ArrayList<>();
//		Label diceL = new Label("Roll the dice to determine your fate");
//		labels.add(diceL);
//		Label turnL = new Label("The green border indicates your turn");
//		labels.add(turnL);
//		Label snakeL = new Label("Encounter snakes and slide down");
//		labels.add(snakeL);
//		Label ladderL = new Label("Find ladders and climb up");
//		labels.add(ladderL);
//		Label questionL = new Label("Land on a question mark or get one on the dice roll and answer questions for your destiny");
//		labels.add(questionL);
//		Label surpriseL = new Label("Surprises can move you forward or backward");
//		labels.add(surpriseL);
//		Label winL = new Label("Be the first to reach the last tile to claim victory!");
//		for (Label l :labels) {
//			l.setStyle("-fx-font-family: Serif; -fx-font-size: 17px;");
//			l.setPadding(new Insets(10,0,5,10)); // top right bottom left
//		}
//		winL.setStyle("-fx-font-family: Serif; -fx-font-size: 22px;");
//		ArrayList<ImageView> imgs = new ArrayList<>();
//		Image dice = new Image(getClass().getResource("/img/icons/dice.png").toExternalForm());
//		ImageView diceIV = new ImageView(dice);
//		imgs.add(diceIV);
//		Image pawn = new Image(getClass().getResource("/img/icons/pawn.png").toExternalForm());
//		ImageView pawnIV = new ImageView(pawn);
//		Label name = new Label ("yourName");
//		name.setPadding(new Insets(7,5,5,0));
//		name.setStyle("-fx-font-size: 10px;");
//
//		HBox pawnTurn = new HBox(pawnIV, name);
//		pawnTurn.setStyle("-fx-border-color: #00FF00; -fx-border-radius: 10; -fx-border-width: 3;");
//		imgs.add(pawnIV);
//		Image snake = new Image(getClass().getResource("/img/icons/redSnake.png").toExternalForm());
//		ImageView snakeIV = new ImageView(snake);
//		imgs.add(snakeIV);
//		Image ladder = new Image(getClass().getResource("/img/icons/ladderIcon.png").toExternalForm());
//		ImageView ladderIV = new ImageView(ladder);
//		imgs.add(ladderIV);
//		Image q = new Image(getClass().getResource("/img/icons/question.png").toExternalForm());
//		ImageView qIV = new ImageView(q);
//		imgs.add(qIV);
//		Image s = new Image(getClass().getResource("/img/icons/surprise.png").toExternalForm());
//		ImageView sIV = new ImageView(s);
//		imgs.add(sIV);
//		
//		for(ImageView iv : imgs) {
//			iv.setFitHeight(ICON_SIZE);
//			iv.setFitWidth(ICON_SIZE);
//		}
//		VBox vbox = new VBox(new HBox(diceIV, diceL), new HBox(pawnTurn, turnL), new HBox(snakeIV, snakeL), 
//				new HBox(ladderIV, ladderL), new HBox(qIV, questionL), new HBox(sIV, surpriseL), winL);
//		vbox.setAlignment(Pos.CENTER);
//        vbox.setSpacing(10); // Set spacing between the lines
//        vbox.setPadding(new Insets(0, 20, 50, 80)); // top right bottom left
//        StackPane content = new StackPane(background, vbox);
//        dialog.getDialogPane().setContent(content);
//
//        ButtonType closeButton = new ButtonType("Close", ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().add(closeButton);
//
//        dialog.showAndWait();
    	
    	// Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Info.fxml"));
        try {
            AnchorPane dialogContent = loader.load();
            // Create a dialog
            Dialog<Void> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(dialogContent);
            dialog.setTitle("Game Rules");
            ButtonType closeButton = new ButtonType("Close", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(closeButton);
            // Show the dialog
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@FXML
	void returnHome(ActionEvent event) {
		if (Alerts.retunHome() == 1)
			newScreen("Home");
    }
	
	@FXML
    void exit(ActionEvent event) {
		if (Alerts.exit()==1)
			Main.mainWindow.close();
    }
	
	@FXML
    void entered(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1.1);
    	((Node)event.getSource()).setScaleY(1.1);
    	((Node)event.getSource()).setCursor(Cursor.HAND);
    }
	
    @FXML
    void exited(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1);
    	((Node)event.getSource()).setScaleY(1);
    	((Node)event.getSource()).setCursor(Cursor.DEFAULT);
    }
    
	void newScreen(String path) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/"+path+".fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
		} catch (Exception e) {
			// handle exception
			e.printStackTrace();
		}  	
    }
}