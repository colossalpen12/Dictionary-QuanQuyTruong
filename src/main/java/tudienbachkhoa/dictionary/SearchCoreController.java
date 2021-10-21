package tudienbachkhoa.dictionary;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class SearchCoreController implements Initializable {

    @FXML
    private JFXButton BackButton = new JFXButton();
    @FXML
    private JFXButton addWordButton = new JFXButton();
    @FXML
    private JFXButton pronounceButton = new JFXButton();
    @FXML
    private JFXButton exitButton = new JFXButton();

    @FXML
    private TextField SearchBar = new TextField();

    @FXML
    private ChoiceBox<String> chooseDictionary = new ChoiceBox<>();

    @FXML
    private TextArea definition;

    @FXML
    private JFXSlider volumeSlider = new JFXSlider();

    Stage editDatabase = new Stage();

    private String meaning = null;

    /**
     * initialize the Dictionary
     **/
    private Dictionary DictionaryInput = new Dictionary();

    private final ObservableList<String> dictionaries = FXCollections.observableArrayList("English to English", "English to Vietnamese"
            , "Vietnamese to English", "Merriam-Webster");
    TextToSpeech Voice = new TextToSpeech();

    private List<String> SearchHistory;

    private final JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

    @Override
    /** initialize choice box and text field value. **/
    public void initialize(URL url, ResourceBundle rb) {
        try {
            volumeSlider.setValue(75);
            ImageView BackIcon = new ImageView(new Image(new FileInputStream("src/images/icons8-back-100.png")));
            BackIcon.preserveRatioProperty().set(true);
            BackIcon.fitHeightProperty().bind(exitButton.widthProperty());
            BackButton.setGraphic(BackIcon);
            ImageView addIcon = new ImageView(new Image(new FileInputStream("src/images/icons8-plus-+-150.png")));
            ImageView pronounceIcon = new ImageView(new Image(new FileInputStream("src/images/icons8-speaker-100.png")));
            ImageView exitIcon = new ImageView(new Image(new FileInputStream("src/images/icons8-exit-100.png")));
            addIcon.preserveRatioProperty().set(true);
            pronounceIcon.preserveRatioProperty().set(true);
            exitIcon.preserveRatioProperty().set(true);
            addIcon.fitWidthProperty().bind(addWordButton.widthProperty());
            pronounceIcon.fitHeightProperty().bind(pronounceButton.heightProperty());
            exitIcon.fitHeightProperty().bind(exitButton.widthProperty());
            addWordButton.setGraphic(addIcon);
            pronounceButton.setGraphic(pronounceIcon);
            exitButton.setGraphic(exitIcon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        chooseDictionary.getItems().addAll(dictionaries);
        chooseDictionary.setValue("English to English");
        DictionaryInput.Retrieve(chooseDictionary.getValue());
        if (SearchHistory != null) {
            autoCompletePopup.getSuggestions().addAll(SearchHistory);
        } else {
            autoCompletePopup.getSuggestions().addAll(DictionaryInput.ListOfWord.prefixMatching(SearchBar.getText().toLowerCase()));
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
        if (chooseDictionary.getValue().equals("Merriam-Webster")) {
            autoCompletePopup.hide();
            autoCompletePopup.getSuggestions().clear();
            autoCompletePopup.getSuggestions().addAll(WebScraping.getSuggestion(SearchBar.getText()));
            autoCompletePopup.show(SearchBar);
        } else {
            autoCompletePopup.hide();
            autoCompletePopup.getSuggestions().clear();
            autoCompletePopup.getSuggestions().addAll(DictionaryInput.ListOfWord.prefixMatching(SearchBar.getText().toLowerCase()));
            autoCompletePopup.show(SearchBar);
        }
    }


    public String definition() {
        if (chooseDictionary.getValue().equals("Merriam-Webster")) {
            return WebScraping.getDescription(SearchBar.getText());
        } else {
            if (!DictionaryInput.ListOfWord.search(SearchBar.getText()))
                return "Word not found";
            return DictionaryInput.WordMap.get(SearchBar.getText().toLowerCase());
        }
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
    public void openAddWord(ActionEvent e) throws IOException {
        if (chooseDictionary.getValue().equals("Merriam-Webster")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unsupported feature!");
            alert.setHeaderText("Merriam-Webster Dictionary does not support database interference.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Ố kê");
                }
            });
        } else {
            EditDatabaseController.dictionary = chooseDictionary.getValue();
            Parent root = FXMLLoader.load(getClass().getResource("addWord.fxml"));
            Scene abt = new Scene(root);
            abt.setFill(Color.TRANSPARENT);
            editDatabase.setTitle("Add/Remove or Edit database");
            editDatabase.setScene(abt);
            editDatabase.show();
        }
    }

    @FXML
    public void ExitApp(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    public void changeDictionary(ActionEvent event) throws IOException {
        if (chooseDictionary.getValue().equals("Merriam-Webster")) {
            if (isInternetReachable() == false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An internet connection error occurred!");
                alert.setHeaderText("Application cannot connect to Merriam-Webster!");
                alert.setContentText("Please check your internet connection!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Ố kê");
                    }
                });
            }
        } else {
            DictionaryInput.Retrieve(chooseDictionary.getValue());
        }
    }

    @FXML
    public void Pronounce(ActionEvent event) throws IOException {
        if (!chooseDictionary.getValue().equals("Vietnamese to English") && DictionaryInput.ListOfWord.search(SearchBar.getText())) {
            Voice.speak(SearchBar.getText());
        } else if (!chooseDictionary.getValue().equals("Vietnamese to English") && !DictionaryInput.ListOfWord.search(SearchBar.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Word not found!!");
            alert.setHeaderText("Word not found!!");
            alert.setContentText("We cannot find the word.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Ố kê");
                }
            });
        } else {
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

    @FXML
    public void ChangeVolume(MouseEvent event){
        Voice.SetVolume((float) volumeSlider.getValue());
    }

    public static boolean isInternetReachable()
    {
        try {
            URL url = new URL("https://www.merriam-webster.com/");
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            Object objData = urlConnect.getContent();
        } catch (Exception e) {
            System.out.println("Cannot connect to the online Dictionary (Merriam-Webster)");
            return false;
        }
        return true;
    }
}
