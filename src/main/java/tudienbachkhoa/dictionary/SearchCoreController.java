package tudienbachkhoa.dictionary;

import com.jfoenix.controls.*;
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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchCoreController implements Initializable {
    @FXML
    private TextField SearchBar;

    @FXML
    private ChoiceBox<String> chooseDictionary = new ChoiceBox<>();

    @FXML
    private TextArea definition;

    private String meaning = null;

    //initialize the Dict
    private Dictionary input = new Dictionary();
    private ObservableList<String> dictionaries = FXCollections.observableArrayList("English to English", "English to Vietnamese", "Vietnamese to English");
    AutoCompletionBinding initial;
    TextToSpeech demo = new TextToSpeech();

    private List<String> SearchHistory = null;

    private final JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

    @Override
    /** initialize choice box and text field value. **/
    public void initialize(URL url, ResourceBundle rb) {
        chooseDictionary.getItems().addAll(dictionaries);
        chooseDictionary.setValue("English to English");
        input.Retrieve(chooseDictionary.getValue());
        if (SearchHistory != null) {
            autoCompletePopup.getSuggestions().addAll(SearchHistory);
        } else {
            autoCompletePopup.getSuggestions().addAll(input.demo.prefixMatching(SearchBar.getText().toLowerCase()));
        }
        autoCompletePopup.setSelectionHandler(event -> {
            SearchBar.setText(event.getObject());
            definition.setText(definition());
            definition.requestFocus();
            // you can do other actions here when text completed
        });
    }

    @FXML //update autocomplete bar sau mỗi kí tự nhập
    public void onSearchAction(KeyEvent event) {
        autoCompletePopup.hide();
        autoCompletePopup.getSuggestions().clear();
        autoCompletePopup.getSuggestions().addAll(input.demo.prefixMatching(SearchBar.getText().toLowerCase()));
        autoCompletePopup.show(SearchBar);
    }


    public String definition() {
        if (!input.demo.search(SearchBar.getText()))
            return "Word not found";
        return input.demo_map.get(SearchBar.getText().toLowerCase());
    }

    @FXML
    public void PressEnter(ActionEvent event) {
        autoCompletePopup.hide();
        definition.setText(definition());
        definition.requestFocus();
    }


    /**
     * Scene Controller
     **/
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
    public void ExitApp(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    public void changeDictionary(ActionEvent event) throws IOException {
        input.Retrieve(chooseDictionary.getValue());
    }

    @FXML
    public void Pronounce(ActionEvent event) throws IOException {
        if (!chooseDictionary.getValue().equals("Vietnamese to English") && input.demo.search(SearchBar.getText())) {
            demo.speak(SearchBar.getText());
        }
        else if (!chooseDictionary.getValue().equals("Vietnamese to English") && !input.demo.search(SearchBar.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Word not found!!");
            alert.setHeaderText("Word not found!!");
            alert.setContentText("We cannot find the word.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Ố kê");
                }
            });
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unsupported Text-To-Speech occurred!!");
            alert.setHeaderText("Unsupported language");
            alert.setContentText("This program doesn't support Vietnamese to English pronunciation.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Ố kê");
                }
            });
        }
    }
}
