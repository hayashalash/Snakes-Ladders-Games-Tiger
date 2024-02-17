package Controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Model.Difficulty;
import Model.Game;
import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class WinnerController implements Initializable{

    @FXML
    private AnchorPane screen;

    @FXML
    private ImageView celebrate;

    @FXML
    private Label winnerLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button playAgainButton;

    @FXML
    private Button home;


     @FXML
     void playAgain(ActionEvent event) {
    	 Difficulty diff = HardController.game.getType();
    	 if (diff == Difficulty.Easy) {
     		EasyController.game = new Game(diff, EasyController.game.getPlayers());
     		newScreen("easyBoard");
     	}
     	else if (diff == Difficulty.Medium) {
     		NormalController.game = new Game(diff, NormalController.game.getPlayers());
     		newScreen("normalBoard");
     	}
     	else if (diff == Difficulty.Hard) {
     		HardController.game = new Game(diff, HardController.game.getPlayers());
     		newScreen("hardBoard");
     	}
     }
     
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// Get the screen dimensions

        // Load the GIF file
    	Image i = new Image(getClass().getResourceAsStream("/img/icons/celebrating-tiger.gif"));
        // Set the loaded image to the existing ImageView (celebrate)
        celebrate.setImage(i);

        System.out.println("Image width: " + i.getWidth());
        System.out.println("Image height: " + i.getHeight());

        
        String congrats = "Congrats " + "haya" + " you won!";
        winnerLabel.setText(congrats);

        // Center the text in the screen
        winnerLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(winnerLabel, 0.0);
        AnchorPane.setRightAnchor(winnerLabel, 0.0);
        winnerLabel.setAlignment(Pos.CENTER);

        // Design the text
        winnerLabel.setStyle("-fx-text-fill: #cc8624; " +
                "-fx-font-size: 36px; " +
                "-fx-font-family: Broadway; ");
    }
	

    @FXML
    void returnHome(ActionEvent event) {
    	newScreen("Home");
    }

	 @FXML
    void entered(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1.1);
    	((Node)event.getSource()).setScaleY(1.1);
   
	 }
	 
    @FXML
    void exited(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1);
    	((Node)event.getSource()).setScaleY(1);
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
