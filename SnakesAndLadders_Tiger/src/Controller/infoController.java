package Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import View.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class infoController implements Initializable {
	@FXML
	private AnchorPane anchor;

	@FXML
	private Button closeButton;

    @FXML
    private Button homeButton;

    @FXML
    void closeStage(ActionEvent event) {
    	if (Alerts.exit()==1)
			Main.mainWindow.close();
    }

    public Button getHomeButton() {
		return homeButton;
	}

	public void setHomeButton(Button homeButton) {
		this.homeButton = homeButton;
	}

	@FXML
    private void goHome() {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
			Scene scene = new Scene(root);
			Main.mainWindow.setScene(scene);
			Main.mainWindow.show();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
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
	
}
