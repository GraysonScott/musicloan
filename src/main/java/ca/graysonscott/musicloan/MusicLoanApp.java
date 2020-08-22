package ca.graysonscott.musicloan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicLoanApp extends Application {

    private AlbumDAO buildDAO() {
        return new MySQLAlbumDAO();
    }

    private MusicCatalogue buildCatalogue() {
        return new MusicCatalogue(buildDAO());
    }

    private CatalogueController buildController(Stage stage) {
        return new CatalogueController(buildCatalogue(), stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui.fxml"));
        loader.setControllerFactory(t -> buildController(primaryStage));
        primaryStage.setTitle("Music Catalogue");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
