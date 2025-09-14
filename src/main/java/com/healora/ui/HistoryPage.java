package com.healora.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.healora.DatabaseManager;

import java.util.List;

public class HistoryPage {

    // Data class for a history entry
    public static class HistoryEntry {
        String timestamp;
        String mood;
        String activity;

        public HistoryEntry(String timestamp, String mood, String activity) {
            this.timestamp = timestamp;
            this.mood = mood;
            this.activity = activity;
        }
    }

    public void showHistory(Stage stage, Scene mainScene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #9face6, #74ebd5);");

        // --- Glass card container ---
        VBox card = new VBox(20);
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.85);" +
                "-fx-background-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 15,0,0,5);"
        );

        // --- Top bar with Back + Title ---
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("⬅ Back");
        backBtn.setStyle("-fx-background-color: #4a6cf7; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-padding: 6 14; -fx-background-radius: 8;");
        backBtn.setOnAction(e -> {stage.setScene(mainScene);
        stage.setMaximized(true);});

        Label title = new Label("📜 My History");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; "
                + "-fx-font-family: 'Segoe UI', 'Georgia'; -fx-text-fill: #2c3e50;");
        
         Button refreshBtn = new Button("🔄 Refresh");
         refreshBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; "
            + "-fx-font-size: 13px; -fx-font-weight: bold; "
            + "-fx-padding: 5 12; -fx-background-radius: 8;");

        topBar.getChildren().addAll(backBtn, title, refreshBtn);
        card.getChildren().add(topBar);

        // --- History List ---
        ListView<HistoryEntry> listView = new ListView<>();
        listView.setPrefHeight(400);   // enough space to show items
        listView.setPrefWidth(550);

        ObservableList<HistoryEntry> items = FXCollections.observableArrayList();
        listView.setItems(items);

        Runnable loadActivities = () -> {
        items.clear();
        List<String> savedActivities = DatabaseManager.getAllSavedActivities();
        for (String entryStr : savedActivities) {
            try {
                int firstBracket = entryStr.indexOf("]");
                String timestamp = entryStr.substring(1, firstBracket);
                String rest = entryStr.substring(firstBracket + 2);
                String[] parts = rest.split(" → ");
                if (parts.length == 2) {
                    String mood = parts[0];
                    String activity = parts[1];
                    items.add(new HistoryEntry(timestamp, mood, activity));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        };

         // Load on startup
        loadActivities.run();

        // Refresh button reloads
        refreshBtn.setOnAction(e -> loadActivities.run());

        // Custom cell with aesthetic styling
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(HistoryEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("📅 " + entry.timestamp +
                            "\n😊 Mood: " + entry.mood +
                            "\n🎯 Activity: " + entry.activity);
                    
                                // text color via API instead of CSS
                    setTextFill(javafx.scene.paint.Color.web("#2c3e50"));

        
                    setStyle("-fx-padding: 12; -fx-font-size: 14px;" +
                            "-fx-background-radius: 10;" +
                            "-fx-border-color: #dfe6e9;" +
                            "-fx-font-color: #333" +
                            "-fx-border-radius: 10;");
                }
            }
        });

        // Hover effect
        listView.setStyle(
                "-fx-background-color: transparent;" // light teal hover
        );

        card.getChildren().add(listView);
        root.setCenter(card);

        Scene scene = new Scene(root, 650, 520);
        stage.setScene(scene);
        stage.setTitle("History");
        stage.setMaximized(true);
        stage.show();
    }
    
}
