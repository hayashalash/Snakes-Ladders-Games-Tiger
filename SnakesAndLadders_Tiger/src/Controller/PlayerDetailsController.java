package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Color;
import Model.Difficulty;
import Model.Game;
import Model.Player;
import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PlayerDetailsController implements Initializable{
	
	// color images paths to be displayed in the combobox
	private static final String GREEN = "/img/icons/green.png";
	private static final String BLUE = "/img/icons/blue.png";
	private static final String PINK = "/img/icons/pink.png";
	private static final String RED = "/img/icons/red.png";
	private static final String PURPLE = "/img/icons/purple.png";
	private static final String YELLOW = "/img/icons/yellow.png";

	private int playersNum = 2; // player and System
	public static Difficulty diff;
	
	Methods methods = new Methods();
	
	@FXML
    private Label difficulty;
	
    @FXML
    private Button musicIcon;

    @FXML
    private Button exitBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button startGameBtn;

    @FXML
    private TextField playerTxt;

    @FXML
    private ComboBox<Image> playerClr;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		final ObservableList<Image> images = fetchImages();
        fillComboBox(playerClr, images);
		
        difficulty.setText("Game Difficulty: "+diff);
        StackPane.setAlignment(difficulty, javafx.geometry.Pos.CENTER);
        
    	if (Main.note.isPlaying()) {
    		musicIcon.setOpacity(1.0);
    	}
    	else {
    		musicIcon.setOpacity(0.5);
    	}	
	}
    
    @FXML
    void TurnOffOn(ActionEvent event) {
    	methods.turnOffOn(event, musicIcon);
    }

    @FXML
    void backToDifficulty(ActionEvent event) {
    	methods.newScreen("ChooseDifficulty");
    }

    @FXML
    void entered(MouseEvent event) {
    	methods.entered(event);
    }

    @FXML
    void exitGame(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void exited(MouseEvent event) {
    	methods.exited(event);
    }

    private void fillComboBox(ComboBox<Image> combo, ObservableList<Image> options) {
        combo.getItems().addAll(options);
        combo.setButtonCell(new ImageListCell());
        combo.setCellFactory(listView -> new ImageListCell());
        combo.getSelectionModel().select(0);
    }
    
    class ImageListCell extends ListCell<Image> {
        private final ImageView view;
        private final double IMAGE_WIDTH = 110;
        private final double IMAGE_HEIGHT = 17;
        ImageListCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            view = new ImageView();
            view.setFitWidth(IMAGE_WIDTH);
            view.setFitHeight(IMAGE_HEIGHT);
        }
 
        @Override protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);
 
            if (item == null || empty) {
                setGraphic(null);
            } else {
                view.setImage(item);
                setGraphic(view);
            }
        }
    }
 
    private ObservableList<Image> fetchImages() { // get list of images of colors
        Image green = new Image(getClass().getResource(GREEN).toExternalForm());
    	Image blue = new Image(getClass().getResource(BLUE).toExternalForm());
    	Image pink = new Image(getClass().getResource(PINK).toExternalForm());
    	Image red = new Image(getClass().getResource(RED).toExternalForm());
    	Image purple = new Image(getClass().getResource(PURPLE).toExternalForm());
    	Image yellow = new Image(getClass().getResource(YELLOW).toExternalForm());
    	ObservableList<Image> options = FXCollections.observableArrayList();
    	options.addAll(green, blue, pink, red, purple, yellow);
        
        return options;
    }
    
    @FXML
    void startGame(ActionEvent event) {
    	if (playerTxt.getText().isEmpty() || playerClr.getSelectionModel().isEmpty()) {
    		Alerts.warning("Some of the information is missing. Please fill out all fields");
    		return;
    	}
    	// Regular expression to match letters nd numbers only
    	Pattern pattern = Pattern.compile("[a-zA-Z\\d]+");

        // Matcher to match the pattern against the input
        Matcher matcher = pattern.matcher(playerTxt.getText());
                
        if (!matcher.matches()) {
        	Alerts.warning("Your nickname cannot contain spaces or special characters.");
    		return;
        }
        // ArrayList to store the players
        ArrayList<Player> players = new ArrayList<>();
        String playerName = playerTxt.getText();
    	int colorIndex = playerClr.getSelectionModel().getSelectedIndex();
    	Player player;
    	
    	if (colorIndex == 0) //first color is green
    		player = new Player(playerName, Color.Green);
    	else if (colorIndex == 1) // second color is blue
    		player = new Player(playerName, Color.Blue);
    	else if (colorIndex == 2) // third color is pink
    		player = new Player(playerName, Color.Pink);
    	else if (colorIndex == 3) // fourth color is red
    		player = new Player(playerName, Color.Red);
    	else if (colorIndex == 4) // fifth color is purple
    		player = new Player(playerName, Color.Purple);
    	else // if (colorIndex == 5) --> sixth color is yellow
    		player = new Player(playerName, Color.Yellow);
    	
    	players.add(player);
    	
    	Player system = new Player("System", Color.System);
    	system.setSystem(true);
    	players.add(system);
    	
    	if (diff == Difficulty.Easy) {
    		EasyController.game = new Game(diff, players, LocalDate.now());
    		methods.newScreen("easyBoard");
    	}
    	else if (diff == Difficulty.Medium) {
    		NormalController.game = new Game(diff, players, LocalDate.now());
    		methods.newScreen("normalBoard");
    	}
    	else if (diff == Difficulty.Hard) {
    		HardController.game = new Game(diff, players, LocalDate.now());
    		methods.newScreen("hardBoard");
    	}
    }
}
