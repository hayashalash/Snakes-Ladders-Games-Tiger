package Controller;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Color;
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
import javafx.scene.layout.VBox;

public class ChoosePlayersController implements Initializable{

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


