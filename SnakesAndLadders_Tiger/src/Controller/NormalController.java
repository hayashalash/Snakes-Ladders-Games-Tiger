package Controller;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private Button update;
    
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
		gameController = new GameController(game, board, grid, playersStart, time, timer, player1, player2, player3, player4, surpriseValue, surprise);
		Image defaultImage = new Image(getClass().getResource(DEFAULT_DICE_IMAGE_PATH).toExternalForm());
	    diceResult.setImage(defaultImage);
	    exitButton.toFront(); // Ensure the exit button is always in the front
		Tooltip r = new Tooltip("Game Rules");
        Tooltip.install(info, r);
        
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
        Dice.RollingDiceStartingGame(game); // fills the queue randomly to determine the order of player turns
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
    }
	
	@Override
	public Player getNextPlayerToMove() {
        Player nextPlayer = game.getPlayersOrder().poll(); // Take the player out of the queue to start their turn
        game.getPlayersOrder().offer(nextPlayer); // Return the player to the end of the queue
        return nextPlayer;
    }
	
	@Override
	public void viewResultDice(Player currentPlayer,int diceResult) {//this for easy difficulty only
    	if(diceResult <= 6) {
    		gameController.move(currentPlayer, diceResult);
		}
    	else if(diceResult == 7 || diceResult == 8) {
    		//display easy question 
    		Platform.runLater(() -> {
    			int steps = gameController.showQuestionPopup(Difficulty.Easy);
    			System.out.println("steps to move after question are: "+steps);
        		gameController.move(currentPlayer, steps); 
    		});
//    		gameController.move(currentPlayer, 50); // TODO this is temporary for testing purposes, revert back when done
    	}
    	else if(diceResult == 9 || diceResult == 10) {
    		//display normal question 
    		Platform.runLater(() -> {
    			int steps = gameController.showQuestionPopup(Difficulty.Medium);
    			System.out.println("steps to move after question are: "+steps);
        		gameController.move(currentPlayer, steps); 
    		});
//    		gameController.move(currentPlayer, 50); // TODO this is temporary for testing purposes, revert back when done
    	}
    	else if(diceResult == 11 || diceResult == 12) {
    		//display hard question 	
    		Platform.runLater(() -> {
    			int steps = gameController.showQuestionPopup(Difficulty.Hard);
    			System.out.println("steps to move after question are: "+steps);
        		gameController.move(currentPlayer, steps); 
    		});
//    		gameController.move(currentPlayer, 50); // TODO this is temporary for testing purposes, revert back when done
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
	public void updateBoard(ActionEvent event) throws IOException {
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


}