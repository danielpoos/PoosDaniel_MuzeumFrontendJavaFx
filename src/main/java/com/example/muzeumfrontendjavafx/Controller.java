package com.example.muzeumfrontendjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {
    protected Stage stage;

    public Stage getStage() {
        return stage;
    }

    public static Controller newStage(String fxml, String title) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        Controller controller = fxmlLoader.getController();
        controller.stage = stage;
        return controller;
    }
}
