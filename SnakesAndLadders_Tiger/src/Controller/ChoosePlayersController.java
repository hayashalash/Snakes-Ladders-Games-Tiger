package Controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Color;
import Model.Player;
import View.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChoosePlayersController implements Initializable{

	// color images paths to be displayed in the combobox
	private static final String GREEN = "file:/img/icons/green.png";
	private static final String BLUE = "file:/img/icons/blue.png";
	private static final String PINK = "file:/img/icons/pink.png";
	private static final String RED = "file:/img/icons/red.png";
	private static final String PURPLE = "file:/img/icons/purple.png";
	private static final String YELLOW = "file:/img/icons/yellow.png";
	private int playersNum = 2; //default value
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	final ObservableList<Image> images = fetchImages();
    	player1clr = fillComboBox(images);
    	player2clr = fillComboBox(images);
    	player3clr = fillComboBox(images);
    	player4clr = fillComboBox(images);
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
    
    private ComboBox<Image> fillComboBox(ObservableList<Image> options) { // fill a combo box with the color images
    	ComboBox<Image> combo = new ComboBox<>();
        combo.getItems().addAll(options);
        combo.setButtonCell(new ImageListCell());
        combo.setCellFactory(listView -> new ImageListCell());
        combo.getSelectionModel().select(0);
        return combo;
    }
    class ImageListCell extends ListCell<Image> {
        private final ImageView view;
 
        ImageListCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            view = new ImageView();
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
        Image green = new Image(GREEN);
    	Image blue = new Image(BLUE);
    	Image pink = new Image(PINK);
    	Image red = new Image(RED);
    	Image purple = new Image(PURPLE);
    	Image yellow = new Image(YELLOW);
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
    	newScreen("ChooseDifficulty");
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
    		return ;
    	}
    	if ((playersNum == 3) && (player1txt.getText().isEmpty() || player1clr.getSelectionModel().isEmpty() || 
    			player2txt.getText().isEmpty() || player2clr.getSelectionModel().isEmpty() ||
    			player3txt.getText().isEmpty() || player3clr.getSelectionModel().isEmpty())) {
    		Alerts.warning("Some of the information is missing. Please fill out all fields");
    		return ;
    	}
    	if ((playersNum == 4) && (player1txt.getText().isEmpty() || player1clr.getSelectionModel().isEmpty() || 
    			player2txt.getText().isEmpty() || player2clr.getSelectionModel().isEmpty()  ||
    			player3txt.getText().isEmpty() || player3clr.getSelectionModel().isEmpty() || 
    			player4txt.getText().isEmpty() || player4clr.getSelectionModel().isEmpty())) {
    		Alerts.warning("Some of the information is missing. Please fill out all fields");
    		return ;
    	}
    	 	
    	String p1color = player1clr.getSelectionModel().getSelectedItem().toString();
    	String p1name = player1txt.getText();
    	String p2color = player2clr.getSelectionModel().getSelectedItem().toString();
    	String p2name = player2txt.getText();
    	Player firstP;
    	Player secondP;
    	
    	//create first player
    	if (p1color.equals(BLUE)){
    		firstP = new Player(p1name, Color.Blue);
    	}
    	else if (p1color.equals(GREEN)){
    		firstP = new Player(p1name, Color.Green);
    	}
    	else if (p1color.equals(PINK)){
    		firstP = new Player(p1name, Color.Pink);
    	}
    	else if (p1color.equals(PURPLE)){
    		firstP = new Player(p1name, Color.Purple);
    	}
    	else if (p1color.equals(RED)){
    		firstP = new Player(p1name, Color.Red);
    	}
    	else if (p1color.equals(YELLOW)){
    		firstP = new Player(p1name, Color.Yellow);
    	}


    	// create second player
    	if (p2color.equals(BLUE)){
    		secondP = new Player(p1name, Color.Blue);
    	}
    	else if (p2color.equals(GREEN)){
    		secondP = new Player(p1name, Color.Green);
    	}
    	else if (p2color.equals(PINK)){
    		secondP = new Player(p1name, Color.Pink);
    	}
    	else if (p2color.equals(PURPLE)){
    		secondP = new Player(p1name, Color.Purple);
    	}
    	else if (p2color.equals(RED)){
    		secondP = new Player(p1name, Color.Red);
    	}
    	else if (p2color.equals(YELLOW)){
    		secondP = new Player(p1name, Color.Yellow);
    	}
    	
    	if (playersNum == 3 || playersNum ==4) {
    		// TODO create third player
    	}
    	
    	if (playersNum ==4) {
    		// TODO create fourth player
    	}
    	
    	// TODO create game object and move to Game screen based on difficulty provided in previous screen
    	
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


