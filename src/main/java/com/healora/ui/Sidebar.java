package com.healora.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sidebar extends VBox {


    public Sidebar(Stage stage,Scene mainScene) {

        getStyleClass().add("sidebar");
        setSpacing(12);
        setPadding(new Insets(16));
        setAlignment(Pos.TOP_CENTER);

        Button home = nav("🏠 Home");
        Button activities = nav("🧘 Activities");
        Button history = nav("📈 History");
        Button settings = nav("⚙ Settings");

        // --- Button actions ---

        // Home button placeholder
        home.setOnAction(e -> {
            System.out.println("Go to Home Page");
        });

        // Activities button placeholder
        activities.setOnAction(e -> {
            System.out.println("Open Recommendations Page");
        });

        // History button → open HistoryPage
        history.setOnAction(e -> {
            HistoryPage historyPage = new HistoryPage();
            historyPage.showHistory(stage, mainScene);
        });

        // Settings button → open SettingsPage
        settings.setOnAction(e -> {
            SettingsPage settingsPage = new SettingsPage();
            settingsPage.showSettings(stage, mainScene);
        });

        getChildren().addAll(home, activities, history, settings);
    }

    private Button nav(String text) {
        Button b = new Button(text);
        b.getStyleClass().add("nav-btn");
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle("-fx-font-family: 'Segoe UI Emoji', 'Apple Color Emoji', 'Noto Color Emoji', 'Arial'; " +
                   "-fx-font-size: 16; -fx-alignment: CENTER_LEFT;");
        return b;
    }
}

