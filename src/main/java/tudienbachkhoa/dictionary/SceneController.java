package tudienbachkhoa.dictionary;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;

    @FXML
    public void switchToSettings(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OptionsView.fxml"));
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void OpenDictionary(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("DictionaryView.fxml"));
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void ExitApp(ActionEvent event) throws IOException{
        Platform.exit();
    }
}
