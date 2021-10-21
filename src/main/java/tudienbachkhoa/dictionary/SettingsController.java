package tudienbachkhoa.dictionary;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private JFXToggleButton database;
    @FXML
    private JFXSlider volume;
    @FXML
    private JFXButton exitButton = new JFXButton();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ImageView exitIcon = new ImageView(new Image(new FileInputStream("src/images/icons8-exit-100.png")));
            exitIcon.preserveRatioProperty().set(true);
            exitIcon.fitHeightProperty().bind(exitButton.widthProperty());
            exitButton.setGraphic(exitIcon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        volume.setValue(config.volume);
    }

    @FXML
    public void switchToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void ExitApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void changeVolume(MouseEvent event){
    }
}
