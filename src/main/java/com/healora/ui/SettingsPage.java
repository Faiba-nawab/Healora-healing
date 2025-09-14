package com.healora.ui;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.prefs.Preferences;

public class SettingsPage {

    private final Preferences prefs = Preferences.userNodeForPackage(SettingsPage.class);
    private boolean darkTheme;

    public void showSettings(Stage stage, Scene mainScene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #9face6, #74ebd5);");

        // --- Glass card container ---
        VBox card = new VBox(25);
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.75);" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15,0,0,8);"
        );

        // --- Top bar with Back + Title ---
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("⬅ Back");
        backBtn.getStyleClass().add("primary-btn"); // CSS class instead of inline
        backBtn.setOnAction(e -> {
        stage.setScene(mainScene);
        stage.setMaximized(true);
        stage.show(); // 👈 keep main page full screen too
});


        Label title = new Label("⚙ Settings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-family: 'Segoe UI', 'Georgia';");

        topBar.getChildren().addAll(backBtn, title);
        card.getChildren().add(topBar);

        // --- Theme Toggle Switch ---
        HBox themeBox = new HBox(15);
        themeBox.setAlignment(Pos.CENTER_LEFT);

        Label themeLabel = new Label("🌗 Dark Theme");
        themeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2c3e50;");

        Rectangle toggleBackground = new Rectangle(55, 28, Color.LIGHTGRAY);
        toggleBackground.setArcWidth(25);
        toggleBackground.setArcHeight(25);
        toggleBackground.setStroke(Color.GRAY);
        toggleBackground.setStrokeWidth(1);

        Circle toggleCircle = new Circle(12, Color.WHITE);
        toggleCircle.setEffect(new DropShadow(5, Color.gray(0, 0.3)));

        HBox toggleWrapper = new HBox(toggleBackground, toggleCircle);
        toggleWrapper.setAlignment(Pos.CENTER_LEFT);
        toggleWrapper.setSpacing(-40); // overlap circle on background

        themeBox.getChildren().addAll(themeLabel, toggleWrapper);
        card.getChildren().add(themeBox);

        // --- Placeholder Options ---
        VBox optionsBox = new VBox(15);
        optionsBox.setAlignment(Pos.TOP_LEFT);
        optionsBox.setPadding(new Insets(15, 0, 0, 5));

        Label placeholder1 = new Label("🔔 Notifications (Coming soon)");
        placeholder1.setStyle("-fx-font-size: 15px; -fx-text-fill: #444;");

        Label placeholder2 = new Label("🔤 Font Size (Coming soon)");
        placeholder2.setStyle("-fx-font-size: 15px; -fx-text-fill: #444;");

        Label placeholder3 = new Label("🔒 Privacy Settings (Coming soon)");
        placeholder3.setStyle("-fx-font-size: 15px; -fx-text-fill: #444;");

        optionsBox.getChildren().addAll(placeholder1, placeholder2, placeholder3);
        card.getChildren().add(optionsBox);

        root.setCenter(card);

        Scene scene = new Scene(root, 520, 420);


        // Load saved theme
        darkTheme = prefs.getBoolean("darkTheme", false);
        applyTheme(darkTheme, scene, mainScene, root);
        updateToggle(toggleCircle, toggleBackground, darkTheme, false);  // 👈 added

        // Toggle theme
        themeBox.setOnMouseClicked(e -> {
            darkTheme = !darkTheme;
            prefs.putBoolean("darkTheme", darkTheme);
            applyTheme(darkTheme, scene, mainScene, root);
            updateToggle(toggleCircle, toggleBackground, darkTheme, true);
        });

        stage.setScene(scene);
        stage.setTitle("Settings");
        stage.setMaximized(true);
        stage.show();
    }

     private void updateToggle(Circle circle, Rectangle bg, boolean dark, boolean animate) {
        double targetX = dark ? 25 : 0;
        Color bgColor = dark ? Color.web("#34495e") : Color.LIGHTGRAY;

        if (animate) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(200), circle);
            transition.setToX(targetX);
            transition.play();
        } else {
            circle.setTranslateX(targetX);
        }

        bg.setFill(bgColor);
    }

     private void applyTheme(boolean dark, Scene settingsScene, Scene mainScene, BorderPane settingsRoot) {
        // Remove previous class
        settingsRoot.getStyleClass().remove("dark");
        mainScene.getRoot().getStyleClass().remove("dark");

        if(dark) {
            settingsRoot.getStyleClass().add("dark");
            mainScene.getRoot().getStyleClass().add("dark");
        }
    }
}
    
