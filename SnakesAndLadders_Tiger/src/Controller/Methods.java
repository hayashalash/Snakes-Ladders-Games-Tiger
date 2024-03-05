package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Methods {
	// Class for methods shared across all screens or multiple screens
	// An object of Methods exists in all controllers
	
	// Maintain theme by sharing the same button style
	String buttonStyle = "-fx-background-color: #D2691E; " +  // Background color
            "-fx-text-fill: white; " +           // Text color
            "-fx-font-size: 16px; " +            // Font size
            "-fx-font-family: Serif; " +         // Font family
            "-fx-background-radius: 5px; " +     // Background radius
            "-fx-border-radius: 5px; " +         // Border radius
            "-fx-border-color: #DEB887;" +      // Border color
    		"-fx-effect:  dropshadow( one-pass-box , black , 8 , 0.0 , 3 , 0 );"; // Drop shadow effect
	
	public String getButtonStyle() {
		return buttonStyle;
	}

	void newScreen(String path) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/"+path+".fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
			Main.mainWindow.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}  	
    }
	
    void entered(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1.1);
    	((Node)event.getSource()).setScaleY(1.1);
    	((Node)event.getSource()).setCursor(Cursor.HAND);
    }
    
    void exited(MouseEvent event){
    	((Node)event.getSource()).setScaleX(1);
    	((Node)event.getSource()).setScaleY(1);
    	((Node)event.getSource()).setCursor(Cursor.DEFAULT);
    }
    
    void turnOffOn(ActionEvent event, Button musicIcon) {
    	if (Main.note.isPlaying()) {
    		musicIcon.setOpacity(0.5);
    		Main.stopBackgroundMusic();
    	}
    	else {
    		musicIcon.setOpacity(1);
    		Main.resumeBackgroundMusic();
    	}
    }
}
