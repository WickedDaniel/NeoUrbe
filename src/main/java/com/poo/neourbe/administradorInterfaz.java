package com.poo.neourbe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class administradorInterfaz extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NeoUrbeApp.class.getResource("adminInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Configuración");
        stage.setScene(scene);
        stage.show();

        ScrollPane parametrizacionPanel = (ScrollPane)scene.lookup("#parametrizacionPanel");
        VBox stepOne = (VBox)scene.lookup("#configStepOne");
        VBox stepTwo = (VBox)scene.lookup("#configStepTwo");
    }
}
