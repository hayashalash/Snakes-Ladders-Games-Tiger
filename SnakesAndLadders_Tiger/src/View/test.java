package View;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class test extends Application {

    private static final String ADD_ICON = "/img/icons/add.png";
    private static final String MINUS_ICON = "/img/icons/minus.png";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Toggle Input Example");

        Button toggleButton = createIconButton(ADD_ICON);
        TextField textField = new TextField();
        textField.setVisible(false);

        toggleButton.setOnAction(e -> {
            if (toggleButton.getGraphic().getUserData() == ADD_ICON) {
                toggleButton.setGraphic(createIcon(MINUS_ICON));
                textField.setVisible(true);
            } else {
                toggleButton.setGraphic(createIcon(ADD_ICON));
                textField.setText("");
                textField.setVisible(false);
            }
        });

        HBox root = new HBox(10);
        root.getChildren().addAll(toggleButton, textField);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 300, 100);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createIconButton(String iconFileName) {
        Button button = new Button("", createIcon(iconFileName));
        return button;
    }

    private ImageView createIcon(String iconFileName) {
        Image iconImage = new Image(getClass().getResourceAsStream(iconFileName));
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageView.setUserData(iconFileName); // Set user data to identify the icon
        return imageView;
    }
}
