package com.healora.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;

public class MusicLauncher {
    public static void show() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Music for Healing");

        Button relaxBtn = new Button("Play Relaxing Music");
        relaxBtn.setOnAction(e -> openLink("https://www.youtube.com/watch?v=2OEL4P1Rz04"));

        Button focusBtn = new Button("Play Focus Music");
        focusBtn.setOnAction(e -> openLink("https://www.youtube.com/watch?v=wp43OdtAAkM"));

        VBox layout = new VBox(15, relaxBtn, focusBtn);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }

    static void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object play() {
        
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }
}
