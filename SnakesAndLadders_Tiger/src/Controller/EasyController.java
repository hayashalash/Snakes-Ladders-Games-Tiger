package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.Map.Entry;
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
import javafx.util.Duration;
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
import javafx.scene.control.TextField;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class EasyController implements Initializable{

	private final double ICON_SIZE = 50; // the moving icons on the board
    private final double IMAGE_SIZE = 50; // the icons next to the names
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
	private static final String INFO_IMAGE_PATH = "/img/screens/blank.jpg";
	private static final String QUESTION_IMAGE_PATH = "/img/icons/question.png";
	private double SURPRISE_SIZE = 50;
	private double TOKEN_SIZE = 50;
	public static Game game;
	private GameController gameController;
	Board board = new Board(game.getDifficulty());
	public Player currentTurn;
	private static final String DEFAULT_DICE_IMAGE_PATH = "/img/icons/dice.png";
	private static final String SURPRISE_IMAGE_PATH = null;
	ArrayList<Player> playersOutsideBoard = new ArrayList<>();

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
        diceImageMap.put(5, "/img/icons/diceQ.png");
        diceImageMap.put(6, "/img/icons/diceQ.png");
        diceImageMap.put(7, "/img/icons/diceQ.png");
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
	    gameController.showLadders();
	    gameController.showSnakes();
		showQuestions();
		showSurprises();
		//ensureExitButtonOnTop();
		Dice.RollingDiceStartingGame(game);
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
	public void showSurprises() {
		for (Tile st : board.getSurpriseTiles()) {
			Image surpriseImage = new Image(getClass().getResource(SURPRISE_IMAGE_PATH).toExternalForm());
			int row = st.getxCoord();
	        int column = st.getyCoord();
	        showOneCellIcon(surpriseImage, row, column, SURPRISE_SIZE);
		}
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
			iv.setFitHeight(IMAGE_SIZE);
			iv.setFitWidth(IMAGE_SIZE);
			playersStart.getChildren().add(iv);
		}
		
		for (Player p : game.getPlayers()) {
			Label name = new Label(p.getPlayerName());
			name.setStyle("-fx-font-family: Serif; -fx-font-size: 20px;");
			name.setPadding(new Insets(10,5,10,5));
			ImageView icon = new ImageView(icons.get(p));
			icon.setFitHeight(ICON_SIZE);
			icon.setFitWidth(ICON_SIZE);
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
        dialog.setWidth(800);
        dialog.setHeight(500);
        Image info = new Image(getClass().getResource(INFO_IMAGE_PATH).toExternalForm());
		ImageView background = new ImageView(info);
		background.setFitHeight(dialog.getHeight());
		background.setFitWidth(dialog.getWidth());
		background.setVisible(true);
		ArrayList<Label> labels = new ArrayList<>();
		Label diceL = new Label("Roll the dice to determine your fate");
		labels.add(diceL);
		Label turnL = new Label("The green border indicates your turn");
		labels.add(turnL);
		Label snakeL = new Label("Encounter snakes and slide down");
		labels.add(snakeL);
		Label ladderL = new Label("Find ladders and climb up");
		labels.add(ladderL);
		Label questionL = new Label("Land on a question mark or get one on the dice roll and answer questions for your destiny");
		labels.add(questionL);
		Label surpriseL = new Label("Surprises can move you forward or backward");
		labels.add(surpriseL);
		Label winL = new Label("Be the first to reach the last tile to claim victory!");
		for (Label l :labels) {
			l.setStyle("-fx-font-family: Serif; -fx-font-size: 17px;");
			l.setPadding(new Insets(10,0,5,10)); // top right bottom left
		}
		winL.setStyle("-fx-font-family: Serif; -fx-font-size: 22px;");
		ArrayList<ImageView> imgs = new ArrayList<>();
		Image dice = new Image(getClass().getResource("/img/icons/dice.png").toExternalForm());
		ImageView diceIV = new ImageView(dice);
		imgs.add(diceIV);
		Image pawn = new Image(getClass().getResource("/img/icons/pawn.png").toExternalForm());
		ImageView pawnIV = new ImageView(pawn);
		Label name = new Label ("yourName");
		name.setPadding(new Insets(7,5,5,0));
		name.setStyle("-fx-font-size: 10px;");

		HBox pawnTurn = new HBox(pawnIV, name);
		pawnTurn.setStyle("-fx-border-color: #00FF00; -fx-border-radius: 10; -fx-border-width: 3;");
		imgs.add(pawnIV);
		Image snake = new Image(getClass().getResource("/img/icons/redSnake.png").toExternalForm());
		ImageView snakeIV = new ImageView(snake);
		imgs.add(snakeIV);
		Image ladder = new Image(getClass().getResource("/img/icons/ladderIcon.png").toExternalForm());
		ImageView ladderIV = new ImageView(ladder);
		imgs.add(ladderIV);
		Image q = new Image(getClass().getResource("/img/icons/question.png").toExternalForm());
		ImageView qIV = new ImageView(q);
		imgs.add(qIV);
		Image s = new Image(getClass().getResource("/img/icons/surprise.png").toExternalForm());
		ImageView sIV = new ImageView(s);
		imgs.add(sIV);
		
		for(ImageView iv : imgs) {
			iv.setFitHeight(ICON_SIZE);
			iv.setFitWidth(ICON_SIZE);
		}
		VBox vbox = new VBox(new HBox(diceIV, diceL), new HBox(pawnTurn, turnL), new HBox(snakeIV, snakeL), 
				new HBox(ladderIV, ladderL), new HBox(qIV, questionL), new HBox(sIV, surpriseL), winL);
		vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10); // Set spacing between the lines
        vbox.setPadding(new Insets(0, 20, 50, 80)); // top right bottom left
        StackPane content = new StackPane(background, vbox);
        dialog.getDialogPane().setContent(content);

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
        boolean added = game.getPlayersOrder().offer(nextPlayer);
        if(added)
        	System.out.println("player "+nextPlayer.getPlayerName()+" was added back to the queue");
        else
        	System.out.println("player "+nextPlayer.getPlayerName()+" was NOT added back to the queue");
        return nextPlayer;
    }


    private void viewResultDice(Player currentPlayer,int diceResult) {//this for easy difficulty only
    	if(diceResult <= 4) {
    		move(currentPlayer, diceResult);
		}
    	else if(diceResult == 5 ) {
    		//display easy question 
//    		showQuestionPopup(Difficulty.Easy);
    		move(currentPlayer, 0);

    	}
    	else if(diceResult ==6) {
    		//display normal question 
//    		showQuestionPopup(Difficulty.Medium);
    		move(currentPlayer, 0);


    	}
    	else if(diceResult == 7) {
    		//display hard question 	
//    		showQuestionPopup(Difficulty.Hard);
    		move(currentPlayer, 0);


        }	
	}

	
   
	
    


    void move(Player player, int steps) {	    
//	    System.out.println(player.toString());
	    int currentPosition = player.getPlayerPlace();
	    System.out.println("current player position: "+currentPosition);

	    player.setPlayerPrevPlace(currentPosition);
	    hidePlayerToken(player);
	    
	    int newPosition = NextMove(currentPosition,steps);	    
	    System.out.println("new player position: "+newPosition);
	    // Set player's new position
	    player.setPlayerPlace(newPosition);
	    displayPlayerToken(player, newPosition);
	    
	    // Check if player reaches last tile
	    if (newPosition == board.getBoardSize()) {
	        player.setPlayerPlace(newPosition);
	        displayPlayerToken(player, newPosition);
	        game.setWinner(player);
	        System.out.println(player.getPlayerName() + " is the WINNER!");
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
	    token.setFitHeight(TOKEN_SIZE);
	    token.setFitWidth(TOKEN_SIZE);
	    token.setVisible(true);
	    System.out.println("player to be displayed now: "+player.getPlayerName());
	    if(newPosition!=0) {
	    	// If the token is not already in the grid, add it
		    if (!grid.getChildren().contains(iconsOnBoard.get(player))) {
		        grid.getChildren().add(iconsOnBoard.get(player));
		        if (playersOutsideBoard.size() > 0) { // if there are still players outside the board
		        	if (player.equals(playersOutsideBoard.get(0))) {
			        	playersStart.getChildren().remove(0);
			        	playersOutsideBoard.remove(0);
			        }
			        else if (player.equals(playersOutsideBoard.get(1))) {
			        	playersStart.getChildren().remove(1);
			        	playersOutsideBoard.remove(1);
			        }
			        else if (game.getPlayersNum()>2 && player.equals(playersOutsideBoard.get(2))) {
			        	playersStart.getChildren().remove(2);
			        	playersOutsideBoard.remove(2);
			        }
			        else if (game.getPlayersNum()>3 && player.equals(playersOutsideBoard.get(3))) {
			        	playersStart.getChildren().remove(3);
			        	playersOutsideBoard.remove(3);
			        }
		        }
		    }
		    Tile pos = board.getTile(newPosition);
//		    System.out.println(pos);
		    int row = pos.getxCoord();
		    int column = pos.getyCoord();
		    GridPane.setRowIndex(iconsOnBoard.get(player), row);
		    GridPane.setColumnIndex(iconsOnBoard.get(player), column);
		    
	    }
	}

	private void hidePlayerToken(Player p) {
	    ImageView token = iconsOnBoard.get(p);
	    if (token != null) {
	        grid.getChildren().remove(token);
	    }
	}
	 int NextMove(int currPosition, int steps) {
	    	System.out.println("steps to move in NextMove are: " + steps);
		    int nextPos = currPosition + steps;

		    if (nextPos > board.getBoardSize() || nextPos < 1) {
		    	System.out.println("next position is "+ nextPos +" and is larger than the board size os smaller than 1.");
		        return currPosition; // Ensure next position is within the board boundaries
		    }

		    Tile nextTile = board.getTile(nextPos);
		    if (nextTile == null) {
		    	System.out.println("next position is "+ nextPos +" and that tile is null.");
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
		            return ladder.getLadderTop();
		        case Surprise:
		            System.out.println("Yaaaay you got a gift!");
		            return nextPos; // Handle surprise tiles appropriately
		        case Question:
		            System.out.println("I have a question for you");
		            return nextPos; // Handle question tiles appropriately
		        default:
		            // Handle unknown tile types or other cases
		        	System.out.println("Next step will be: " + nextPos);
		            return nextPos;
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
	public  int showQuestionPopup(Difficulty difficulty) {//view the question  dialog  and return the number of steps he has to step 
	    Dialog<ButtonType> dialog = new Dialog<>();
	    dialog.setTitle("Question");
			Question q = returnQuestion(difficulty);
			System.out.println(q);
		
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
	                resultTextField.setText("Your answer is right!");
	                selectedAnswer.setStyle("-fx-text-fill: green;");
	                if(difficulty==Difficulty.Easy || difficulty==Difficulty.Medium) {
	                	return 0;
	                }
	                else return 1;
	            }
	         else {
                resultTextField.setText("Wrong answer.");
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
                if(difficulty==Difficulty.Easy)return -1;
                if(difficulty==Difficulty.Medium)return -2;
                if(difficulty==Difficulty.Hard)return -3;


	        }
	        }
	    }
		return 0;
	}
	private Question returnQuestion(Difficulty difficulty) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private void ensureExitButtonOnTop() {
	  rootAnchorPane.getChildren().remove(exitButton); // Remove exitButton from AnchorPane
	    rootAnchorPane.getChildren().add(exitButton);    // Re-add exitButton to AnchorPane (on top)
	}
	}


