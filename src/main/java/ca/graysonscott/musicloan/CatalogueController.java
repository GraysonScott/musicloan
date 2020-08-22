package ca.graysonscott.musicloan;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CatalogueController {
    @FXML private TextField searchField;
    @FXML private TableView<Album> albumTable;
    @FXML private TableColumn<Album, Long> idCol;
    @FXML private TableColumn<Album, String> nameCol;
    @FXML private TableColumn<Album, String> artistCol;
    @FXML private TableColumn<Album, GenreType> genreCol;
    @FXML private TableColumn<Album, String> yearCol;
    @FXML private TableColumn<Album, Boolean> availableCol;
    @FXML private ChoiceBox<AlbumSearchType> choiceBox;

    private final MusicCatalogue catalogue;
    private final Stage stage;

    public CatalogueController(MusicCatalogue catalogue, Stage stage) {
        this.catalogue = catalogue;
        this.stage = stage;
        stage.setOnCloseRequest(e -> catalogue.close());
    }

    public void initialize() {
        choiceBox.getItems().setAll(AlbumSearchType.values());
        choiceBox.getSelectionModel().selectFirst();
        idCol.setCellValueFactory(new PropertyValueFactory<Album,Long>("uniqueID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Album, String>("name"));
        artistCol.setCellValueFactory(new PropertyValueFactory<Album, String>("artist"));
        genreCol.setCellValueFactory(new PropertyValueFactory<Album, GenreType>("genre"));
        yearCol.setCellValueFactory(new PropertyValueFactory<Album, String>("publishedYear"));
        availableCol.setCellValueFactory(new PropertyValueFactory<Album, Boolean>("available"));
        albumTable.setItems(FXCollections.observableList(catalogue.findAll()));
    }

    public void onSearch() {
        String param = searchField.getText();
        if (param.equals("")) {
            albumTable.setItems(FXCollections.observableList(catalogue.findAll()));
        } else {
            albumTable.setItems(FXCollections.observableList(catalogue.search(choiceBox.getValue(), param)));
        }
    }

    public void onLoan() {
        catalogue.loanAlbum(albumTable.getSelectionModel().getSelectedItem().getUniqueID());
        onSearch();
    }

    public void onReturn() {
        catalogue.returnAlbum(albumTable.getSelectionModel().getSelectedItem().getUniqueID());
        onSearch();
    }

    public void onCreate() throws IOException {
        final Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAlbum.fxml"));
        loader.setControllerFactory(t -> buildCreateAlbumController(popup));
        Parent root = loader.load();
        popup.setScene(new Scene(root));
        popup.setTitle("Create New Album");
        popup.showAndWait();
        onSearch();
    }

    public void onDelete() {
        catalogue.deleteAlbum(albumTable.getSelectionModel().getSelectedItem().getUniqueID());
        onSearch();
    }

    private CreateAlbumController buildCreateAlbumController(Stage popup){
        return new CreateAlbumController(catalogue, popup);
    }
}
