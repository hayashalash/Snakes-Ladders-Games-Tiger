package Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import Model.Board;
import Model.Color;
import Model.Dice;
import Model.Difficulty;
import Model.Game;
import Model.Ladder;
import Model.Player;
import Model.Question;
import Model.QuestionTile;
import Model.Snake;
import Model.SnakeColor;
import Model.Tile;
import View.Alerts;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.shape.Line;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class NormalController implements Initializable{

	private final double ICON_WIDTH = 35; // the moving icons on the board
    private final double ICON_HEIGHT = 35;
    private final double IMAGE_WIDTH = 45; // the icons next to the names
    private final double IMAGE_HEIGHT = 45;
    private final double QUESTION_WIDTH = 30; // question icon size
    private final double QUESTION_HEIGHT = 30;
    private final double SURPRISE_WIDTH = 35; // surprise icon size
    private final double SURPRISE_HEIGHT = 35;
    private final double RED_SNAKE_WIDTH = 40; 
    private final double RED_SNAKE_HEIGHT = 40;
    private final double TILE_SIZE  = 53.8;
    private HashMap<Player, Image> icons = new HashMap<>();
    private HashMap<Player, ImageView> iconsOnBoard = new HashMap<>();
    private HashMap<Player, Integer> currentPosition;
	private static final String GREEN = "/img/icons/greenPlayer.png";
	private static final String BLUE = "/img/icons/bluePlayer.png";
	private static final String PINK = "/img/icons/pinkPlayer.png";
	private static final String RED = "/img/icons/redPlayer.png";
	private static final String PURPLE = "/img/icons/purplePlayer.png";
	private static final String YELLOW = "/img/icons/yellowPlayer.png";
	
	private static final String INFO_IMAGE_PATH = "/img/screens/infoBack.jpg";
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
	//private static final String LADDER_7_IMAGE_PATH = "/img/icons/ladder7.png";
	//private static final String LADDER_8_IMAGE_PATH = "/img/icons/ladder8.png";
	private static final String TOKEN_IMAGE_PATH = "/img/icons/greenPlayer.png";
	private static final double TOKEN_WIDTH = 35;
	private static final double TOKEN_HEIGHT = 35;
	private static final String DEFAULT_DICE_IMAGE_PATH = "/img/icons/dice.png";
	
	private int iterationCount = 0;
	private Timeline timeline = null;
	
	public static Game game;
	Board board = new Board(game.getType());
	public Player currentTurn;
	
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
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
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
		showPlayers();
		showLadders();
		showSnakes();
		showQuestions();
		showSurprises();
		ensureExitButtonOnTop();
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
			iv.setFitHeight(ICON_HEIGHT);
			iv.setFitWidth(ICON_WIDTH);
			playersStart.getChildren().add(iv);
		}
		
		for (Player p : game.getPlayers()) {
			Label name = new Label(p.getPlayerName());
			name.setStyle("-fx-font-family: Serif; -fx-font-size: 20px;");
			ImageView icon = new ImageView(icons.get(p));
			icon.setFitHeight(IMAGE_HEIGHT);
			icon.setFitWidth(IMAGE_WIDTH);
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
	    int xHead = board.getTile(headTile).getxCoord();
	    int yHead = board.getTile(headTile).getyCoord();
	    int xTail = board.getTile(tailTile).getxCoord();
	    int yTail = board.getTile(tailTile).getyCoord();

	    // Load snake image
	    Image snakeImage = new Image(getClass().getResource(imagePath).toExternalForm());

	    // If the snake color is red, set a fixed size for the image
	    if (snake.getColor() == SnakeColor.Red) {
	        // Create ImageView for the question
	        ImageView redSnakeImageView = new ImageView(snakeImage);
	        redSnakeImageView.setFitHeight(RED_SNAKE_WIDTH);
	        redSnakeImageView.setFitWidth(RED_SNAKE_HEIGHT);
	        redSnakeImageView.setVisible(true);
	        GridPane.setRowIndex(redSnakeImageView, xHead);
	        GridPane.setColumnIndex(redSnakeImageView, yHead);
	        grid.getChildren().addAll(redSnakeImageView);
	        GridPane.setHalignment(redSnakeImageView, javafx.geometry.HPos.CENTER); // Center horizontally
	        GridPane.setValignment(redSnakeImageView, javafx.geometry.VPos.CENTER); // Center vertically
	    } else {
	        
	        double slope = (double) (yTail - yHead) / (xTail - xHead);
	        double angle = Math.toDegrees(Math.atan(slope));

	        // Calculate the length of the snake
	        double snakeLength = Math.sqrt(Math.pow(xTail - xHead, 2) + Math.pow(yTail - yHead, 2)) * TILE_SIZE;

	        // Calculate the position of the snake
	        int xPos = (xTail + xHead) / 2;
	        int yPos = (yTail + yHead) / 2;

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
	       // case 7:
	        //    return LADDER_7_IMAGE_PATH;
	       // case 8:
	       //     return LADDER_8_IMAGE_PATH;
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
	       // case 7:
	        //    return LADDER_7B_IMAGE_PATH;
	       // case 8:
	       //     return LADDER_8B_IMAGE_PATH;
	        default:
	            return DEFAULT_LADDER_IMAGE_PATH;
	    }
	}
	
	private void displayLadder(Ladder ladder, String imagePath, String BigimagePath) {
	    // Get the head and tail coordinates of the ladder
	    int upperTile = ladder.getLadderTop();
	    int bottomTile = ladder.getLadderBottom();
	    int xTop = board.getTile(upperTile).getxCoord();
	    int yTop = board.getTile(upperTile).getyCoord();
	    int xBottom = board.getTile(bottomTile).getxCoord();
	    int yBottom = board.getTile(bottomTile).getyCoord();
	    boolean bigLadder = false;
	    Image ladderImage = new Image(getClass().getResource(imagePath).toExternalForm());
	    
	    if(Math.abs(xTop-xBottom)>= (board.getBoardLen()/2) || 
	    		Math.abs(yTop-yBottom)>= (board.getBoardLen()/2)) {
		    ladderImage = new Image(getClass().getResource(BigimagePath).toExternalForm());
		    bigLadder = true;
	    }
	    
        double slope = (double) (yBottom - yTop) / (xBottom - xTop);
        double angle = Math.toDegrees(Math.atan(slope));

        // Calculate the length of the snake
        double ladderLength = Math.sqrt(Math.pow(xBottom - xTop, 2) + Math.pow(yBottom - yTop, 2)) * TILE_SIZE;

        // Calculate the position of the snake
        int xPos = (int) (xBottom + xTop) / 2;
        int yPos = (int) (yBottom + yTop) / 2;
        


        // Create ImageView for the snake image
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


	
	public void showQuestions() {
		for (QuestionTile qt : board.getQuestionTiles()) {
			Image questionImage = new Image(getClass().getResource(QUESTION_IMAGE_PATH).toExternalForm());
			int row = qt.getxCoord();
	        int column = qt.getyCoord();

	        // Create ImageView for the question
	        ImageView question = new ImageView(questionImage);
	        question.setFitHeight(QUESTION_WIDTH);
	        question.setFitWidth(QUESTION_HEIGHT);
	        question.setVisible(true);
	        GridPane.setRowIndex(question, row);
	        GridPane.setColumnIndex(question, column);
	        // Add the question to GridPane
	        grid.getChildren().addAll(question);
	        GridPane.setHalignment(question, javafx.geometry.HPos.CENTER); // Center horizontally
	        GridPane.setValignment(question, javafx.geometry.VPos.CENTER); // Center vertically
		}
	}
	

	public void showSurprises() {
		for (Tile st : board.getSurpriseTiles()) {
			Image surpriseImage = new Image(getClass().getResource(SURPRISE_IMAGE_PATH).toExternalForm());
			int row = st.getxCoord();
	        int column = st.getyCoord();

	        // Create ImageView the surprise
	        ImageView surprise = new ImageView(surpriseImage);
	        surprise.setFitHeight(SURPRISE_WIDTH);
	        surprise.setFitWidth(SURPRISE_HEIGHT);
	        surprise.setVisible(true);
	        GridPane.setRowIndex(surprise, row);
	        GridPane.setColumnIndex(surprise, column);
	        // Add the surprise to GridPane
	        grid.getChildren().add(surprise);
	        GridPane.setHalignment(surprise, javafx.geometry.HPos.CENTER); // Center horizontally
	        GridPane.setValignment(surprise, javafx.geometry.VPos.CENTER); // Center vertically
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
    	((Node) event.getSource()).setCursor(Cursor.HAND);
    }
	
    @FXML
    void exited(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1);
    	((Node)event.getSource()).setScaleY(1);
    	((Node) event.getSource()).setCursor(Cursor.DEFAULT);
    }
    
    @FXML
    void showInfo(ActionEvent event) throws IOException{
    	Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Game Rules");
        dialog.setHeaderText("");

        Image info = new Image(getClass().getResource(INFO_IMAGE_PATH).toExternalForm());
		ImageView infoImageView = new ImageView(info);
		infoImageView.setFitHeight(500);
		infoImageView.setFitWidth(700);
		infoImageView.setVisible(true);
        dialog.getDialogPane().setContent(infoImageView);

        ButtonType closeButton = new ButtonType("Close", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);

        dialog.showAndWait();
    }
    
    @FXML
    void handleDiceClick(ActionEvent event) throws InterruptedException {	
    	 initializeMap();
    	 int lastDiceResult=0;
    	 Duration duration = Duration.millis(1000);
    	 int numFrames = 20;
    	 Duration frameInterval = duration.divide(numFrames);
    	 Timeline timeline = new Timeline();
    	 // Add keyframes to the timeline
    	    for (int i = 0; i < numFrames; i++) {
    	        int result = Dice.RandomNumberGenerator(Difficulty.Easy);
    	        lastDiceResult = result; // Save the result
    	        String imagePath = diceImageMap.get(result);

    	        // Create a keyframe for each image of the dice
    	        KeyFrame keyFrame = new KeyFrame(frameInterval.multiply(i),
    	                e -> updateDiceImage(imagePath));

    	        timeline.getKeyFrames().add(keyFrame);
    	    }
    	    
    	  timeline.setCycleCount(1); // Set the time line to  one
    	  timeline.setOnFinished(e -> {
    	    	 // After 10 seconds, reset the dice image to the default
    	   PauseTransition pauseTransition = new PauseTransition(Duration.seconds(10));
    	    pauseTransition.setOnFinished(event1 -> updateDiceImage(DEFAULT_DICE_IMAGE_PATH));
    	    pauseTransition.play();    	   
    	  });
		viewResultDice(lastDiceResult);
    	  timeline.play();//animation
    	}

    
    
    private void viewResultDice(int diceResult) {//this for easy difficulty only
    	if(diceResult <= 6) {
    		move(diceResult); 
		}
    	if(diceResult == 7) {//display easy question
    		
    	}
    	else { if(diceResult == 8) {//display normal question 
    		
    	}
    	  else if(diceResult == 9) {//display hard question 
    		
    	  }
    	}
		
	}

	private void updateDiceImage(String imagePath) {//update the dice image 
    	 Image image = new Image(getClass().getResource(imagePath).toExternalForm());
    	 diceResult.setImage(image);
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

    
	void move(int steps) {

	        // Now you can use this HashMap for further processing
	        
	        for (Player p : game.getPlayers()) {
	        	System.out.println(p.toString());
	            int currentPosition = p.getPlayerPlace();
	            p.setPlayerPrevPlace(currentPosition);
	            hidePlayerToken(p);
	            int newPosition = currentPosition + steps;
	            // Check if newPosition exceeds 100
	            if (newPosition > 100) {
	                continue; // Skip this player
	            }
	            
	            // Set player's new position
	            p.setPlayerPlace(newPosition);
	            displayPlayerToken(p,newPosition);
	            
	            // Check if player reaches 100
	            if (newPosition == 100) {
		            p.setPlayerPlace(newPosition);
		            displayPlayerToken(p,newPosition);
	            	game.setWinner(p);
	                System.out.println(p.getPlayerName() + " is the WINNER!");
	            }
	        }
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

	
	private void displayPlayerToken(Player player, int newPosition) {
	    Image tokenImage = new Image(getClass().getResource(getTokenImagePath(player)).toExternalForm());
	    ImageView token = iconsOnBoard.get(player);
	    if (token == null) {
	        token = new ImageView(tokenImage);
	        iconsOnBoard.put(player, token);
	    }
	    token.setFitHeight(TOKEN_WIDTH);
	    token.setFitWidth(TOKEN_HEIGHT);
	    token.setVisible(true);
	    
	    Tile pos = board.getTile(newPosition);
	    int row = pos.getxCoord();
	    int column = pos.getyCoord();
	    
	    GridPane.setRowIndex(token, row);
	    GridPane.setColumnIndex(token, column);
	    
	    // If the token is not already in the grid, add it
	    if (!grid.getChildren().contains(token)) {
	        grid.getChildren().add(token);
	    }
	}

	private void hidePlayerToken(Player p) {
	    ImageView token = iconsOnBoard.get(p);
	    if (token != null) {
	        grid.getChildren().remove(token);
	    }
	}
	
	private void ensureExitButtonOnTop() {
	    rootAnchorPane.getChildren().remove(exitButton); // Remove exitButton from AnchorPane
	    rootAnchorPane.getChildren().add(exitButton);    // Re-add exitButton to AnchorPane (on top)
	}

    
}

