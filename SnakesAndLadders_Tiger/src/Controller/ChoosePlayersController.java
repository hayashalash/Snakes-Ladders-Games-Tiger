package Controller;
import java.net.URL;
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
import javafx.scene.layout.VBox;

public class ChoosePlayersController implements Initializable{

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	final ObservableList<Image> images = fetchImages();
    	player1clr = fillComboBox(images);
    	player2clr = fillComboBox(images);
    	player3clr = fillComboBox(images);
    	player4clr = fillComboBox(images);
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
        Image green = new Image("file:/img/icons/green.png");
    	Image blue = new Image("file:/img/icons/blue.png");
    	Image pink = new Image("file:/img/icons/pink.png");
    	Image red = new Image("file:/img/icons/red.png");
    	Image purple = new Image("file:/img/icons/purple.png");
    	Image yellow = new Image("file:/img/icons/yellow.png");
    	ObservableList<Image> options = FXCollections.observableArrayList();
    	options.addAll(green, blue, pink, red, purple, yellow);
        
        return options;
    }
    
    @FXML
    void fourthPlayer(MouseEvent event) {
    	if (player3box.isDisable() && player4box.isDisable()) //cannot enable 4th player before 3rd
    		return;
    	if (player4box.isDisable()) { // add fourth player
    		player4box.setDisable(false);
    		playersNum++;
    	}
    	else { // remove fourth player
    		player4box.setDisable(true);
    		playersNum--;
    	}
    }

    @FXML
    void thirdPlayer(MouseEvent event) {
    	if (!player3box.isDisable() && !player4box.isDisable()) // cannot disable 3rd player before 4th
    		return;
    	if (player3box.isDisable()) { // add third player
    		player3box.setDisable(false);
    		playersNum++;
    	}
    	else { // remove third player
    		player3box.setDisable(true);
    		playersNum--;
    	}
    }

    
    @FXML
    void backToDifficulty(ActionEvent event) {
    	newScreen("Difficulty");
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
    	//if (player1clr.getSelectionModel().getSelectedItem() ==
    	//Player p1 = new Player(player1txt.getText(), )
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


