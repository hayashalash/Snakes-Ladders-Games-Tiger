package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import Model.Board;
import Model.Color;
import Model.Dice;
import Model.Difficulty;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.QuestionTile;
import Model.Snake;
import Model.SnakeColor;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class EasyController implements Initializable{

	private final double ICON_WIDTH = 35; // the moving icons on the board
    private final double ICON_HEIGHT = 35;
    private final double IMAGE_WIDTH = 45; // the icons next to the names
    private final double IMAGE_HEIGHT = 45;
    private final double QUESTION_WIDTH = 50; // question icon size
    private final double QUESTION_HEIGHT = 50;
    private final double RED_SNAKE_WIDTH = 50; // question icon size
    private final double RED_SNAKE_HEIGHT = 50;
    private final double YELLOW_SNAKE_WIDTH = 130; // question icon size
    private final double YELLOW_SNAKE_HEIGHT = 130;
    private final double BLUE_SNAKE_WIDTH = 200; // question icon size
    private final double BLUE_SNAKE_HEIGHT = 300;
    private final double GREEN_SNAKE_WIDTH = 200; // question icon size
    private final double GREEN_SNAKE_HEIGHT = 200;
    private final double TILE_SIZE  = 76;
    private HashMap<Player, Image> icons = new HashMap<>();
    private HashMap<Player, ImageView> iconsOnBoard = new HashMap<>();
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
	public static Game game;
	Board board = new Board(game.getType());
	public Player currentTurn;
	private static final String DEFAULT_DICE_IMAGE_PATH = "/img/icons/dice.png";

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
        diceImageMap.put(0, "/img/icons/diceroll0.jpg");
        diceImageMap.put(1, "/img/icons/diceroll1.jpg");
        diceImageMap.put(2, "/img/icons/diceroll2.jpg");
        diceImageMap.put(3, "/img/icons/diceroll3.jpg");
        diceImageMap.put(4, "/img/icons/diceroll4.jpg");
        diceImageMap.put(5, "/img/icons/dicerollQuestion.jpg");
        diceImageMap.put(6, "/img/icons/dicerollQuestion.jpg");
        diceImageMap.put(7, "/img/icons/dicerollQuestion.jpg");
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
		showSnakes();
		showQuestions();
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

	    for (Entry<SnakeColor, List<Snake>> entry : snakesByColor.entrySet()) {
	        SnakeColor color = entry.getKey();
	        List<Snake> snakes = entry.getValue();
	        for (Snake snake : snakes) {
	            switch (color) {
	                case Red:
	                    displayRedSnake(snake, RED_SNAKE_IMAGE_PATH, RED_SNAKE_HEIGHT, RED_SNAKE_WIDTH);	            
	                    break;
	                case Yellow:
	                    displayYellowSnake(snake, YELLOW_SNAKE_IMAGE_PATH, YELLOW_SNAKE_HEIGHT, YELLOW_SNAKE_WIDTH);
	                    break;
	                case Green:
	                    displayGreenSnake(snake, GREEN_SNAKE_IMAGE_PATH, GREEN_SNAKE_HEIGHT, GREEN_SNAKE_WIDTH);	            
	                    break;
	                case Blue:
	                    displayBlueSnake(snake, BLUE_SNAKE_IMAGE_PATH, BLUE_SNAKE_HEIGHT, BLUE_SNAKE_WIDTH);
	                    break;
	            }
	        }
	    }
	}
	private void displayRedSnake(Snake snake, String imagePath, double height, double width) {
	    Image snakeImage = new Image(getClass().getResource(imagePath).toExternalForm());

	    // Calculate the position of the snake's head using chooseRandomTile
	    int headTile = board.chooseRandomTile(1); // The red snake occupies one tile

	    // Convert tile index to row and column indices
	    int headRow = (headTile - 1) / 7; // Assuming each row has 7 tiles
	    int headColumn = (headTile - 1) % 7;

	    // Calculate the position within the grid to place the snake
	    double xPosition = headColumn * TILE_SIZE; 
	    double yPosition = headRow * TILE_SIZE; 

	    // Display the snake's head
	    ImageView snakeHead = new ImageView(snakeImage);
	    snakeHead.setFitHeight(height);
	    snakeHead.setFitWidth(width);
	    snakeHead.setVisible(true);
	    GridPane.setMargin(snakeHead, new Insets(yPosition, 0, 0, xPosition)); // Set margin to adjust position
	    grid.getChildren().add(snakeHead);
	}

	
	
	private void displayYellowSnake(Snake snake, String imagePath, double height, double width) {
	    Image snakeImage = new Image(getClass().getResource(imagePath).toExternalForm());

	    // Calculate the position of the snake's head using chooseRandomTile
	    int headTile = board.chooseRandomTile(4); // The yellow snake occupies four tiles

	    // Convert tile index to row and column indices
	    int headRow = (headTile - 1) / 7; // Assuming each row has 7 tiles
	    int headColumn = (headTile - 1) % 7;

	    // Check if head is at the first row or first column, adjust if necessary
	    if (headRow == 0) headRow++; // Ensure head is not in the first row
	    if (headColumn == 0) headColumn++; // Ensure head is not in the first column

	    // Calculate the position within the grid to place the snake's head
	    double xPosition = headColumn * TILE_SIZE + (TILE_SIZE - width); // 76 is the width of each tile
	    double yPosition = headRow * TILE_SIZE; // 76 is the height of each tile

	    // Display the snake's head
	    ImageView snakeHead = new ImageView(snakeImage);
	    snakeHead.setFitHeight(height);
	    snakeHead.setFitWidth(width);
	    snakeHead.setVisible(true);
	    GridPane.setMargin(snakeHead, new Insets(yPosition, 0, 0, xPosition)); // Set margin to adjust position
	    grid.getChildren().add(snakeHead);
	}
	
	private void displayBlueSnake(Snake snake, String imagePath, double height, double width) {
	    Image snakeImage = new Image(getClass().getResource(imagePath).toExternalForm());

	    // Calculate the position of the snake's head using chooseRandomTile
	    int headTile = board.chooseRandomTile(4); // The blue snake occupies four tiles

	    // Convert tile index to row and column indices
	    int headRow = (headTile - 1) / 7; // Assuming each row has 7 tiles
	    int headColumn = (headTile - 1) % 7;

	    // Check if head is at the bottom three rows, adjust if necessary
	    if (headRow >= 4) headRow = 3; // Ensure head is not in the bottom three rows

	    // Calculate the position within the grid to place the snake's head
	    double xPosition = headColumn * TILE_SIZE; 
	    double yPosition = headRow * TILE_SIZE; 

	    // Display the snake's head
	    ImageView snakeHead = new ImageView(snakeImage);
	    snakeHead.setFitHeight(height);
	    snakeHead.setFitWidth(width);
	    snakeHead.setVisible(true);
	    GridPane.setMargin(snakeHead, new Insets(yPosition, 0, 0, xPosition)); // Set margin to adjust position
	    grid.getChildren().add(snakeHead);
	}

	private void displayGreenSnake(Snake snake, String imagePath, double height, double width) {
	    Image snakeImage = new Image(getClass().getResource(imagePath).toExternalForm());

	    // Calculate the position of the snake's head using chooseRandomTile
	    int headTile = board.chooseRandomTile(1); // The green snake occupies one tile for its head

	    // Convert tile index to row and column indices
	    int headRow = (headTile - 1) / 7; // Assuming each row has 7 tiles
	    int headColumn = (headTile - 1) % 7;

	    // Check if head is in the first two rows or columns, adjust if necessary
	    if (headRow <= 1) headRow = 2; // Ensure head is not in the first two rows
	    if (headColumn <= 1) headColumn = 2; // Ensure head is not in the first two columns

	    // Calculate the position within the grid to place the snake's head
	    double xPosition = headColumn * TILE_SIZE; 
	    double yPosition = headRow * TILE_SIZE; 

	    // Display the snake's head
	    ImageView snakeHead = new ImageView(snakeImage);
	    snakeHead.setFitHeight(height);
	    snakeHead.setFitWidth(width);
	    snakeHead.setVisible(true);
	    GridPane.setMargin(snakeHead, new Insets(yPosition, 0, 0, xPosition)); // Set margin to adjust position
	    grid.getChildren().add(snakeHead);
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
		viewResultDise(lastDiceResult);
    	  timeline.play();//animation
    	}

    
    

    private static void viewResultDise(int diceResult){//this for easy  difficulty only
    	if(diceResult<4) {
    		// function to move the player 
		}
    	if(diceResult==5) {//display easy question
    	//	GameController.showQuestionPopup(Difficulty.Easy);
    	}
    	else { if(diceResult==6) {//display normal question 
    		
    	}
    	  else if(diceResult==7) {//display hard question 
    		
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
	
public static void movePlayer(int n) {
     
    }
 
}

