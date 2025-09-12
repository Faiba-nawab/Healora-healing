package com.healora.ui;

import com.healora.DatabaseManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MoodPane extends VBox {

    public interface MoodSelectionListener {
        void onMoodSelected(String mood);
    }

    public MoodPane(MoodSelectionListener listener) {
        setSpacing(12);
        setPadding(new Insets(24));

        Text heading = new Text("💭 How are you feeling today?");
        heading.getStyleClass().add("section-title");

        String[] moods = {"Happy", "Sad", "Angry", "Stressed", "Anxious", "Tired", "Neutral"};

        getChildren().add(heading);

        for (String mood : moods) {
            Button btn = new Button(mood);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> {
                DatabaseManager.saveMood(mood);  // Save selected mood
                if (listener != null) {
                    listener.onMoodSelected(mood); // Notify main app
                }
            });
            getChildren().add(btn);
        }
    }
}

