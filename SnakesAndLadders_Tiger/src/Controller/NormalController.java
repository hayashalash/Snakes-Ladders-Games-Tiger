package Controller;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import Model.Board;
import Model.Dice;
import Model.Difficulty;
import Model.Game;
import Model.Player;
import View.Alerts;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class NormalController extends BoardController implements Initializable  {
	
	public NormalController() {
	    // Initialization code, if needed
	}
	
    public NormalController(Difficulty bType) {
		super();
	}

	//Path
	private static final String DEFAULT_DICE_IMAGE_PATH = "/img/icons/dice.png";
	
	Methods methods = new Methods();
	ArrayList<Player> playersOutsideBoard = new ArrayList<>();
	boolean correct = false; // checks if answer is correct
    int returnVal = 0; // returns the number of steps the player should move based on their answer
	private GameController gameController;
	public static Game game;
	Board board = new Board(game.getDifficulty());
//	private boolean systemsTurn = false; // determines whether it is now the systems turn or not in games with the system
	
	private boolean gameWithSystem = false;
	
    private static final String DICE_SOUND_FILE = "/img/wavs/dice.wav";
    private static final String QUESTION_SOUND_FILE = "/img/wavs/questionSound.wav";
    
    private static MediaPlayer diceSoundPlayer;
    private static MediaPlayer questionSoundPlayer;


    static {       
        Media diceSound = new Media(Alerts.class.getResource(DICE_SOUND_FILE).toString());
        diceSoundPlayer = new MediaPlayer(diceSound);
        
        Media questionSound = new Media(Alerts.class.getResource(QUESTION_SOUND_FILE).toString());
        questionSoundPlayer = new MediaPlayer(questionSound);
    }
  
    public static void playDiceSound() {
    	diceSoundPlayer.stop(); // Stop the sound in case it's already playing
    	diceSoundPlayer.play();
    }
    
    public static void playQuestionSound() {
    	questionSoundPlayer.stop();
    	questionSoundPlayer.play();
    }
    
    @FXML
    private AnchorPane rootAnchorPane;
	
    @FXML
    private ImageView surpriseValue;
	
    @FXML
    private ImageView surprise;	

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

    @FXML
    private Button home;

    @FXML
    private Button info;
    
    @FXML
    private Button reset;
    
    @FXML
    private Button pause;
    
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
    
    @FXML
    private Button musicIcon;    
    
    
    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.turnOffOn(event, musicIcon);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playersStart.setAlignment(Pos.BASELINE_RIGHT);
		for (Player p : game.getPlayers()) { // check whether this is a game with friends or with the system
			if (p.isSystem)
				gameWithSystem = true;
		}
		gameController = new GameController(rootAnchorPane, game, board, grid, playersStart, time, timer, player1, player2, player3, player4, surpriseValue, surprise);
		Image defaultImage = new Image(getClass().getResource(DEFAULT_DICE_IMAGE_PATH).toExternalForm());
	    diceResult.setImage(defaultImage);
	    exitButton.toFront(); // Ensure the exit button is always in the front
		Tooltip r = new Tooltip("Game Rules");
        Tooltip.install(info, r);
        Tooltip pauseG = new Tooltip("Pause Game");
        Tooltip.install(pause, pauseG);
        Tooltip res = new Tooltip("Restart Game");
        Tooltip.install(reset, res);
    	if (Main.note.isPlaying()) {
    		musicIcon.setOpacity(1.0);
    	}
    	else {
    		musicIcon.setOpacity(0.5);
    	}
        
		if(!board.createBoard())
    	{
    		Alerts.warning("An error occured while creating the board!");
    		return;
    	}
		gameController.startTimer();
		if (!gameWithSystem) // if game is with friends
			Dice.RollingDiceStartingGame(game); // fills the queue randomly to determine the order of player turns
		else { // if game is one player VS. system
			// set player to be first
			Player system = null;
			Player player = null;
			for (Player p : game.getPlayers()) {
				if (p.isSystem)
					system = p;
				else
					player = p;
			}
			game.getPlayersOrder().add(player);
			game.getPlayersOrder().add(system);
		}
        gameController.showPlayers();
	    gameController.showLadders();
	    gameController.showSnakes();
	    gameController.showQuestions();
	    gameController.showSurprises();
	}
	
	@Override
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
    @FXML
    public void handleDiceClick(ActionEvent event) throws InterruptedException {
    	rollDice();
    }
	
	public void rollDice() {
		// Enable the button after animation completes
		playDiceSound();
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
            
            // After 3 seconds, reset the dice image to the default
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
            pauseTransition.setOnFinished(event1 -> onFinished(currentPlayer, lastDiceResult[0]));
            pauseTransition.play();
        });
        System.out.println("Dice result: "+lastDiceResult[0]);
        // Display the dice result

        timeline.play(); // Start the animation
	}
	
	@Override
	public void onFinished(Player currentPlayer, int lastResult) {
    	updateDiceImage(DEFAULT_DICE_IMAGE_PATH); // Reset dice image to original
    	diceButton.setDisable(false); // Enable the button after animation completes
        // Move the current player based on the dice result after animation completes
    	viewResultDice(currentPlayer, lastResult);
    	if (gameWithSystem) {
        	if (!currentPlayer.isSystem) { // if the current player is not the system
        		diceButton.setDisable(true); // don't allow the player to roll the dice as it is the system's turn
    			// Wait 5 seconds before starting the system's turn
		        PauseTransition delay = new PauseTransition(Duration.seconds(5));
		        delay.setOnFinished(event -> {
		        	rollDice(); // roll the dice automatically for the system player
		        });
		        Platform.runLater(() -> {
		        	delay.play();
		        });
        	}
        }
    }
	
	@Override
	public Player getNextPlayerToMove() {
        Player nextPlayer = game.getPlayersOrder().poll(); // Take the player out of the queue to start their turn
        game.getPlayersOrder().offer(nextPlayer); // Return the player to the end of the queue
        return nextPlayer;
    }
	
	@Override
	public void viewResultDice(Player currentPlayer, int diceResult) {
    	if(diceResult <= 6) {
    		gameController.move(currentPlayer, diceResult);
		}
    	else if(diceResult == 7 || diceResult == 8) {
    		//display easy question 
    		playQuestionSound();
    		Platform.runLater(() -> {
    			int steps = gameController.showQuestionPopup(Difficulty.Easy, currentPlayer.isSystem());
    			System.out.println("steps to move after question are: "+steps);
        		gameController.move(currentPlayer, steps);
    		});  		
//    		gameController.move(currentPlayer, 5); // TODO this is temporary for testing purposes, revert back when done
    	}
    	else if(diceResult == 9 || diceResult == 10) {
    		//display normal question 
    		playQuestionSound();
    		Platform.runLater(() -> {
    			int steps = gameController.showQuestionPopup(Difficulty.Medium, currentPlayer.isSystem());
    			System.out.println("steps to move after question are: "+steps);
        		gameController.move(currentPlayer, steps);
    		});           
//    		gameController.move(currentPlayer, 5); // TODO this is temporary for testing purposes, revert back when done
    	}
    	else if(diceResult == 11 || diceResult == 12) {
    		//display hard question 
    		playQuestionSound();
    		Platform.runLater(() -> {
    			int steps = gameController.showQuestionPopup(Difficulty.Hard, currentPlayer.isSystem());
    			System.out.println("steps to move after question are: "+steps);
        		gameController.move(currentPlayer, steps);
    		});
//    		gameController.move(currentPlayer, 5); // TODO this is temporary for testing purposes, revert back when done
        }
	}
	@Override
	public void updateDiceImage(String imagePath) {//update the dice image 
    	 Image image = new Image(getClass().getResource(imagePath).toExternalForm());
    	 diceResult.setImage(image);
    	 if (imagePath.equals(DEFAULT_DICE_IMAGE_PATH)) { // Add shadow only to the default image to show clickable
    		 diceResult.setStyle("-fx-effect:  dropshadow(one-pass-box , black , 8 , 0.0 , 2 , 4);");
    	 }
    	 else {
    		 diceResult.setStyle("-fx-effect:  dropshadow(one-pass-box , black , 8 , 0.0 , 0 , 0);");
    	 }
    }
   
	@Override
	@FXML
	public void resetBoard(ActionEvent event) throws IOException {
		if (Alerts.restartGame() == 1) {
			// Reset the game state through the GameController instance
		    if (gameController != null) {
		        gameController.resetGame();
		    }

		    // Create a new game instance
		    NormalController.game = new Game(Difficulty.Medium, NormalController.game.getPlayers(), LocalDate.now());

		    // Navigate to the hard board screen
		    methods.newScreen("normalBoard");
		}
	}

	@Override
	protected void entered() {
		// TODO Auto-generated method stub
		
	}
	@FXML
    void showInfo(ActionEvent event) throws IOException{
    	gameController.showInfo();
    }
	
	@FXML
    void pauseGame(ActionEvent event) {
		gameController.pauseGame();
    }

}