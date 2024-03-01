package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;

import java.util.Random;
import Model.Board;
import Model.Color;
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
import Model.Tile;
import Model.TileType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import Model.SysData;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

 public class GameController {
	Methods methods = new Methods();
	 
	//Sizes
    private final double IMAGE_SIZE = 45; // the icons next to the names
    // Easy Board Sizes
    private final double E_QUESTION_SIZE = 50; // question icon size
    private final double E_SURPRISE_SIZE = 50; // surprise icon size
    private final double E_RED_SNAKE_SIZE = 50; 
    private final double E_TILE_SIZE  = 76;
	private final double E_TOKEN_SIZE = 50;
	// Normal Board Sizes
    private final double N_QUESTION_SIZE = 30; // question icon size
    private final double N_SURPRISE_SIZE = 35; // surprise icon size
    private final double N_RED_SNAKE_SIZE = 40; 
    private final double N_TILE_SIZE  = 53.8;
	private final double N_TOKEN_SIZE = 35;
	// Hard Board Sizes
    private final double H_QUESTION_SIZE = 25; // question icon size
    private final double H_SURPRISE_SIZE = 25; // surprise icon size
    private final double H_RED_SNAKE_SIZE = 30; 
    private final double H_TILE_SIZE  = 42;
	private final double H_TOKEN_SIZE = 30;
	// Current Sizes: depends on the difficulty of the current board
	private double QUESTION_SIZE = 0;
	private double SURPRISE_SIZE = 0;
	private double RED_SNAKE_SIZE = 0;
	private double TILE_SIZE = 0;
	private double TOKEN_SIZE = 0;
    //Lists
    private HashMap<Player, Image> icons = new HashMap<>();
    private ArrayList<Player> originalOrder = new ArrayList<>();
    //Path
	private static final String GREEN = "/img/icons/greenPlayer.png"; //Player Token path
	private static final String BLUE = "/img/icons/bluePlayer.png"; //Player Token path
	private static final String PINK = "/img/icons/pinkPlayer.png"; //Player Token path
	private static final String RED = "/img/icons/redPlayer.png"; //Player Token path
	private static final String PURPLE = "/img/icons/purplePlayer.png"; //Player Token path
	private static final String YELLOW = "/img/icons/yellowPlayer.png";	//Player Token path
	private static final String ARROW = "/img/icons/arrow3.gif"; //Player's turn arrow path
	private static final String RED_SNAKE_IMAGE_PATH = "/img/icons/redSnake.png";
	private static final String YELLOW_SNAKE_IMAGE_PATH = "/img/icons/yellowSnake.png";
	private static final String BLUE_SNAKE_IMAGE_PATH = "/img/icons/blueSnake.png";
	private static final String GREEN_SNAKE_IMAGE_PATH = "/img/icons/greenSnake.png";
	private static final String QUESTION_IMAGE_PATH = "/img/icons/question.png";
	private static final String SURPRISE_IMAGE_PATH = "/img/icons/surprise.png";
	private static final String DEFAULT_SNAKE_IMAGE_PATH = null;
	private static final String LADDER_1_IMAGE_PATH = "/img/icons/ladder1.png";
	private static final String LADDER_2_IMAGE_PATH = "/img/icons/ladder2.png";
	private static final String LADDER_3_IMAGE_PATH = "/img/icons/ladder3.png";
	private static final String LADDER_4_IMAGE_PATH = "/img/icons/ladder4.png";
	private static final String LADDER_5_IMAGE_PATH = "/img/icons/ladder5.png";
	private static final String LADDER_6_IMAGE_PATH = "/img/icons/ladder6.png";
	private static final String LADDER_7_IMAGE_PATH = "/img/icons/ladder7.png";
	private static final String LADDER_8_IMAGE_PATH = "/img/icons/ladder8.png";
	private static final String LADDER_1B_IMAGE_PATH = "/img/icons/ladder1B.png";// Big ladder
	private static final String LADDER_2B_IMAGE_PATH = "/img/icons/ladder2B.png";// Big ladder
	private static final String LADDER_3B_IMAGE_PATH = "/img/icons/ladder3B.png";// Big ladder
	private static final String LADDER_4B_IMAGE_PATH = "/img/icons/ladder4B.png";// Big ladder
	private static final String LADDER_5B_IMAGE_PATH = "/img/icons/ladder5B.png";// Big ladder
	private static final String LADDER_6B_IMAGE_PATH = "/img/icons/ladder6B.png";// Big ladder
	private static final String LADDER_7B_IMAGE_PATH = "/img/icons/ladder7B.png";// Big ladder
	private static final String LADDER_8B_IMAGE_PATH = "/img/icons/ladder8B.png";// Big ladder
	private static final String DEFAULT_LADDER_IMAGE_PATH = null;
	private static final String SURPRISE_GIF_PATH =  "/img/icons/surpriseGIF.gif";
	private static final String SURPRISE_PLUS_PATH = "/img/icons/surprisePlus.png"; 
	private static final String SURPRISE_MINUS_PATH = "/img/icons/surpriseMinus.png"; 	

	//Fields
	int returnVal = 0; // returns the number of steps the player should move based on their answer
	private Duration gameDuration = Duration.ZERO;
	private static final int VISIBLE_DURATION_MS = 4500; // 4.5 seconds

	private ImageView surpriseValue;
	private ImageView surprise;
    private Board board;
    private GridPane grid;
    private Game game;
    private Label time; // shows game duration on screen
    private Timeline timer;
        
    private HBox playersStart;
    
    private HBox player1;
    private HBox player2;
    private HBox player3;
    private HBox player4;
	
    // Constructor
    public GameController(Game game, Board board, GridPane grid, HBox playersStart, Label time,
    		Timeline timer, HBox player1, HBox player2, HBox player3, HBox player4, ImageView surpriseValue, ImageView surprise) {
        this.game = game;
    	this.board = board;
        this.grid = grid;
        this.playersStart = playersStart;
        this.time = time;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.surpriseValue = surpriseValue;
        this.surprise = surprise;
        InitSizes();
    }
	private void InitSizes(){
		if(board.getbType()== Difficulty.Easy) {
			QUESTION_SIZE = E_QUESTION_SIZE;
			SURPRISE_SIZE = E_SURPRISE_SIZE;
			RED_SNAKE_SIZE = E_RED_SNAKE_SIZE;
			TILE_SIZE = E_TILE_SIZE;
			TOKEN_SIZE = E_TOKEN_SIZE;
		} else if(board.getbType()== Difficulty.Medium) {
			QUESTION_SIZE = N_QUESTION_SIZE;
			SURPRISE_SIZE = N_SURPRISE_SIZE;
			RED_SNAKE_SIZE = N_RED_SNAKE_SIZE;
			TILE_SIZE = N_TILE_SIZE;
			TOKEN_SIZE = N_TOKEN_SIZE;
		} else {
			QUESTION_SIZE = H_QUESTION_SIZE;
			SURPRISE_SIZE = H_SURPRISE_SIZE;
			RED_SNAKE_SIZE = H_RED_SNAKE_SIZE;
			TILE_SIZE = H_TILE_SIZE;
			TOKEN_SIZE = H_TOKEN_SIZE;
		}
	}
	
	public void startTimer() {
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
	
	// Method to stop the timer and return the duration of the game as a String
	private String stopTimer() {
	    timer.stop();
	    String durationGame = Game.formatDuration(gameDuration);
	    return durationGame;
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
				greenImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, greenPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Blue) {
				Image bluePlayer = new Image(getClass().getResource(BLUE).toExternalForm());
				ImageView blueImageView = new ImageView(bluePlayer);
				playerIcons.add(blueImageView);
				blueImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, bluePlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Pink) {
				Image pinkPlayer = new Image(getClass().getResource(PINK).toExternalForm());
				ImageView pinkImageView = new ImageView(pinkPlayer);
				playerIcons.add(pinkImageView);
				pinkImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, pinkPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Red) {
				Image redPlayer = new Image(getClass().getResource(RED).toExternalForm());
				ImageView redImageView = new ImageView(redPlayer);
				playerIcons.add(redImageView);
				redImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, redPlayer); // associate created image with the player
			}
			else if (p.getPlayerColor() == Color.Purple) {
				Image purplePlayer = new Image(getClass().getResource(PURPLE).toExternalForm());
				ImageView purpleImageView = new ImageView(purplePlayer);
				playerIcons.add(purpleImageView);
				purpleImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, purplePlayer); // associate created icon with the player
			}
			else if (p.getPlayerColor() == Color.Yellow) {
				Image yellowPlayer = new Image(getClass().getResource(YELLOW).toExternalForm());
				ImageView yellowImageView = new ImageView(yellowPlayer);
				playerIcons.add(yellowImageView);
				yellowImageView.setId(String.valueOf(p.getPlayerID())); // setting an index to the player icon in order to easily retrieve it later
				icons.put(p, yellowPlayer); // associate created icon with the player
			}
		}
		for (ImageView iv : playerIcons) { // the player icons that will move on the board
			iv.setFitHeight(TOKEN_SIZE);
			iv.setFitWidth(TOKEN_SIZE);
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
			icon.setFitHeight(IMAGE_SIZE);
			icon.setFitWidth(IMAGE_SIZE);
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
	
	public void win(int currentRow, int currentColumn, Player p, int newPosition) {
        displayPlayerToken(currentRow, currentColumn, p, newPosition); // display the winner at the last tile
		p.setPlayerPlace(newPosition);

        game.setWinner(p);
        game.setGameDuration(stopTimer());
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
        // Wait 2 seconds before loading the winner FXML
        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
        delay.setOnFinished(event -> {
        	methods.newScreen("Winner");
            System.out.println(p.getPlayerName() + " is the WINNER!");
        });
        delay.play();
	}
	
	void move(Player player, int steps) {	    
	    int currentPosition = player.getPlayerPlace();
	    int currentRow;
	    int currentColumn;
	    if (currentPosition > 0) {
	    	Tile pos = board.getTile(currentPosition);
		    currentRow = pos.getRow();
		    currentColumn = pos.getColumn();
	    }
	    else {
	    	currentRow = board.getBoardLen()-1;
	    	currentColumn = 0;
	    }
	    System.out.println("current player position: "+currentPosition);
	    if (steps != 0) {
		    
		    int nextPos = currentPosition + steps;
		    int newPosition = NextMove(currentRow, currentColumn, currentPosition, steps, player);
		    if (nextPos < board.getBoardSize() && nextPos >= 1) { // if nextPos is within the board boundaries, it could be a snake/ladder
		    	player.setPlayerPrevPlace(currentPosition);
		    	Tile nextTile = board.getTile(nextPos);
		    	// if the next tile is a snake head or ladder bottom,
		    	// move the player to that tile before moving them to the snake tail / ladder top
		    	if (nextTile.gettType().equals(TileType.LadderBottom) || nextTile.gettType().equals(TileType.SnakeHead)) {
		    		int newRow = nextTile.getRow();
				    int newColumn = nextTile.getColumn();
				    // move to the ladder bottom / snake head
				    Platform.runLater(() -> {
				    	displayPlayerToken(currentRow, currentColumn, player, nextPos);
			        	player.setPlayerPlace(nextPos);
				    });
				    
				    // Wait 2 seconds before climbing up the ladder or sliding down the snake
			        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
			        delay.setOnFinished(event -> {
			        	System.out.println("newRow is: "+newRow);
			        	System.out.println("newColumn is: "+newColumn);
					    displayPlayerToken(newRow, newColumn, player, newPosition);
					    player.setPlayerPlace(newPosition);
			        });
			        Platform.runLater(() -> {
			        	delay.play();
			        });
		    	}
		    	else if (nextTile.gettType().equals(TileType.Surprise)){
		    		int newRow = nextTile.getRow();
				    int newColumn = nextTile.getColumn();	
				    
				    Platform.runLater(() -> {
				    	displayPlayerToken(currentRow, currentColumn, player, nextPos);
			        	player.setPlayerPlace(nextPos);
				    });
				    
		    		// Wait 3 seconds before moving to the next tile
				    int surpriseSteps = handleSurpriseTileReached();
			        PauseTransition delay = new PauseTransition(Duration.seconds(5));
			        delay.setOnFinished(event -> {
			        	System.out.println("newRow is: "+newPosition+surpriseSteps);
			        	System.out.println("newColumn is: "+newPosition+surpriseSteps);
					    displayPlayerToken(newRow, newColumn, player, newPosition+surpriseSteps);
					    player.setPlayerPlace(newPosition+surpriseSteps);
			        });
			        Platform.runLater(() -> {
			        	delay.play();
			        });
		    	}
		    	else {
			    	// Set player's new position
		    		Platform.runLater(() -> {
		    			displayPlayerToken(currentRow, currentColumn, player, newPosition);
		    			player.setPlayerPlace(newPosition);
		    		});
			    }
		    }
		    else { // new position is outside the board --> 0
		    	// Set player's new position
	    		Platform.runLater(() -> {
	    			displayPlayerToken(currentRow, currentColumn, player, newPosition);
	    			player.setPlayerPlace(newPosition);
	    		});
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
	
	private ImageView getTokenFromGrid(Player player) {
		String id = String.valueOf(player.getPlayerID());
        for (javafx.scene.Node node : grid.getChildren()) {
            if (node instanceof ImageView && node.getId() != null && node.getId().equals(id)) {
            	return (ImageView) node;
            }
        }
        return null; // Return null if a token with the player ID is not found
    }
	
	private ImageView getTokenFromStart(Player player) {
		String id = String.valueOf(player.getPlayerID());
    	for (javafx.scene.Node node : playersStart.getChildren()) { // Look for token outside the grid
            if (node instanceof ImageView && node.getId() != null && node.getId().equals(id)) {
            	return (ImageView) node;
            }
        }
        return null; // Return null if a token with the player ID is not found
    }
	
	private void displayPlayerToken(int currentR, int currentC, Player player, int newPosition) {
		ImageView playerToken = null;
		if (player.getPlayerPlace() == 0) {
			playerToken = getTokenFromStart(player);
			System.out.println("player is moving from position 0");
		}
		else{
			playerToken = getTokenFromGrid(player);
			System.out.println("player is moving from a place in the grid");
		}

	    if (playerToken == null) {
	    	System.out.println("playerToken is NULL!");
	    }
	    if(newPosition!=0) { // new position is on the grid
	    	// If the token is not already in the grid, add it
		    if (getTokenFromGrid(player) == null) {
		        grid.getChildren().add(playerToken);
		    }
		    Tile pos = board.getTile(newPosition);
		    int row = pos.getRow();
		    int column = pos.getColumn();
		    playerToken.toFront();
		    moveTokenToCell(currentR, currentC, row, column, playerToken);
		    
		    // Check if other players are already on this tile to avoid covering each other's tokens
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
		    	GridPane.setHalignment(playerToken, javafx.geometry.HPos.CENTER); // Center horizontally
		    	GridPane.setValignment(playerToken, javafx.geometry.VPos.CENTER); // Center vertically
		    	player.setEnteredTile(1); // I entered in the first position
		    }
		    else if (!enteredTileOrder.contains(2)) { // if no other player is in position number 2 on the tile
		    	GridPane.setHalignment(playerToken, javafx.geometry.HPos.LEFT); // set player at the cell's left
		        GridPane.setValignment(playerToken, javafx.geometry.VPos.CENTER);
		        player.setEnteredTile(2); // I entered in the second position
		    }
		    else if (!enteredTileOrder.contains(3)) { // if no other player is in position number 3 on the tile
		    	GridPane.setHalignment(playerToken, javafx.geometry.HPos.RIGHT); // set player at the cell's right
	    		GridPane.setValignment(playerToken, javafx.geometry.VPos.CENTER);
	    		player.setEnteredTile(3); // I entered in the third position
		    }
		    else if (!enteredTileOrder.contains(4)) { // if no other player is in position number 4 on the tile
		    	GridPane.setHalignment(playerToken, javafx.geometry.HPos.CENTER);
	    		GridPane.setValignment(playerToken, javafx.geometry.VPos.BOTTOM); // set player at the cell's bottom
	    		player.setEnteredTile(4); // I entered in the fourth position
		    }
	    }
	    else { // if the new position is 0
	    	if (getTokenFromStart(player) == null) { // if the player is not outside the board
	    		playersStart.getChildren().add(playerToken); // place the player outside the board = position 0
	    		playerToken.setVisible(true);
	    		if (playerToken.isVisible()) {
	    			System.out.println("player "+player.getPlayerName()+" is visible");
	    		}
	    		playerToken.toFront();
	    		System.out.println("player "+player.getPlayerName()+" was added to the start HBox");
	    	}
	    }
	}
	
	private void moveTokenToCell(int currentRow, int currentColumn, int targetRow, int targetColumn, ImageView token) {
		double tileWidth = grid.getPrefWidth()/board.getBoardLen();
		double tileHeight = grid.getPrefHeight()/board.getBoardLen();

    	// Calculate the starting coordinates based on currentRow and currentColumn
        double startX = currentColumn * tileWidth;
        double startY = currentRow * tileHeight;

        // Calculate the ending coordinates based on targetRow and targetColumn
        double endX = targetColumn * tileWidth;
        double endY = targetRow * tileHeight;

        // Create TranslateTransition to move the ImageView to the target position
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), token);

        // Set the starting position
        translateTransition.setFromX(startX);
        translateTransition.setFromY(startY);

        // Set the ending position
        translateTransition.setToX(endX);
        translateTransition.setToY(endY);

        // Play the TranslateTransition
        translateTransition.play();
        System.out.println("currentRow in moveTokenToCell: "+currentRow);
        System.out.println("currentColumn in moveTokenToCell: "+currentColumn);
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
	            if (snake.getColor() == SnakeColor.Red) {
	                System.out.println("NextMove will be: 1");
	                return 1;
	            } else {
	                System.out.println("NextMove will be: " + snake.getSnakeTail());
	                return snake.getSnakeTail();
	            }
	            
	        case LadderBottom:
	            LadderTile ladderT = (LadderTile) nextTile;
	            Ladder ladder = ladderT.getLadder();
	            System.out.println("NextMove will be: " + ladder.getLadderTop());
	            return ladder.getLadderTop();
	            
	        case Surprise:
	            System.out.println("Yaaaay you got a gift!");
	            //int surpriseSteps = handleSurpriseTileReached();
	            //return nextPos+surpriseSteps;
	            
	            
	            int newPosition1 = nextPos;
	    	    p.setPlayerPrevPlace(currPosition);
	    	    // Set player's new position
	    	    displayPlayerToken(currentRow, currentColumn, p, newPosition1);
	    	    p.setPlayerPlace(newPosition1);
	    	    System.out.println("current player position on surprise tile: "+newPosition1);
	    	    // Wait 2 seconds before showing the question dialog
		        PauseTransition delay1 = new PauseTransition(Duration.seconds(5));
		        delay1.setOnFinished(event -> {
		        	int surpriseSteps = handleSurpriseTileReached();
		    		move(p, newPosition1+surpriseSteps);
		        });
		        Platform.runLater(() -> {
		        	delay1.play();
		        });
	            return p.getPlayerPlace();
	            
	        case Question:
	            System.out.println("I have a question for you");
	            int newPosition = nextPos;
	    	    p.setPlayerPrevPlace(currPosition);
	    	    // Set player's new position
	    	    displayPlayerToken(currentRow, currentColumn, p, newPosition);
	    	    p.setPlayerPlace(newPosition);
	    	    System.out.println("current player position on question tile: "+newPosition);
	    	    // Wait 2 seconds before showing the question dialog
		        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
		        delay.setOnFinished(event -> {
		        	QuestionTile qt = (QuestionTile) board.getTile(nextPos);
	    	    	int newSteps = showQuestionPopup(qt.getQuestionDiff());
		    		move(p, newSteps);
		        });
		        Platform.runLater(() -> {
		        	delay.play();
		        });
//	    	    Platform.runLater(() -> {
//	    	    	QuestionTile qt = (QuestionTile) board.getTile(nextPos);
//	    	    	int newSteps = showQuestionPopup(qt.getQuestionDiff());
//		    		move(p, newSteps);
//	    	    });
	            return p.getPlayerPlace();
	        default: // Handle the rest of the tile types which do not require special treatment
	        	System.out.println("Next step will be: " + nextPos);
	            return nextPos;
	    }
	}
	
    public int showQuestionPopup(Difficulty difficulty) { // view the question  dialog  and return the number of steps to move
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Question");
	    // Disable the close button
		dialog.initStyle(StageStyle.UNDECORATED);
		
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
		answerGroup.setUserData(null);
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
		submit.setOnMouseEntered(e -> methods.entered(e));
		submit.setOnMouseExited(e -> methods.exited(e));
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
		});
		returnVal = 0; // initialize the return value to a  default: 0
		submit.setOnAction(e -> {
			okButton.setDisable(false); // enable closing the dialog after the answer has been submitted
			RadioButton selectedAnswer = (RadioButton) answerGroup.getSelectedToggle(); // Get the selected answer
			if (selectedAnswer != null) {
		        submit.setDisable(true); // do not allow to submit again
		        for (Toggle toggle : answerGroup.getToggles()) {
		            RadioButton radioButton = (RadioButton) toggle;
		            radioButton.setDisable(true); // do not allow to change the answer
		        }
		    }
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
			// When text is bold, it is slightly enlarged. We want the dialog to fit the enlarged text without clipping it
			dialog.getDialogPane().getScene().getWindow().sizeToScene(); 
		});
      dialog.showAndWait();
      return returnVal;
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
    
    public int handleSurpriseTileReached() {
        // Show the surprise box and its images
        surpriseValue.setVisible(true);
        surprise.setVisible(true);

        // Call the function to display the surprise and return [-10/+10] steps for the player to move
        return displaySurprise();
    }
	
