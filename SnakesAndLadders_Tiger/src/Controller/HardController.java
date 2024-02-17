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
    private final double RED_SNAKE_SIZE = 35; // question icon size
    private final double TILE_HEIGHT  = 41;
    private final double TILE_WIDTH  = 42;
    private final double BOARD_LEFT_OFFSET  = 220;
    private final double BOARD_TOP_OFFSET  = 27;
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
	private static final String LADDER_IMAGE_PATH = "/img/icons/ladder.png";
	private static final String LONGLADDER_IMAGE_PATH = "/img/icons/longLadder.png";
	private static final String DEFAULT_SNAKE_IMAGE_PATH = null;
	public static Game game;
	Board board = new Board(game.getType());
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
			showSnakes();
			showLadders();
			showQuestions();
			showSurprises();

			getCellWidth(4);
			getCellHeight(4);
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
	    // Get the head and tail cells of the snake
	    int headTile = snake.getSnakeHead();
	    int tailTile = snake.getSnakeTail();
	    // Convert tile indices to row and column indices for the head and tail
	    int rowHead = board.getTile(headTile).getxCoord();
	    int colHead = board.getTile(headTile).getyCoord();
	    int rowTail = board.getTile(tailTile).getxCoord();
	    int colTail = board.getTile(tailTile).getyCoord();
	    // Load snake image
	    Image snakeImage = new Image(getClass().getResource(imagePath).toExternalForm());

	    if (snake.getColor() == SnakeColor.Red) {
	    	showOneCellIcon(snakeImage, rowHead, colHead, RED_SNAKE_SIZE);
	    } else {
	    	// Calculate the snake length using Euclidean distance formula
	    	double width = TILE_WIDTH * (Math.abs(colHead-colTail)); // base of the triangle
	    	double height = TILE_HEIGHT * (Math.abs(rowHead-rowTail)); // height of the triangle
	        double diagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)); // length of the snake
	        
	        ImageView snakeImageView = new ImageView(snakeImage);
	        
	        // for proportional sizes - adjust a larger width to a longer snake
	        if ((Math.abs(colHead-colTail) >= 4)) // if snake is very long
	        		snakeImageView.setFitWidth(TILE_WIDTH); // set large width
	        else // if snake is not very long
	        	snakeImageView.setFitWidth(0.7 * TILE_WIDTH); // set smaller width
	        
	        snakeImageView.setFitHeight(diagonal);
	        
	        double angleRadians = Math.atan(width / height); // calculate the angle between the diagonal and the base in radians

	        double angleDegrees = Math.toDegrees(angleRadians);	// convert the angle from radians to degrees

	        if (colHead < colTail)  // means the image should rotate to the left
	        	angleDegrees = (-1) * angleDegrees; // Flip the image horizontally
	        
	        snakeImageView.setRotate(angleDegrees); // Rotate the image to match the angle

	        StackPane stackPane = new StackPane();
//	        stackPane.setAlignment(Pos.CENTER);

	        // Add the ImageView to the StackPane
	        stackPane.getChildren().add(snakeImageView);
	        stackPane.setPrefSize(width+TILE_WIDTH, height+TILE_HEIGHT);
	        stackPane.setMaxSize(width+TILE_WIDTH, height+TILE_HEIGHT);
	        stackPane.setMinSize(width+TILE_WIDTH, height+TILE_HEIGHT);

	        double xLayout;
	        if (colHead < colTail) { // set position on the board based on the left side
	        	xLayout = BOARD_LEFT_OFFSET + (colHead*TILE_WIDTH);
	        }
	        else
	        	xLayout = BOARD_LEFT_OFFSET + (colTail*TILE_WIDTH);
	        double yLayout = BOARD_TOP_OFFSET + (rowHead*TILE_HEIGHT);
	        screen.getChildren().add(stackPane);
	        stackPane.setLayoutX(xLayout);
	        stackPane.setLayoutY(yLayout);
