package tudienbachkhoa.dictionary;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditDatabaseController implements Initializable {

    @FXML
    private JFXButton addButton = new JFXButton();
    @FXML
    private JFXButton removeButton = new JFXButton();
    @FXML
    private JFXButton applyButton = new JFXButton();

    public static String dictionary;
    @FXML
    private JFXTextArea edit = new JFXTextArea();
    @FXML
    private TextField input = new TextField();
    @FXML
    private JFXListView<String> WordList = new JFXListView<>();

    private Dictionary DictionaryInput = new Dictionary();

    private String SelectedWord = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DictionaryInput.Retrieve(dictionary);
        applyButton.disableProperty().set(true);
        removeButton.disableProperty().set(true);
        WordList.getItems().addAll(DictionaryInput.Dict);
        WordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            removeButton.disableProperty().set(false);
            SelectedWord = newValue;
            edit.setText(DictionaryInput.WordMap.get(SelectedWord));
            applyButton.disableProperty().set(true);
            edit.requestFocus();
            edit.textProperty().addListener((observableValue, s, s2) -> {
                if (s2 != s) {
                    applyButton.disableProperty().set(false);
                }
            });
        });
        //  addButton.disableProperty().set(true);
    }

    public String edit_definition() {
        if (!DictionaryInput.ListOfWord.search(input.getText()))
            return "Word not found";
        return DictionaryInput.WordMap.get(input.getText().toLowerCase());
    }

    @FXML
    public void edit_PressEnter(ActionEvent event) {
        edit.setText(edit_definition());
        edit.requestFocus();
    }

    @FXML
    public void addWord(ActionEvent event) {
        if (input.getText() == null || input.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No input found!!!");
            alert.setHeaderText("No input word found.");
            alert.setContentText("There are no characters on input.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Ố kê");
                }
            });
        } else if (DictionaryInput.ListOfWord.search(input.getText().toLowerCase())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("We found a match!");
            alert.setHeaderText("The word you entered has already existed");
            alert.setContentText("Do you want to replace the existing word?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                DictionaryInput.apply(input.getText().toLowerCase(), edit.getText(), dictionary);
                refreshWordList();
            }
        } else {
            DictionaryInput.insert(input.getText().toLowerCase(), edit.getText(), dictionary);
            refreshWordList();
        }
    }

    @FXML
    public void removeWord(ActionEvent event) {
        if (SelectedWord == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No selected word found!!!");
            alert.setHeaderText("No selected word found.");
            alert.setContentText("You have not selected any words.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Ố kê");
                }
            });
        } else {
            DictionaryInput.delete(SelectedWord, dictionary);
            WordList.getSelectionModel().clearSelection();
            removeButton.disableProperty().set(true);
            refreshWordList();
        }
    }

    @FXML
    public void applyChanges(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Apply changes to the original definition ");
        alert.setHeaderText("Are you sure you want to replace the original definition?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            DictionaryInput.apply(SelectedWord, edit.getText(), dictionary);
            refreshWordList();
        } else {
        }
        edit.setText(DictionaryInput.WordMap.get(SelectedWord));
        applyButton.disableProperty().set(true);
    }

    @FXML
    public void onKeyTyped(KeyEvent event) {
        WordList.getItems().clear();
        WordList.getItems().addAll(DictionaryInput.ListOfWord.prefixMatching(input.getText().toLowerCase()));
        WordList.refresh();
    }

    public void refreshWordList() {
        DictionaryInput.Retrieve(dictionary);
        WordList.getItems().clear();
        WordList.getItems().addAll(DictionaryInput.ListOfWord.prefixMatching(input.getText().toLowerCase()));
        WordList.refresh();
    }

}
