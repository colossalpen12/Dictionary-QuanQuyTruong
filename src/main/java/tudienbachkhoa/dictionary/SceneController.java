package tudienbachkhoa.dictionary;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;


    /** All about switching scenes. **/
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
    public void OpenDictionary(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("DictionaryView.fxml"));
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openAbout(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));
        Scene abt = new Scene(root);
        abt.setFill(Color.TRANSPARENT);
        Stage about = new Stage();
        about.setTitle("About");
        about.setScene(abt);
        about.show();
    }

    public void switchToGoogleTranslate(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GoogleTranslateView.fxml"));
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void ExitApp(ActionEvent event) {
        Platform.exit();
    }
}