//	        stackPane.setStyle("-fx-border-color: black;");	        
	    }
	}
	public void showLadders() {
		for (HashMap.Entry<Integer, Ladder> l : board.getLadders().entrySet())
			displayLadder(l.getValue());
	}
	public void displayLadder(Ladder ladder) {
		// Get the top and bottom cells of the ladder
	    int topTile = ladder.getLadderTop();
	    int bottomTile = ladder.getLadderBottom();
	    // Convert tile indices to row and column indices for the top and bottom
	    int rowHead = board.getTile(topTile).getxCoord();
	    int colHead = board.getTile(topTile).getyCoord();
	    int rowTail = board.getTile(bottomTile).getxCoord();
	    int colTail = board.getTile(bottomTile).getyCoord();

    	// Calculate the ladder length using Euclidean distance formula
    	double width = TILE_WIDTH * (Math.abs(colHead-colTail)); // base of the triangle
    	double height = TILE_HEIGHT * (Math.abs(rowHead-rowTail)); // height of the triangle
        double diagonal = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)); // length of the ladder
        Image ladderImage;
        if ((Math.abs(colHead-colTail) >= 5) || (Math.abs(rowHead-rowTail) >= 5)) {
        	ladderImage = new Image(getClass().getResource(LONGLADDER_IMAGE_PATH).toExternalForm());
        }
        else {
    	    ladderImage = new Image(getClass().getResource(LADDER_IMAGE_PATH).toExternalForm());
        } 
        // Load ladder image
        ImageView ladderImageView = new ImageView(ladderImage);
        
        // for proportional sizes - adjust a larger width to a longer ladder
        if ((Math.abs(colHead-colTail) >= 5)) // if ladder is very long
        	ladderImageView.setFitWidth(2*TILE_WIDTH); // set large width
        else // if ladder is not very long
        	ladderImageView.setFitWidth(1.5 * TILE_WIDTH); // set smaller width
        
        ladderImageView.setFitHeight(diagonal);
        
        double angleRadians = Math.atan(width / height); // calculate the angle between the diagonal and the base in radians

        double angleDegrees = Math.toDegrees(angleRadians);	// convert the angle from radians to degrees

        if (colHead < colTail)  // means the image should rotate to the left
        	angleDegrees = (-1) * angleDegrees; // Flip the image horizontally
        
        ladderImageView.setRotate(angleDegrees); // Rotate the image to match the angle

        StackPane stackPane = new StackPane();
//	        stackPane.setAlignment(Pos.CENTER);

        // Add the ImageView to the StackPane
        stackPane.getChildren().add(ladderImageView);
        stackPane.setPrefSize(width+TILE_WIDTH, height+TILE_HEIGHT);
        stackPane.setMaxSize(width+TILE_WIDTH, height+TILE_HEIGHT);
        stackPane.setMinSize(width+TILE_WIDTH, height+TILE_HEIGHT);

        double xLayout;
        if (colHead < colTail) { // set position on the board based on the left side
        	xLayout = BOARD_LEFT_OFFSET + (colHead*TILE_WIDTH);
        }
        else
        	xLayout = BOARD_LEFT_OFFSET + (colTail*TILE_WIDTH);
        double yLayout = BOARD_TOP_OFFSET + (rowHead*TILE_HEIGHT);
        screen.getChildren().add(stackPane);
        stackPane.setLayoutX(xLayout);
        stackPane.setLayoutY(yLayout);
	}
	
	public double getCellHeight(int rowIndex) {
        double cellHeight = grid.getRowConstraints().get(rowIndex).getPrefHeight();
        
        System.out.println("Cell height: " + cellHeight);
        return cellHeight;
	}
	public double getCellWidth(int colIndex) {
        double cellWidth = grid.getColumnConstraints().get(colIndex).getPrefWidth();
        
        System.out.println("Cell width: " + cellWidth);
        return cellWidth;
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
}