//	public static void viewSurpriseTile() {
//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.setTitle("Surprise");
//
//        StackPane stackPane = new StackPane();
//        Image backgroundImage = new Image("/img/screens/surpriseBackground.jpg");//set  the background
//        ImageView backgroundImageView = new ImageView(backgroundImage);
//
//        // Add the background image to the StackPane
//        stackPane.getChildren().add(backgroundImageView);
//
//        // Create a label for displaying random text
//        Label randomTextLabel = new Label();
//        randomTextLabel.setStyle("-fx-font-size: 18; -fx-text-fill: Black;");
//
//        String[] possibleTexts = {"10 steps forward !", "10 steps back!"};
//        Random random = new Random();
//        int surpriseResult=random.nextInt(possibleTexts.length);
//        String randomText = possibleTexts[surpriseResult];
//        randomTextLabel.setText(randomText);
//
//        stackPane.getChildren().add(randomTextLabel);
//        dialog.getDialogPane().setContent(stackPane);
//
//        // buttons, labels, or other components to the StackPane as needed
//
//        dialog.showAndWait();
//    }
 
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
			int row = qt.getRow();
	        int column = qt.getColumn();
	        showOneCellIcon(questionImage, row, column, QUESTION_SIZE);
		}
	}
	
	public void showSurprises() {
		for (Tile st : board.getSurpriseTiles()) {
			Image surpriseImage = new Image(getClass().getResource(SURPRISE_IMAGE_PATH).toExternalForm());
			int row = st.getRow();
	        int column = st.getColumn();
	        showOneCellIcon(surpriseImage, row, column, SURPRISE_SIZE);
		}
	}
	public void showSnakes() {
	    HashMap<SnakeColor, List<Snake>> snakesByColor = new HashMap<>();
	    for (HashMap.Entry<Integer, Snake> s : board.getSnakes().entrySet()) {
	        Snake snake = s.getValue();
	        if (!snakesByColor.containsKey(snake.getColor())) {
	            snakesByColor.put(snake.getColor(), new ArrayList<>());
	        }
	        snakesByColor.get(snake.getColor()).add(snake);
	    }

	    for (List<Snake> snakes : snakesByColor.values()) {
	        for (Snake snake : snakes) {
	            displaySnake(snake, getSnakeImagePath(snake));
	        }
	    }
	}

	private String getSnakeImagePath(Snake snake) {
	    // Add logic to determine the image path based on snake properties
	    // For example:
	    switch (snake.getColor()) {
	        case Red:
	            return RED_SNAKE_IMAGE_PATH;
	        case Yellow:
	            return YELLOW_SNAKE_IMAGE_PATH;
	        case Green:
	            return GREEN_SNAKE_IMAGE_PATH;
	        case Blue:
	            return BLUE_SNAKE_IMAGE_PATH;
	        default:
	            return DEFAULT_SNAKE_IMAGE_PATH; 
	    }
	}

	private void displaySnake(Snake snake, String imagePath) {
	    // Get the head and tail coordinates of the snake
	    int headTile = snake.getSnakeHead();
	    int tailTile = snake.getSnakeTail();
	    int xHead = board.getTile(headTile).getRow();
	    int yHead = board.getTile(headTile).getColumn();
	    int xTail = board.getTile(tailTile).getRow();
	    int yTail = board.getTile(tailTile).getColumn();

	    // Load snake image
	    Image snakeImage = new Image(getClass().getResource(imagePath).toExternalForm());

	    // If the snake color is red, set a fixed size for the image
	    if (snake.getColor() == SnakeColor.Red) {
	        showOneCellIcon(snakeImage, xHead, yHead, RED_SNAKE_SIZE);
	    } else {
	        
	        double slope = (double) (yTail - yHead) / (xTail - xHead);
	        double angle = Math.toDegrees(Math.atan(slope));

	        // Calculate the length of the snake
	        double snakeLength = Math.sqrt(Math.pow(xTail - xHead, 2) + Math.pow(yTail - yHead, 2)) * TILE_SIZE;

	        // Calculate the position of the snake
//	        int xPos = (xTail + xHead) / 2;
//	        int yPos = (yTail + yHead) / 2;

	        // Create ImageView for the snake image
	        ImageView snakeImageView = new ImageView(snakeImage);
	        snakeImageView.setFitWidth(2*TILE_SIZE); // Width remains TILE_SIZE
	        snakeImageView.setFitHeight(2*snakeLength+0.1*TILE_SIZE); // Set the height to match the calculated length
	        snakeImageView.setRotate(-angle); // Rotate the image to match the angle between head and tail

	    	if(xHead-xTail>0) {
		       snakeImageView.setScaleX(-1);
		       snakeImageView.setScaleY(-1);
	    	}
	        snakeImageView.setVisible(true);
	        GridPane.setRowIndex(snakeImageView, xHead);
	        GridPane.setColumnIndex(snakeImageView, yHead);
	        grid.getChildren().addAll(snakeImageView);
	        GridPane.setHalignment(snakeImageView, javafx.geometry.HPos.CENTER); // Center horizontally
	        GridPane.setValignment(snakeImageView, javafx.geometry.VPos.CENTER); // Center vertically
	    }
	}

	public void showLadders() {
	    HashMap<Integer, List<Ladder>> laddersBySize = new HashMap<>();
	    for (HashMap.Entry<Integer, Ladder> l : board.getLadders().entrySet()) {
	        Ladder ladder = l.getValue();
	        int ladderSize = ladder.getLadderLen();
	        if (!laddersBySize.containsKey(ladderSize)) {
	            laddersBySize.put(ladderSize, new ArrayList<>());
	        }
	        laddersBySize.get(ladderSize).add(ladder);
	    }

	    for (List<Ladder> ladders : laddersBySize.values()) {
	        for (Ladder ladder : ladders) {
	            displayLadder(ladder, getLadderImagePath(ladder),getBigLadderImagePath(ladder));
	        }
	    }
	}

	private String getLadderImagePath(Ladder ladder) {
	    switch (ladder.getLadderLen()) {
	        case 1:
	            return LADDER_1_IMAGE_PATH;
	        case 2:
	            return LADDER_2_IMAGE_PATH;
	        case 3:
	            return LADDER_3_IMAGE_PATH;
	        case 4:
	            return LADDER_4_IMAGE_PATH;
	        case 5:
	            return LADDER_5_IMAGE_PATH;
	        case 6:
	            return LADDER_6_IMAGE_PATH;
	        case 7:
	            return LADDER_7_IMAGE_PATH;
	        case 8:
	            return LADDER_8_IMAGE_PATH;
	        default:
	            return DEFAULT_LADDER_IMAGE_PATH;
	    }
	}
	
	private String getBigLadderImagePath(Ladder ladder) {
	    switch (ladder.getLadderLen()) {
	        case 1:
	            return LADDER_1B_IMAGE_PATH;
	        case 2:
	            return LADDER_2B_IMAGE_PATH;
	        case 3:
	            return LADDER_3B_IMAGE_PATH;
	        case 4:
	            return LADDER_4B_IMAGE_PATH;
	        case 5:
	            return LADDER_5B_IMAGE_PATH;
	        case 6:
	            return LADDER_6B_IMAGE_PATH;
	        case 7:
	            return LADDER_7B_IMAGE_PATH;
	        case 8:
	            return LADDER_8B_IMAGE_PATH;
	        default:
	            return DEFAULT_LADDER_IMAGE_PATH;
	    }
	}
	
	private void displayLadder(Ladder ladder, String imagePath, String BigimagePath) {
	    // Get the head and tail coordinates of the ladder
	    int upperTile = ladder.getLadderTop();
	    int bottomTile = ladder.getLadderBottom();
	    int xTop = board.getTile(upperTile).getRow();
	    int yTop = board.getTile(upperTile).getColumn();
	    int xBottom = board.getTile(bottomTile).getRow();
	    int yBottom = board.getTile(bottomTile).getColumn();
	    boolean bigLadder = false;
	    Image ladderImage = new Image(getClass().getResource(imagePath).toExternalForm());
	    
	    if(Math.abs(xTop-xBottom)>= (board.getBoardLen()/2) || 
	    		Math.abs(yTop-yBottom)>= (board.getBoardLen()/2)) {
		    ladderImage = new Image(getClass().getResource(BigimagePath).toExternalForm());
		    bigLadder = true;
	    }
	    
        double slope = (double) (yBottom - yTop) / (xBottom - xTop);
        double angle = Math.toDegrees(Math.atan(slope));

        // Calculate the length of the ladder
        double ladderLength = Math.sqrt(Math.pow(xBottom - xTop, 2) + Math.pow(yBottom - yTop, 2)) * TILE_SIZE;

        // Calculate the position of the ladder
//        int xPos = (int) (xBottom + xTop) / 2;
//        int yPos = (int) (yBottom + yTop) / 2;

        // Create ImageView for the ladder image
        ImageView ladderImageView = new ImageView(ladderImage);
        if(bigLadder) {
            ladderImageView.setFitWidth(2.2*TILE_SIZE); // Width remains TILE_SIZE
        } else {
            ladderImageView.setFitWidth(1.2*TILE_SIZE); // Width remains TILE_SIZE
        }
        ladderImageView.setFitHeight(2*ladderLength+0.1*TILE_SIZE); // Set the height to match the calculated length
        ladderImageView.setRotate(-angle); // Rotate the image to match the angle between head and tail

    	if(xTop-xBottom>0) {
    		ladderImageView.setScaleX(-1);
    		ladderImageView.setScaleY(-1);
    	}else {
	     // snakeImageView.setScaleY(-1);
    	}
        //snakeImageView.setScaleY(-1);
       // snakeImageView.setScaleX(-1);
    	ladderImageView.setVisible(true);
        GridPane.setRowIndex(ladderImageView, xTop);
        GridPane.setColumnIndex(ladderImageView, yTop);
        grid.getChildren().addAll(ladderImageView);
        GridPane.setHalignment(ladderImageView, javafx.geometry.HPos.CENTER); // Center horizontally
        GridPane.setValignment(ladderImageView, javafx.geometry.VPos.CENTER); // Center vertically
	}
	
	void showInfo() throws IOException{	
    	// Load the FXML file of the game rules
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Info.fxml"));
        try {
            AnchorPane anchorPane  = loader.load();
            StackPane dialogContent = new StackPane();
            // Add the AnchorPane to the StackPane which centers better in a dialog than an AnchorPane
            dialogContent.getChildren().add(anchorPane);
            // Get the controller associated with the FXML of the game rules
            infoController controller = loader.getController();
            
            // Disable the button with fx:id "homeButton"
            Button homeButton = controller.getHomeButton();
            homeButton.setDisable(true);
            homeButton.setOpacity(0);
            
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

}
