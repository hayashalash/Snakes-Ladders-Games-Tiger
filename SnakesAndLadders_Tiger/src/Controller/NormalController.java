package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.ResourceBundle;
import Model.Board;
import Model.Color;
import Model.Game;
import Model.Player;
import Model.Question;
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

public class NormalController implements Initializable{

	private final double ICON_WIDTH = 35; // the moving icons on the board
    private final double ICON_HEIGHT = 35;
    private final double IMAGE_WIDTH = 45; // the icons next to the names
    private final double IMAGE_HEIGHT = 45;
    private final double QUESTION_WIDTH = 30; // question icon size
    private final double QUESTION_HEIGHT = 30;
    private final double SURPRISE_WIDTH = 25; // surprise icon size
    private final double SURPRISE_HEIGHT = 25;
    private final double TILE_SIZE  = 53.8;
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
	private static final String SURPRISE_IMAGE_PATH = "/img/icons/surprise.png";
	private static final String DEFAULT_SNAKE_IMAGE_PATH = null;
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
		showSnakes();
		showQuestions();
		showSurprises();
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
	    // Convert tile indices to row and column indices for the head
//	    int headRow = (headTile - 1) / board.getBoardLen();
//	    int headColumn = (headTile - 1) % board.getBoardLen();
//
//	    // Convert tile indices to row and column indices for the tail
//	    int tailRow = (tailTile - 1) / board.getBoardLen();
//	    int tailColumn = (tailTile - 1) % board.getBoardLen();

	    // Load snake image
	    Image snakeImage = new Image(getClass().getResource(imagePath).toExternalForm());

	    // If the snake color is red, set a fixed size for the image
	    if (snake.getColor() == SnakeColor.Red) {
	        double width = TILE_SIZE - (0.2 * TILE_SIZE); // 20% reduction in width
	        double height = TILE_SIZE - (0.2 * TILE_SIZE); // 20% reduction in height

	        // Create ImageView for the red snake image
	        ImageView redSnakeImageView = new ImageView(snakeImage);
	        redSnakeImageView.setFitWidth(width);
	        redSnakeImageView.setFitHeight(height);
	        redSnakeImageView.setTranslateX(yHead * TILE_SIZE); // Position the image at the head tile
	        redSnakeImageView.setTranslateY(xHead * TILE_SIZE);

	        // Add the red snake image to the grid
	        grid.getChildren().add(redSnakeImageView);
	    } else {
	    // Calculate the angle between head and tail tiles
	    double angle = Math.toDegrees(Math.atan2(xTail - xHead, yTail - yHead));

	    // Adjust the rotation angle to ensure the head is always oriented upwards
	    if (angle < -90 || angle > 90) {
	        angle += 180; // Rotate by 180 degrees if the angle is in the lower half-plane
	    }

	    // Calculate the length of the snake image
	    double snakeLength = Math.sqrt(Math.pow(xTail - xHead, 2) + Math.pow(yTail - yHead, 2)) * TILE_SIZE;

	    // Create ImageView for the snake image
	    ImageView snakeImageView = new ImageView(snakeImage);
	    snakeImageView.setFitWidth(TILE_SIZE - (0.2 * TILE_SIZE));
	    snakeImageView.setFitHeight(snakeLength);
	    snakeImageView.setRotate(angle); // Rotate the image to match the angle between head and tail
	    snakeImageView.setTranslateX(yHead * TILE_SIZE); // Position the image at the head tile
	    snakeImageView.setTranslateY(xHead * TILE_SIZE);

	    // Add the snake image to the grid
	    grid.getChildren().add(snakeImageView);
	}
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
    
    @FXML
    void handleDiceClick(ActionEvent event) {
    	
    	
    }
    
}
