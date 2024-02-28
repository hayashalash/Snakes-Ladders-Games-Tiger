package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class Methods {
	// Class for methods shared across all screens
	
	String buttonStyle = "-fx-background-color: #D2691E; " +  // Background color
            "-fx-text-fill: white; " +           // Text color
            "-fx-font-size: 14px; " +            // Font size
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
}
