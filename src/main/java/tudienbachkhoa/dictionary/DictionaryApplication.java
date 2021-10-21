package tudienbachkhoa.dictionary;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/** this one loads the app. **/

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(DictionaryApplication.class.getResource("Menu.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        fxmlLoader.setOnMousePressed(pressEvent -> {
            fxmlLoader.setOnMouseDragged(dragEvent -> {
                stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        Scene scene = new Scene(fxmlLoader, 860, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Quan-Quy-Truong Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

/**
 *
 --add-exports=javafx.base/com.sun.javafx.event=org.controlsfx.control
 --add-exports
 javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix
 --add-exports
 javafx.controls/com.sun.javafx.scene.control=com.jfoenix
 --add-exports
 javafx.base/com.sun.javafx.binding=com.jfoenix
 --add-exports
 javafx.graphics/com.sun.javafx.stage=com.jfoenix
 --add-exports
 javafx.base/com.sun.javafx.event=com.jfoenix

 nếu không chạy được thì thêm tất cả chỗ kia vào running configuration
 */