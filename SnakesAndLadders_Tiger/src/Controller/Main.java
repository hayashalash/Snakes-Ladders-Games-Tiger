package Controller;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import Model.Difficulty;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

	public static Stage mainWindow=null;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			primaryStage.initStyle(StageStyle.UNDECORATED);
			Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
			Scene scene = new Scene(root, 852, 595);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
//			primaryStage.getIcons().add(new Image("/View/images/logo.png"));
			mainWindow=primaryStage;
			primaryStage.resizableProperty().setValue(false);
			primaryStage.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		launch(args);
		try {
			Model.SysData.getInstance().readFromJson();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void showQustion(Difficulty d) {
		//bring Hashmap from question difficulty and Arraylist of question for each difficulty 
		//choose the diificulty and bring random question from the arraylist 
	}
}
