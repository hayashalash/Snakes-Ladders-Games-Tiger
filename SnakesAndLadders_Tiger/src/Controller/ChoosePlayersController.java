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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChoosePlayersController implements Initializable{

	Methods methods = new Methods();
	
	// color images paths to be displayed in the combobox
	private static final String GREEN = "/img/icons/green.png";
	private static final String BLUE = "/img/icons/blue.png";
	private static final String PINK = "/img/icons/pink.png";
	private static final String RED = "/img/icons/red.png";
	private static final String PURPLE = "/img/icons/purple.png";
	private static final String YELLOW = "/img/icons/yellow.png";
	private int playersNum = 2; //default value
	
	public static Difficulty diff;
	
	@FXML
	private AnchorPane rootAnchorPane;
	
	@FXML
    private Button exitBtn;

    @FXML
    private TextField player1txt;

    @FXML
    private ComboBox<Image> player1clr;

    @FXML
    private TextField player2txt;

    @FXML
    private ComboBox<Image> player2clr;

    @FXML
    private VBox player3box;

    @FXML
    private TextField player3txt;

    @FXML
    private ComboBox<Image> player3clr;

    @FXML
    private VBox player4box;

    @FXML
    private TextField player4txt;

    @FXML
    private ComboBox<Image> player4clr;

    @FXML
    private Button backBtn;

    @FXML
    private Button startGameBtn;

    @FXML
    private Button addThird;

    @FXML
    private Button removeThird;

    @FXML
    private Button addFourth;

    @FXML
    private Button removeFourth;
    
    @FXML
    private Label difficulty;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ObservableList<Image> images = fetchImages();
        fillComboBox(player1clr, images);
        fillComboBox(player2clr, images);
        fillComboBox(player3clr, images);
        fillComboBox(player4clr, images);
        difficulty.setText("Game Difficulty: "+diff);
        StackPane.setAlignment(difficulty, javafx.geometry.Pos.CENTER);
        
//    	player1clr.getItems().addAll(Color.values());
//    	player1clr.getSelectionModel().select(0);
//    	Media mediaFile = new Media(this.getClass().getResource(MEDIA_URL).toExternalForm());
//    	player = new MediaPlayer(mediaFile);
//    	media.setMediaPlayer(player);
//    	player.setAutoPlay(true);
//    	media.setVisible(true);
//    	player.setVolume(0.1);
//    	player.play();
    	
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
    void addFourthP(ActionEvent event) {
    	if (player3box.isDisable()) // cannot add 4th player before 3rd
    		return;
    	if (player4box.isDisable()) {
    		player4box.setDisable(false); // enable the box
    		removeFourth.setDisable(false); // enable the option to remove 4th player
    		addFourth.setDisable(true); // gray out the option to add
    		playersNum++;
    	}
    }

    @FXML
    void addThirdP(ActionEvent event) {
    	if (player3box.isDisable()) { // add third player
    		player3box.setDisable(false); // enable the box
    		removeThird.setDisable(false); // enable the option to remove 3rd player
    		addThird.setDisable(true); // gray out the option to add
    		playersNum++;
    	}
    }

    @FXML
    void removeFourthP(ActionEvent event) {
    	if (player4box.isDisable()) // this player is already grayed out
    		return;
    	player4txt.clear(); // clear the name
		player4box.setDisable(true); // gray out the box
		removeFourth.setDisable(true); // gray out the option to disable
		addFourth.setDisable(false); // enable the option to add
		playersNum--;
    }

    @FXML
    void remvoveThirdP(ActionEvent event) {
    	if (!player4box.isDisable()) // cannot disable 3rd player before 4th
    		return;
    	if (player3box.isDisable()) // this player is already grayed out
    		return;
    	else { // remove third player
    		player3txt.clear(); // clear the name
    		player3box.setDisable(true); // gray out the box
    		removeThird.setDisable(true); // gray out the option to disable
    		addThird.setDisable(false); // enable the option to add
    		playersNum--;
    	}
    }
    
    @FXML
    void backToDifficulty(ActionEvent event) {
    	methods.newScreen("ChooseDifficulty");
    }

    @FXML
    void exitGame(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    @FXML
    void startGame(ActionEvent event) {
    	if ((playersNum == 2) && (player1txt.getText().isEmpty() || player1clr.getSelectionModel().isEmpty() || 
    			player2txt.getText().isEmpty() || player2clr.getSelectionModel().isEmpty())) {
    		Alerts.warning("Some of the information is missing. Please fill out all fields");
    		return;
    	}
    	else if ((playersNum == 3) && (player1txt.getText().isEmpty() || player1clr.getSelectionModel().isEmpty() || 
    			player2txt.getText().isEmpty() || player2clr.getSelectionModel().isEmpty() ||
    			player3txt.getText().isEmpty() || player3clr.getSelectionModel().isEmpty())) {
    		Alerts.warning("Some of the information is missing. Please fill out all fields");
    		return;
    	}
    	else if ((playersNum == 4) && (player1txt.getText().isEmpty() || player1clr.getSelectionModel().isEmpty() || 
    			player2txt.getText().isEmpty() || player2clr.getSelectionModel().isEmpty()  ||
    			player3txt.getText().isEmpty() || player3clr.getSelectionModel().isEmpty() || 
    			player4txt.getText().isEmpty() || player4clr.getSelectionModel().isEmpty())) {
    		Alerts.warning("Some of the information is missing. Please fill out all fields");
    		return;
    	}
    	if ((playersNum == 2) && player1clr.getSelectionModel().getSelectedItem().equals(player2clr.getSelectionModel().getSelectedItem())) {
    		Alerts.warning("Please choose a unique color for each player");
    		return;
    	}
    	else if ((playersNum == 3) && (player1clr.getSelectionModel().getSelectedItem().equals(player2clr.getSelectionModel().getSelectedItem()) || 
    			player1clr.getSelectionModel().getSelectedItem().equals(player3clr.getSelectionModel().getSelectedItem()) || 
    			player2clr.getSelectionModel().getSelectedItem().equals(player3clr.getSelectionModel().getSelectedItem()))) {
    		Alerts.warning("Please choose a unique color for each player");
    		return;
    	}
    	else if ((playersNum == 4) && (player1clr.getSelectionModel().getSelectedItem().equals(player2clr.getSelectionModel().getSelectedItem()) || 
    			player1clr.getSelectionModel().getSelectedItem().equals(player3clr.getSelectionModel().getSelectedItem()) || 
    			player1clr.getSelectionModel().getSelectedItem().equals(player4clr.getSelectionModel().getSelectedItem()) ||
    			player2clr.getSelectionModel().getSelectedItem().equals(player3clr.getSelectionModel().getSelectedItem()) ||
    			player2clr.getSelectionModel().getSelectedItem().equals(player4clr.getSelectionModel().getSelectedItem()) ||
    			player3clr.getSelectionModel().getSelectedItem().equals(player4clr.getSelectionModel().getSelectedItem()))) {
    		Alerts.warning("Please choose a unique color for each player");
    		return;
    	}
    	if ((playersNum == 2) && player1txt.getText().equals(player2txt.getText())) {
    		Alerts.warning("Please choose a unique name for each player");
    		return;
    	}
    	else if ((playersNum == 3) && (player1txt.getText().equals(player2txt.getText()) || 
    			player1txt.getText().equals(player3txt.getText()) || 
    			player2txt.getText().equals(player3txt.getText()))) {
    		Alerts.warning("Please choose a unique name for each player");
    		return;
    	}
    	else if ((playersNum == 4) && (player1txt.getText().equals(player2txt.getText()) || 
    			player1txt.getText().equals(player3txt.getText()) || 
    			player1txt.getText().equals(player4txt.getText()) ||
    			player2txt.getText().equals(player3txt.getText()) ||
    			player2txt.getText().equals(player4txt.getText()) ||
    			player3txt.getText().equals(player4txt.getText()))) {
    		Alerts.warning("Please choose a unique name for each player");
    		return;
    	}
    	// Regular expression to match letters only
        Pattern pattern = Pattern.compile("[a-zA-Z]+");

        // Matcher to match the pattern against the input
        Matcher matcher1 = pattern.matcher(player1txt.getText());
        Matcher matcher2 = pattern.matcher(player2txt.getText());
                
        if (!matcher1.matches()) {
        	Alerts.warning("Players names cannot contain numbers or special characters.");
    		return;
        }
        if (!matcher2.matches()) {
        	Alerts.warning("Players names cannot contain numbers or special characters.");
    		return;
        }
        
        if (playersNum > 2) {
        	Matcher matcher3 = pattern.matcher(player3txt.getText());
        	if (!matcher3.matches()) {
            	Alerts.warning("Players names cannot contain numbers or special characters.");
        		return;
            }
        }
        if (playersNum == 4) {
        	Matcher matcher4 = pattern.matcher(player4txt.getText());
        	if (!matcher4.matches()) {
            	Alerts.warning("Players names cannot contain numbers or special characters.");
        		return;
            }
        }
    	
    	ArrayList<Player> players = new ArrayList<>();
    	String p1name = player1txt.getText();
    	String p2name = player2txt.getText();
    	int player1indx = player1clr.getSelectionModel().getSelectedIndex();
    	int player2indx = player2clr.getSelectionModel().getSelectedIndex();
    	Player firstP;
    	Player secondP;
    	
    	
    	//create first player
    	if (player1indx == 0) //first color is green
    		firstP = new Player(p1name, Color.Green);
    	else if (player1indx == 1) // second color is blue
    		firstP = new Player(p1name, Color.Blue);
    	else if (player1indx == 2) // third color is pink
    		firstP = new Player(p1name, Color.Pink);
    	else if (player1indx == 3) // fourth color is red
    		firstP = new Player(p1name, Color.Red);
    	else if (player1indx == 4) // fifth color is purple
    		firstP = new Player(p1name, Color.Purple);
    	else // if (player1indx == 5) --> sixth color is yellow
    		firstP = new Player(p1name, Color.Yellow);
    	
    	players.add(firstP);

    	// create second player
    	if (player2indx == 0) //first color is green
    		secondP = new Player(p2name, Color.Green);
    	else if (player2indx == 1) // second color is blue
    		secondP = new Player(p2name, Color.Blue);
    	else if (player2indx == 2) // third color is pink
    		secondP = new Player(p2name, Color.Pink);
    	else if (player2indx == 3) // fourth color is red
    		secondP = new Player(p2name, Color.Red);
    	else if (player2indx == 4) // fifth color is purple
    		secondP = new Player(p2name, Color.Purple);
    	else // if (player2indx == 5) --> sixth color is yellow
    		secondP = new Player(p2name, Color.Yellow);
    	
    	players.add(secondP);
    	
    	if (playersNum == 3 || playersNum ==4) {
        	String p3name = player3txt.getText();
        	int player3indx = player3clr.getSelectionModel().getSelectedIndex();
        	Player thirdP;
    		if (player3indx == 0)
    			thirdP = new Player(p3name, Color.Green);
        	else if (player3indx == 1)
        		thirdP = new Player(p3name, Color.Blue);
        	else if (player3indx == 2)
        		thirdP = new Player(p3name, Color.Pink);
        	else if (player3indx == 3)
        		thirdP = new Player(p3name, Color.Red);
        	else if (player3indx == 4)
        		thirdP = new Player(p3name, Color.Purple);
        	else // if (player3indx == 5)
        		thirdP = new Player(p3name, Color.Yellow);
    		
    		players.add(thirdP);
    	}
    	
    	if (playersNum ==4) {
        	String p4name = player4txt.getText();
        	int player4indx = player4clr.getSelectionModel().getSelectedIndex();
        	Player fourthP;
    		if (player4indx == 0)
    			fourthP = new Player(p4name, Color.Green);
        	else if (player4indx == 1)
        		fourthP = new Player(p4name, Color.Blue);
        	else if (player4indx == 2)
        		fourthP = new Player(p4name, Color.Pink);
        	else if (player4indx == 3)
        		fourthP = new Player(p4name, Color.Red);
        	else if (player4indx == 4)
        		fourthP = new Player(p4name, Color.Purple);
        	else // if (player4indx == 5)
        		fourthP = new Player(p4name, Color.Yellow);
    		
    		players.add(fourthP);
    	}
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
    
    @FXML
    void entered(MouseEvent event){
    	methods.entered(event);
    }
	
    @FXML
    void exited(MouseEvent event){
    	methods.exited(event);
    }
    
}


