package Controller;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

	public static Stage mainWindow=null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			primaryStage.initStyle(StageStyle.DECORATED);
			Parent root = FXMLLoader.load(getClass().getResource("/View/ChoosePlayers.fxml"));
			Scene scene = new Scene(root, 600, 400);
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

	public static void Main(String[] args) throws IOException {
		launch(args);
//		Model.SysData.initializeMyFileWriter();
	}
	
}