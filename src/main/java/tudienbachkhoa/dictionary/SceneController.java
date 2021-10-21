package tudienbachkhoa.dictionary;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class SceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Stage about = new Stage();
    @FXML
    private JFXButton exitButton = new JFXButton();
    @FXML
    private JFXButton aboutButton = new JFXButton();
    @FXML
    private JFXButton DictionaryButton = new JFXButton();
    @FXML
    private JFXButton GoogleTranslateButton = new JFXButton();
    @FXML
    private JFXButton SettingsButton = new JFXButton();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ImageView DictionaryIcon = new ImageView(new Image(new FileInputStream("src/images/dictionary.png")));
            DictionaryIcon.preserveRatioProperty().set(true);
            DictionaryIcon.setFitHeight(75);
            DictionaryIcon.setFitWidth(75);
            DictionaryButton.setGraphic(DictionaryIcon);

            ImageView GoogleTranslateIcon = new ImageView(new Image(new FileInputStream("src/images/translate.png")));
            GoogleTranslateIcon.preserveRatioProperty().set(true);
            GoogleTranslateIcon.setFitHeight(75);
            GoogleTranslateIcon.setFitWidth(75);
            GoogleTranslateButton.setGraphic(GoogleTranslateIcon);

            ImageView SettingsIcon = new ImageView(new Image(new FileInputStream("src/images/settings.png")));
            SettingsIcon.preserveRatioProperty().set(true);
            SettingsIcon.setFitHeight(75);
            SettingsIcon.setFitWidth(75);
            SettingsButton.setGraphic(SettingsIcon);

            ImageView exitIcon = new ImageView(new Image(new FileInputStream("src/images/icons8-exit-100.png")));
            exitIcon.preserveRatioProperty().set(true);
            exitIcon.fitHeightProperty().bind(exitButton.widthProperty());
            exitButton.setGraphic(exitIcon);

            ImageView aboutIcon = new ImageView(new Image(new FileInputStream("src/images/icons8-about-384.png")));
            aboutIcon.preserveRatioProperty().set(true);
            aboutIcon.fitHeightProperty().bind(exitButton.widthProperty());
            aboutButton.setGraphic(aboutIcon);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** All about switching scenes. **/
    @FXML
    public void OpenDictionary(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("DictionaryView.fxml"));
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        root.setOnMousePressed(pressEvent -> {
            root.setOnMouseDragged(dragEvent -> {
                stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openAbout(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));
        Scene abt = new Scene(root);
        abt.setFill(Color.TRANSPARENT);
        about.setTitle("About");
        about.setScene(abt);
        about.show();
    }

    public void switchToGoogleTranslate(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GoogleTranslateView.fxml"));
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        root.setOnMousePressed(pressEvent -> {
            root.setOnMouseDragged(dragEvent -> {
                stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void ExitApp(ActionEvent event) {
        Platform.exit();
    }
}
