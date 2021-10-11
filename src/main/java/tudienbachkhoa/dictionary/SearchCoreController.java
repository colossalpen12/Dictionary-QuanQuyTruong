package tudienbachkhoa.dictionary;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchCoreController implements Initializable {
    @FXML
    private TextField SearchBar;
    String[] suggestions = {"dit", "me", "quan", "hi", "hello", "how are you"};

    @FXML
    private ChoiceBox<String> chooseDictionary = new ChoiceBox<>();

    private ObservableList<String> dictionaries = FXCollections.observableArrayList("English to Vietnamese", "Vietnamese to English");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chooseDictionary.getItems().addAll(dictionaries);
        TextFields.bindAutoCompletion(SearchBar, suggestions);
        chooseDictionary.setValue("English to Vietnamese");
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

    public void ExitApp(ActionEvent event) throws IOException {
        Platform.exit();
    }
}
