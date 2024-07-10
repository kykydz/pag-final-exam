package org.app.uas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FileApps extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FileApps.class.getResource("file.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 438, 339);
        stage.setTitle("UAS Rizky Heri Saputra 232053001");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}