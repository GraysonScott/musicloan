package ca.graysonscott.musicloan;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAlbumController {
    private final MusicCatalogue catalogue;
    private final Stage stage;


    @FXML private TextField titleTxtField;
    @FXML private TextField artistTxtField;
    @FXML private TextField yearTextField;
    @FXML private ChoiceBox<GenreType> genreChoiceBox;

    public CreateAlbumController(MusicCatalogue catalogue, Stage stage) {
        this.catalogue = catalogue;
        this.stage = stage;
    }

    @FXML public void initialize() {
        genreChoiceBox.getItems().setAll(GenreType.values());
    }

    public void onCreate() {
        catalogue.addAlbum(titleTxtField.getText(), artistTxtField.getText(), genreChoiceBox.getValue(),
                Integer.parseInt(yearTextField.getText()));
        stage.close();
    }

    public void onCancel() {
        stage.close();
    }
}
