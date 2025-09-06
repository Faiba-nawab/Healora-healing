package com.healora;

import com.healora.ui.ActivityPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        // Root layout
        BorderPane root = new BorderPane();

        // Header
        Text title = new Text("Healora");
        title.getStyleClass().add("app-title");
        BorderPane header = new BorderPane();
        header.setCenter(title);
        header.getStyleClass().add("app-header");
        root.setTop(header);

        // Sidebar (placeholder)
        var sidebar = new com.healora.ui.Sidebar();
        root.setLeft(sidebar);

        // Center content: the nice-looking Activities grid
        var activities = new ActivityPane();
        root.setCenter(activities);

        Scene scene = new Scene(root, 980, 620);
        // attach CSS
        scene.getStylesheets().add(
            getClass().getResource("/styles/app.css").toExternalForm()
        );

        stage.setTitle("Healora — Mood & Healing Activities");
        stage.setScene(scene);
        com.healora.ui.AppStageProvider.setPrimary(stage);
        stage.show();
        
    }

    public static void main(String[] args) { launch(args); }
}
