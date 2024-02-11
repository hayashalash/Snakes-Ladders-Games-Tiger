package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import Model.Board;
import Model.Color;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.QuestionTile;
import Model.Snake;
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

public class EasyController implements Initializable{

	private final double ICON_WIDTH = 35; // the moving icons on the board
    private final double ICON_HEIGHT = 35;
    private final double IMAGE_WIDTH = 45; // the icons next to the names
    private final double IMAGE_HEIGHT = 45;
    private final double QUESTION_WIDTH = 50; // question icon size
    private final double QUESTION_HEIGHT = 50;
    private HashMap<Player, Image> icons = new HashMap<>();
    private HashMap<Player, ImageView> iconsOnBoard = new HashMap<>();
	private static final String GREEN = "/img/icons/greenPlayer.png";
	private static final String BLUE = "/img/icons/bluePlayer.png";
	private static final String PINK = "/img/icons/pinkPlayer.png";
	private static final String RED = "/img/icons/redPlayer.png";
	private static final String PURPLE = "/img/icons/purplePlayer.png";
	private static final String YELLOW = "/img/icons/yellowPlayer.png";
	
	private static final String INFO_IMAGE_PATH = "/img/screens/infoBack.jpg";
	private static final String SNAKE_IMAGE_PATH = "/img/icons/snake.png";
	private static final String QUESTION_IMAGE_PATH = "/img/icons/question.png";
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
<<<<<<< Updated upstream
    private Label time;
    
    private Timeline timer;
    private Duration gameDuration = Duration.ZERO;
=======
    private Button diceButton;
	
>>>>>>> Stashed changes
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
//		showSnakes();
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
		ArrayList<Snake> snakes = new ArrayList();
		for (HashMap.Entry<Integer, Snake> s : board.getSnakes().entrySet()) { // get the board snakes
            snakes.add(s.getValue());
        }
		System.out.println("number of snakes is: " + snakes.size());
		for (Snake s : snakes) {
			Image snakeImage = new Image(getClass().getResource(SNAKE_IMAGE_PATH).toExternalForm());
			int headRow = board.getTile(s.getSnakeHead()).getxCoord();
	        int headColumn = board.getTile(s.getSnakeHead()).getyCoord();
	        int tailRow = board.getTile(s.getSnakeTail()).getxCoord();
	        int tailColumn = board.getTile(s.getSnakeTail()).getyCoord();
	        // Create ImageView for snake head
	        ImageView snakeHead = new ImageView(snakeImage);
	        snakeHead.setFitHeight(100);
	        snakeHead.setFitWidth(100);
	        snakeHead.setVisible(true);
	        GridPane.setRowIndex(snakeHead, headRow);
	        GridPane.setColumnIndex(snakeHead, headColumn);

	        // Create ImageView for snake tail
	        ImageView snakeTail = new ImageView(snakeImage);
	        snakeTail.setFitHeight(100);
	        snakeTail.setFitWidth(100);
	        snakeTail.setVisible(true);
	        GridPane.setRowIndex(snakeTail, tailRow);
	        GridPane.setColumnIndex(snakeTail, tailColumn);
	        // Add snake head and tail to GridPane
	        grid.getChildren().addAll(snakeHead, snakeTail);
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
    void handleDiceClick(ActionEvent event) {
    	if (Alerts.exit()==1)
    	
    }
}
