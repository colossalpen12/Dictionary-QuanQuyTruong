package tudienbachkhoa.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("Menu.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Quan-Quy-Truong Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}