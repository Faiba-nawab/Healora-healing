package com.healora.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;

public class MusicLauncher {
    public static void show() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("🎶 Music for Healing 🎶");

        // --- Heading ---
        Label heading = new Label("✨ Choose Your Healing Vibes ✨");
        heading.setFont(Font.font("Segoe Script", 20));
        heading.setTextFill(Color.web("#4a148c")); // Deep purple
        heading.setEffect(new DropShadow(5, Color.LIGHTGRAY));

        // --- Buttons ---
        Button relaxBtn = new Button("🌙 Relaxing Music");
        relaxBtn.setOnAction(e -> openLink("https://www.youtube.com/watch?v=2OEL4P1Rz04")); // Relax

        Button focusBtn = new Button("🎧 Deep Focus Beats");
        focusBtn.setOnAction(e -> openLink("https://www.youtube.com/watch?v=jfKfPfyJRdk")); // Lofi Focus

        Button sleepBtn = new Button("💤 Sleep Music");
        sleepBtn.setOnAction(e -> openLink("https://www.youtube.com/watch?v=1ZYbU82GVz4")); // Sleep

        // Style buttons
        styleButton(relaxBtn, "#43cea2", "#185a9d"); // teal-blue
        styleButton(focusBtn, "#ff6a00", "#ee0979"); // orange-pink
        styleButton(sleepBtn, "#36d1dc", "#5b86e5"); // cyan-indigo

        // --- Layout ---
        VBox layout = new VBox(20, heading, relaxBtn, focusBtn, sleepBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        // Gradient Background
        layout.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(
                        new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#fbc2eb")),   // pink
                                new Stop(1, Color.web("#a6c1ee"))    // light blue
                        ),
                        new CornerRadii(20), Insets.EMPTY
                )));

        Scene scene = new Scene(layout, 400, 500);
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

    private static void styleButton(Button btn, String color1, String color2) {
        btn.setFont(Font.font("Lucida Handwriting", 16));
        btn.setStyle("-fx-background-color: linear-gradient(" + color1 + "," + color2 + ");"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 15;");
        btn.setPrefWidth(220);
        btn.setPrefHeight(45);

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: linear-gradient(" + color2 + "," + color1 + ");"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 15;"));

        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: linear-gradient(" + color1 + "," + color2 + ");"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 15;"));
    }

    public Object play() {
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }
}
