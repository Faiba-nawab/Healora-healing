package com.healora.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class SimpleTimerDialog {

    private int seconds; // total time in seconds
    private Timeline timeline;
    private static Label timerLabel;
    private Button startBtn, pauseBtn;

    public SimpleTimerDialog(int minutes) {
        this.seconds = minutes * 60;
    }

    public void show(Window parentStage) {
        Stage dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Healing Timer");
        
        // --- Stretching Instructions Heading ---
        Label heading = new Label("🌿 Quick Stretch");
        heading.setFont(Font.font("Harrington", 28));   // Elegant font for heading
        heading.setTextFill(Color.web("#9f712bff"));      // Warm orange-gold
        heading.setAlignment(Pos.CENTER);
        // --- Stretching Instructions ---
        Label instruction = new Label(
                        "1. Stand up and roll your shoulders.\n" +
                        "2. Stretch arms overhead and breathe deeply.\n" +
                        "3. Touch your toes slowly, hold for 10 seconds.\n" +
                        "4. Roll your neck gently side to side.\n\n" +
                        "✨ Repeat twice for relaxation!"
        );
        instruction.setWrapText(true);
        instruction.setFont(Font.font( "Lucida Handwriting", 14));  // Stylish cursive font
        instruction.setTextFill(Color.web("#5c2d2dff"));      
        instruction.setAlignment(Pos.CENTER);
        instruction.setMaxWidth(460);

        


        // --- Timer Label (Digital Clock Style) ---
        try {
            timerLabel = new Label(formatTime(seconds));
        } catch (Exception e) {
           
        }
        timerLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 48));
        timerLabel.setTextFill(Color.WHITE);
        timerLabel.setEffect(new DropShadow(10, Color.DARKSLATEBLUE));

        // --- Buttons ---
        startBtn = new Button("▶ Start");
        pauseBtn = new Button("⏸ Pause");
        Button resetBtn = new Button("⟳ Reset");

        styleButton(startBtn, "#43cea2", "#185a9d");
        styleButton(pauseBtn, "#ff6a00", "#ee0979");
        styleButton(resetBtn, "#36d1dc", "#5b86e5");

        startBtn.setOnAction(e -> startTimer());
        pauseBtn.setOnAction(e -> pauseTimer());
        resetBtn.setOnAction(e -> resetTimer());

        HBox buttonBox = new HBox(15, startBtn, pauseBtn, resetBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // --- Layout ---
        VBox vbox = new VBox(20, heading, instruction, timerLabel, buttonBox);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane(vbox);
        root.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-background-color: linear-gradient(to bottom right, #fff3e0, #f9d79bff, #fcc0adff); -fx-border-color: #a27c42ff ; -fx-border-width: 3"); // Gold border

        // Gradient Background (soft & matching)
        root.setBackground(new javafx.scene.layout.Background(
    new javafx.scene.layout.BackgroundFill(
            new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                   new Stop(0, Color.web("#fceabb")),   // pastel yellow
                    new Stop(0.5, Color.web("#f8b500")), // warm golden
                    new Stop(1, Color.web("#f6d365"))    // peach
            ),
            new CornerRadii(20), Insets.EMPTY
    )));



        Scene scene = new Scene(root, 400, 500);
        dialog.setScene(scene);
        dialog.show();
    }

    private void startTimer() {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) return;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (seconds > 0) {
                seconds--;
                timerLabel.setText(formatTime(seconds));
            } else {
                timeline.stop();
                timerLabel.setText("✨ Done!");
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    private void resetTimer() {
        // Reset the timer to the original value (for example, 5 minutes)
        this.seconds = 5 * 60;
        timerLabel.setText(formatTime(this.seconds));
        if (timeline != null) {
            timeline.stop();
        }
    }

    private static String formatTime(int totalSeconds) {
        int mins = totalSeconds / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    private void styleButton(Button btn, String color1, String color2) {
        btn.setStyle("-fx-background-color: linear-gradient(" + color1 + "," + color2 + ");"
                + "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-background-radius: 10;");
        btn.setPrefWidth(100);
        btn.setPrefHeight(40);

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: linear-gradient(" + color2 + "," + color1 + ");"
                + "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-background-radius: 10;"));

        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: linear-gradient(" + color1 + "," + color2 + ");"
                + "-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-background-radius: 10;"));
    }

    public static Object start(String title, int durationSec, String content) {

        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    public static Object show(int i) {
       
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }
}
