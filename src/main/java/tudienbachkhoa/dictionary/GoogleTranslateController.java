package tudienbachkhoa.dictionary;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GoogleTranslateController implements Initializable {
    @FXML
    private ChoiceBox<String> chooseDictionary = new ChoiceBox<>();
    @FXML
    private TextField input;
    @FXML
    private Button translate;
    @FXML
    private TextArea output;
    @FXML
    private JFXButton exitButton = new JFXButton();
    private ObservableList<String> dictionaries = FXCollections.observableArrayList("English to Vietnamese", "Vietnamese to English");

    @Override  //initialize the choiceBox
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ImageView exitIcon = new ImageView(new Image(new FileInputStream("src/images/icons8-exit-100.png")));
            exitIcon.preserveRatioProperty().set(true);
            exitIcon.fitHeightProperty().bind(exitButton.widthProperty());
            exitButton.setGraphic(exitIcon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        chooseDictionary.getItems().addAll(dictionaries);
        chooseDictionary.setValue("English to Vietnamese");
    }

    public String trans() throws IOException {
        if (chooseDictionary.getValue().equals("English to Vietnamese")){
            return GoogleTranslateAPI.translate("en", "vi", input.getText());
        }
        else
            return GoogleTranslateAPI.translate("vi", "en", input.getText());
    }


    public void setTranslate(ActionEvent e) throws IOException {
        output.setText(trans());
    }

    public void pressEnter(KeyEvent event) throws IOException{
        if (event.getCode().equals(KeyCode.ENTER)) {
            output.setText(trans());
            output.requestFocus();
        }
    }

    @FXML
    public void switchToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
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
