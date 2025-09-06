package com.healora.ui;

import com.healora.DatabaseManager;
import com.healora.DatabaseManager.JournalEntry;

import javafx.fxml.FXMLLoader;
import com.healora.activities.HealingActivity;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.List;

public class JournalDialog {

    private int pageNumber ;  // current page
    private static Label pageLabel;
    private static TextArea journalArea;
    private BorderPane rootPane;

     public JournalDialog(int startPage) {
        this.pageNumber = startPage > 0 ? startPage : 1;
        createAndShowDialog();
    }

    private void createAndShowDialog() {
        Stage stage = new Stage();
        stage.setTitle("📓 Journal");

        // --- Top Section ---
        Label title = new Label("📓 Journal");
        title.getStyleClass().add("journal-title");

        Label dateLabel = new Label("Date: " + LocalDate.now());
        dateLabel.getStyleClass().add("journal-meta");
        pageLabel = new Label("Page " + pageNumber);

        HBox topBox = new HBox(20, title, dateLabel, pageLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        // --- Center Section (Journal Text) ---
        journalArea = new TextArea();
        journalArea.setPrefSize(500, 400);
        journalArea.setWrapText(true);
        journalArea.getStyleClass().add("journal-textarea");

        loadPage(pageNumber);


        // --- Bottom Section (Buttons) ---
        Button saveBtn = new Button("💾 Save");
        saveBtn.getStyleClass().add("journal-button");
        Button nextBtn = new Button("➡ Next Page");
        Button prevBtn = new Button("⬅ Previous Page");
        Button indexBtn = new Button("📑 Index");

         HBox bottomBox = new HBox(15, prevBtn, nextBtn, saveBtn, indexBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        // --- Root Layout ---
        rootPane = new BorderPane();
        rootPane.getStyleClass().add("journal-dialog");
        rootPane.setTop(topBox);
        rootPane.setCenter(journalArea);
        rootPane.setBottom(bottomBox);

        loadPage(pageNumber);

        saveBtn.setOnAction(e -> {
            DatabaseManager.saveJournalEntry(pageNumber, LocalDate.now().toString(), journalArea.getText());
            showAlert("Saved", "Your entry for Page " + pageNumber + " was saved.");
        });

        nextBtn.setOnAction(e -> loadPage(pageNumber + 1));

        prevBtn.setOnAction(e -> {
            if (pageNumber > 1) loadPage(pageNumber - 1);
            animatePageFlip((Pane) stage.getScene().getRoot());
            
        });

        indexBtn.setOnAction(e -> { showIndex();
});


        Scene scene = new Scene(rootPane, 600, 500);
        scene.getStylesheets().add(JournalDialog.class.getResource("/styles/app.css").toExternalForm()); // <-- Add this line
        stage.setScene(scene);
        stage.show();
    }
     private void loadPage(int page) {
        pageNumber = page;
        pageLabel.setText("Page " + pageNumber);
        JournalEntry entry = DatabaseManager.getJournalEntry(pageNumber);
        journalArea.setText(entry != null ? entry.getContent() : "");
        animatePageFlip(rootPane);
        }
    private void animatePageFlip(Pane pageRoot) {
    pageRoot.setEffect(new DropShadow(10, Color.gray(0, 0.5)));
    RotateTransition rotate = new RotateTransition(Duration.millis(350), pageRoot);
    rotate.setAxis(javafx.scene.transform.Rotate.Y_AXIS); // Y-axis flip
    rotate.setFromAngle(0);
    rotate.setToAngle(180);
    rotate.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);

    ScaleTransition scale = new ScaleTransition(Duration.millis(350), pageRoot);
    scale.setFromX(1.0);
    scale.setToX(0.95);
    scale.setAutoReverse(true);
    scale.setCycleCount(2);

    ParallelTransition flip = new ParallelTransition(rotate, scale);
    flip.setOnFinished(e -> { pageRoot.setRotate(0);
        pageRoot.setEffect(null);
    });
    flip.play();
}


    private void showIndex() {
    Stage indexStage = new Stage();
    indexStage.setTitle("📑 Journal Index");

    // --- Title ---
    Label header = new Label("📑 Your Journal Pages");
    header.getStyleClass().add("index-title");

    // --- Search Bar ---
    TextField searchField = new TextField();
    searchField.setPromptText("🔍 Search your journal...");
    searchField.getStyleClass().add("index-search");

    // --- List View ---
    ListView<String> listView = new ListView<>();
    listView.setPrefHeight(350);
    listView.getStyleClass().add("index-list");

    // Load all pages
    List<String> allPages = DatabaseManager.getAllJournalPages();
    listView.getItems().setAll(allPages);

    // Filter results when typing
    searchField.textProperty().addListener((obs, oldVal, newVal) -> {
        if (newVal.isEmpty()) {
            listView.getItems().setAll(allPages);
        } else {
            List<String> filtered = DatabaseManager.searchJournalPages(newVal);
            listView.getItems().setAll(filtered);
        }
    });

    // Double-click → open page
    listView.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2) {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int selectedPage = Integer.parseInt(selected.split(":")[0].replace("Page ", "").trim());
                loadPage(selectedPage);
                indexStage.close();
            }
        }
    });

    VBox box = new VBox(15, header, searchField, listView);
    box.setPadding(new Insets(15));
    box.setAlignment(Pos.CENTER);
    box.getStyleClass().add("index-container");

    Scene scene = new Scene(box, 360, 500);
    scene.getStylesheets().add(JournalDialog.class.getResource("/styles/app.css").toExternalForm());
    indexStage.setScene(scene);
    indexStage.show();
}


    private static void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public static void open(int pageToOpen) {
        new JournalDialog(pageToOpen);
    }

    public static void open(HealingActivity a) {
        new JournalDialog(1); // default first page
    } // or return stage if you want
}

