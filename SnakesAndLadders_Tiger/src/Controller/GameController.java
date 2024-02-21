package Controller;

import java.net.URL;
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
import Model.LadderTile;
import Model.Player;
import Model.Question;
import Model.QuestionTile;
import Model.Snake;
import Model.SnakeColor;
import Model.SnakeTile;
import Model.Tile;
import Model.TileType;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import Model.Dice;
import Model.Difficulty;
import Model.Question;
import Model.SysData;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.Map;
import java.util.Timer;
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

 public class GameController implements Initializable {
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
	    private final double H_QUESTION_SIZE = 30; // question icon size
	    private final double H_SURPRISE_SIZE = 30; // surprise icon size
	    private final double H_RED_SNAKE_SIZE = 35; 
	    private final double H_TILE_SIZE  = 42;
		private final double H_TOKEN_SIZE = 35;
		// Current Sizes: depends on the difficulty of the current board
		private double QUESTION_SIZE = 0;
		private double SURPRISE_SIZE = 0;
		private double RED_SNAKE_SIZE = 0;
		private double TILE_SIZE = 0;
		private double TOKEN_SIZE = 0;
	    //Lists
	    private HashMap<Player, Image> icons = new HashMap<>();
	    private HashMap<Player, ImageView> iconsOnBoard = new HashMap<>();
	    private HashMap<Player, Integer> currentPosition;
	    //Path
		private static final String GREEN = "/img/icons/greenPlayer.png"; //Player Token path
		private static final String BLUE = "/img/icons/bluePlayer.png"; //Player Token path
		private static final String PINK = "/img/icons/pinkPlayer.png"; //Player Token path
		private static final String RED = "/img/icons/redPlayer.png"; //Player Token path
		private static final String PURPLE = "/img/icons/purplePlayer.png"; //Player Token path
		private static final String YELLOW = "/img/icons/yellowPlayer.png";	//Player Token path
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
		private static final String TOKEN_IMAGE_PATH = "/img/icons/greenPlayer.png";
		private static final String DEFAULT_DICE_IMAGE_PATH = "/img/icons/dice.png";
		private static final String SURPRISE_GIF_PATH =  "/img/icons/surpriseGIF.gif";
		
	    private Board board;
	    private GridPane grid;
	    
	    public GameController(Board board, GridPane grid) {
	        this.board = board;
	        this.grid = grid;
	        InitSizes();
	    }
	 
	 @FXML
	    private HBox player1;

	    @FXML
	    private HBox player2;

	    @FXML
	    private HBox player3;

	    @FXML
	    private HBox player4;
	
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

	    void showQuestionPopup(Difficulty difficulty) {
	    Dialog<ButtonType> dialog = new Dialog<>();
	    dialog.setTitle("Question");
        Question q= returnQuestion(difficulty);//call for the random question according to the  difficulty
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
	    vbox.getChildren().addAll(questionLabel, answer1, answer2, answer3, answer4,resultTextField);
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
	        }
	        }
	    }
	}
	
	public  Question returnQuestion(Difficulty difficulty) {//return a random question according to given difficulty
		HashSet<Question> questions= new HashSet();
		HashMap<Difficulty,ArrayList<Question>> questionMap = new HashMap();//define hashmap that store for each difficulty ,questions.
		 questions=SysData.getInstance().getQuestions();
		 for (Question question : questions) {
			 ArrayList<Question> q = new ArrayList();
			 Difficulty diff=question.getDifficulty();
			 q= questionMap.get(diff);
			 questionMap.put(difficulty, q);
		 }
		 Random random = new Random();
		 int r= random.nextInt(10);
		 while(questionMap.get(difficulty).get(r)==null) {
			 Random random2 = new Random();
			  r= random2.nextInt(10);
		 }
		 return questionMap.get(difficulty).get(r);
	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

    public  static void viewResultDise(int diceResult){//this for easy  difficulty only
    	if(diceResult<4) {
    		// function to move the player 
		}
    	if(diceResult==5) {//display easy question
    		
    	}
    	else { if(diceResult==6) {//display normal question 
    		
    	}
    	  else if(diceResult==7) {//display hard question 	
    	  }
    	}
	}

    public static void movePlayer(int n) {
    	
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
	
	public static void viewSurpriseTile() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Surprise");

        StackPane stackPane = new StackPane();
        Image backgroundImage = new Image("/img/screens/surpriseBackground.jpg");//set  the background
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Add the background image to the StackPane
        stackPane.getChildren().add(backgroundImageView);

        // Create a label for displaying random text
        Label randomTextLabel = new Label();
        randomTextLabel.setStyle("-fx-font-size: 18; -fx-text-fill: Black;");

        String[] possibleTexts = {"10 steps forward !", "10 steps back!"};
        Random random = new Random();
        int surpriseResult=random.nextInt(possibleTexts.length);
        String randomText = possibleTexts[surpriseResult];
        randomTextLabel.setText(randomText);

        stackPane.getChildren().add(randomTextLabel);
        dialog.getDialogPane().setContent(stackPane);

        // buttons, labels, or other components to the StackPane as needed

        dialog.showAndWait();
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
		        redSnakeImageView.setFitHeight(RED_SNAKE_SIZE);
		        redSnakeImageView.setFitWidth(RED_SNAKE_SIZE);
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
		
		public void showSurpriseGif() {
		    try {
		        // Load the surprise image
		        Image surpriseImage = new Image(getClass().getResource(SURPRISE_GIF_PATH).toExternalForm());
		        ImageView surpriseImageView = new ImageView(surpriseImage);
		        
		        // Show the surprise GIF
		        surpriseImageView.setVisible(true);

		        // Set up a timeline or pause transition to hide the GIF after a few seconds
		        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
		            surpriseImageView.setVisible(false);
		        }));
		        timeline.play();
		    } catch (Exception e) {
		        // Handle any exceptions (e.g., image not found)
		        e.printStackTrace();
		    }
		}


		
	
}
