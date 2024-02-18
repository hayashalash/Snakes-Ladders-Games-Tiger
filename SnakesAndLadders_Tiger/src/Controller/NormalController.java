package Controller;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
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
import Model.LadderTile;
import Model.Player;
import Model.Question;
import Model.QuestionTile;
import Model.Snake;
import Model.SnakeColor;
import Model.SnakeTile;
import Model.SysData;
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
	private static final String GREEN = "/img/icons/greenPlayer.png"; //Player Token path
	private static final String BLUE = "/img/icons/bluePlayer.png"; //Player Token path
	private static final String PINK = "/img/icons/pinkPlayer.png"; //Player Token path
	private static final String RED = "/img/icons/redPlayer.png"; //Player Token path
	private static final String PURPLE = "/img/icons/purplePlayer.png"; //Player Token path
	private static final String YELLOW = "/img/icons/yellowPlayer.png";	//Player Token path
	private static final String INFO_IMAGE_PATH = "/img/screens/infoBack.jpg";
	private static final String QUESTION_IMAGE_PATH = "/img/icons/question.png";
	private static final String SURPRISE_IMAGE_PATH = "/img/icons/surprise.png";
	private static final String DEFAULT_DICE_IMAGE_PATH = "/img/icons/dice.png";
	private GameController gameController;
	private int currentPlayerIndex = 0;
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
        diceImageMap.put(10, "/img/icons/diceQ.png");
        diceImageMap.put(11, "/img/icons/diceQ.png");
        diceImageMap.put(12, "/img/icons/diceQ.png");
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
	    gameController = new GameController(board, grid);
	    gameController.showSnakes();
	    gameController.showLadders();
		showQuestions();
		showSurprises();
		ensureExitButtonOnTop();
		Dice dice = new Dice();
		dice.RollingDiceStartingGame(game);
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
			iv.setFitHeight(ICON_SIZE);
			iv.setFitWidth(ICON_SIZE);
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
            int result = Dice.RandomNumberGenerator(Difficulty.Medium);
            lastDiceResult = result; // Save the result
            String imagePath = diceImageMap.get(result);

            // Create a keyframe for each image of the dice
            KeyFrame keyFrame = new KeyFrame(frameInterval.multiply(i),
                    e -> updateDiceImage(imagePath));

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setCycleCount(1); // Set the timeline to one cycle
        timeline.setOnFinished(e -> {
            // After 10 seconds, reset the dice image to the default
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(10));
            pauseTransition.setOnFinished(event1 -> updateDiceImage(DEFAULT_DICE_IMAGE_PATH));
            pauseTransition.play();
        });

        // Roll the dice once to get the result
        System.out.println("Dice result: "+lastDiceResult);
        // Display the dice result

        // Move the current player based on the dice result
        Player currentPlayer = getNextPlayerToMove();
        viewResultDice(currentPlayer, lastDiceResult);

        timeline.play(); // Start the animation
    }

    
    private Player getNextPlayerToMove() {
        Player nextPlayer = game.getPlayersOrder().poll();
        // Increment currentPlayerIndex for the next turn
        System.out.println("current player: "+nextPlayer.getPlayerName());
        game.getPlayersOrder().add(nextPlayer);
        return nextPlayer;
    }


    private void viewResultDice(Player currentPlayer,int diceResult) {//this for easy difficulty only
    	if(diceResult <= 6) {
    		move(currentPlayer, diceResult);
		}
    	else if(diceResult == 7 || diceResult == 8) {
    		//display easy question 
    		showQuestionPopup(Difficulty.Easy);
    	}
    	else if(diceResult == 9 || diceResult == 10) {
    		//display normal question 
    		showQuestionPopup(Difficulty.Medium);

    	}
    	else if(diceResult == 11 || diceResult == 12) {
    		//display hard question 	
    		showQuestionPopup(Difficulty.Hard);

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

    
	void move(Player player, int steps) {	    
	    System.out.println(player.toString());
	    int currentPosition = player.getPlayerPlace();
	    player.setPlayerPrevPlace(currentPosition);
	    hidePlayerToken(player);
	    int newPosition = currentPosition + steps;
	    
	    // Check if newPosition exceeds 100
	    if (newPosition > 100) {
	    	newPosition = currentPosition - steps;
	        //Player cannot move beyond the board
	    }
	    System.out.println("current player position: "+newPosition);
	    // Set player's new position
	    player.setPlayerPlace(newPosition);
	    displayPlayerToken(player, newPosition);
	    
	    // Check if player reaches 100
	    if (newPosition == 100) {
	        player.setPlayerPlace(newPosition);
	        displayPlayerToken(player, newPosition);
	        game.setWinner(player);
	        System.out.println(player.getPlayerName() + " is the WINNER!");
	    }
	}
	
	/*
	 	void move(Player player, int steps) {	    
	    System.out.println(player.toString());
	    int currentPosition = player.getPlayerPlace();
	    player.setPlayerPrevPlace(currentPosition);
	    hidePlayerToken(player);
	    
	    int newPosition = NextMove(currentPosition,steps);	    
	    System.out.println("current player position: "+newPosition);
	    // Set player's new position
	    player.setPlayerPlace(newPosition);
	    displayPlayerToken(player, newPosition);
	    
	    // Check if player reaches 100
	    if (newPosition == 100) {
	        player.setPlayerPlace(newPosition);
	        displayPlayerToken(player, newPosition);
	        game.setWinner(player);
	        System.out.println(player.getPlayerName() + " is the WINNER!");
	    }
	}
	*/
	
	 int NextMove(int currPosition, int steps) {
		    int nextPos = currPosition + steps;

		    if (nextPos > board.getBoardSize()) {
		        return currPosition; // Ensure next position is within the board boundaries
		    }

		    Tile nextTile = board.getTile(nextPos);
		    if (nextTile == null) {
		        return currPosition; // Handle case where tile is null
		    }

		    switch (nextTile.gettType()) {
		        case Classic:
		            System.out.println("Next step will be: " + nextPos);
		            return nextPos;
		        case SnakeHead:
		            SnakeTile snakeT = (SnakeTile) nextTile;
		            Snake snake = snakeT.getSnake();
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
		            return ladder.getLadderBottom();
		        case Surprise:
		            System.out.println("Yaaaay you got a gift!");
		            break; // Handle surprise tiles appropriately
		        case Question:
		            System.out.println("I have a question for you");
		            break; // Handle question tiles appropriately
		        default:
		            // Handle unknown tile types or other cases
		            break;
		    }

		    return 0;
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
	    token.setFitHeight(TOKEN_SIZE);
	    token.setFitWidth(TOKEN_SIZE);
	    token.setVisible(true);
	    
	    Tile pos = board.getTile(newPosition);
	    System.out.println(pos);
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
	
	
	
	//question pop up :
	public  void showQuestionPopup(Difficulty difficulty) {//view the question  dialog 
	    Dialog<ButtonType> dialog = new Dialog<>();
	    dialog.setTitle("Question");
		
			Question q = returnQuestion(difficulty);
		
	    // Create  elements for the question and answer:
	    VBox vbox = new VBox();
	    Label questionLabel = new Label(q.getQuestion());
	    ToggleGroup answerGroup = new ToggleGroup();
	    RadioButton answer1 = new RadioButton(q.getAnswer1());
	    RadioButton answer2 = new RadioButton(q.getAnswer2());
	    RadioButton answer3 = new RadioButton(q.getAnswer3());
	    RadioButton answer4 = new RadioButton(q.getAnswer4());
	    TextField resultTextField = new TextField();
	    resultTextField.setEditable(false);
	    vbox.getChildren().addAll(questionLabel, answer1, answer2, answer3, answer4);
	    dialog.getDialogPane().setContent(vbox);	 // Set the content of the dialog
	    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
	    Optional<ButtonType> result = dialog.showAndWait(); // Show the dialog and wait for a button click

	    if (result.isPresent() && result.get() == ButtonType.OK) {  // Handle  button click
	        RadioButton selectedAnswer = (RadioButton) answerGroup.getSelectedToggle();	 // Check the selected answer
	        if (selectedAnswer != null) {
	            int selectedAnswerNumber = (int) selectedAnswer.getUserData();//get the number of seelcted answer
	            int correctAnswerNumber=q.getCorrectAnswer();
	            if(selectedAnswerNumber == correctAnswerNumber) {
	           //     resultTextField.setText("Your answer is right!");
	                selectedAnswer.setStyle("-fx-text-fill: green;");
	            }
	         else {
               // resultTextField.setText("Wrong answer.");
                selectedAnswer.setStyle("-fx-text-fill: red;");
                switch (q.getCorrectAnswer()) {//mark the right answer in green
                case 1:
                    answer1.setStyle("-fx-text-fill: green;");
                    break;
                case 2:
                    answer2.setStyle("-fx-text-fill: green;");
                    break;
                case 3:
                    answer3.setStyle("-fx-text-fill: green;");
                    break;
                case 4:
                    answer4.setStyle("-fx-text-fill: green;");
                    break;
            }
	        }
	        }
	    }
	}
	
    public Question returnQuestion(Difficulty difficulty) {
        HashSet<Question> questions = SysData.getInstance().getQuestions();
        HashMap<Difficulty, ArrayList<Question>> questionMap = new HashMap<>();

        // Initialize ArrayList for each difficulty
        for (Question question : questions) {
            Difficulty diff = question.getDifficulty();
            ArrayList<Question> q = questionMap.getOrDefault(diff, new ArrayList<>());
            q.add(question);
            questionMap.put(diff, q);
        }

        Random random = new Random();
        int r = random.nextInt(10);

        // Ensure that the ArrayList for the specified difficulty is not null and contains questions
        while (questionMap.get(difficulty) == null || questionMap.get(difficulty).isEmpty() || questionMap.get(difficulty).get(r) == null) {
            r = random.nextInt(10);
        }

        return questionMap.get(difficulty).get(r);
    }
    
}

