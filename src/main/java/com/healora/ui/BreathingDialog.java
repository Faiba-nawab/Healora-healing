package com.healora.ui;

import com.healora.activities.HealingActivity;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

public class BreathingDialog extends Stage {
    private boolean isPaused = false;
    private Timeline timeline;

    private Text instruction;
    private Text roundCounter;
    private Circle circle;

    private int totalRounds = 5;  // configurable via settings
    private int currentRound = 0;

    public BreathingDialog() {
        setTitle("⚙ Breathing Exercise");
        initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color:\"#efe1cdff\"; -fx-background-radius: 20;");

        // Title
        Text title = new Text("Calm Breathing");
        title.getStyleClass().add("dialog-title");

        // Instruction ("Inhale / Exhale")
        instruction = new Text("Press Start");
        instruction.getStyleClass().add("dialog-instruction");

        // Circle animation
        circle = new Circle(90, Color.web("#e4c9a3ff", 0.8));
        circle.setStroke(Color.web("#4b6c40ff"));
        circle.setStrokeWidth(4);

        StackPane breathingCircle = new StackPane(circle, instruction);
        breathingCircle.setPrefSize(300, 300);
        breathingCircle.setAlignment(Pos.CENTER);

        // Round counter
        roundCounter = new Text("Rounds: 0 / " + totalRounds);
        roundCounter.getStyleClass().add("dialog-meta");

        // Buttons
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);

        Button startBtn = new Button("Start");
        startBtn.getStyleClass().add("Bre-button");
        Button pauseBtn = new Button("Pause");
        pauseBtn.getStyleClass().add("Bre-button");
        Button stopBtn = new Button("Stop");
        stopBtn.getStyleClass().add("Bre-button");
        FontIcon gearIcon = new FontIcon("fas-cog"); // FontAwesome gear
Button settingsBtn = new Button(" Settings", gearIcon);
        settingsBtn.getStyleClass().add("settings-btn");

        controls.getChildren().addAll(startBtn, pauseBtn, stopBtn, settingsBtn);

        root.getChildren().addAll(title, breathingCircle, roundCounter, controls);

        Scene scene = new Scene(root, 420, 520);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        setScene(scene);

        // Button actions
        startBtn.setOnAction(e -> startBreathing());
        pauseBtn.setOnAction(e -> togglePause());
        stopBtn.setOnAction(e -> stopBreathing());
        settingsBtn.setOnAction(e -> openSettings());
    }

    private void startBreathing() {
        stopBreathing(); // reset if running
        currentRound = 0;
        roundCounter.setText("Rounds: 0 / " + totalRounds);

        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> 
                instruction.setText("Inhale"),
                new KeyValue(circle.radiusProperty(), 80, Interpolator.EASE_BOTH)
                ),

                new KeyFrame(Duration.seconds(4),
                new KeyValue(circle.radiusProperty(), 150, Interpolator.EASE_BOTH) // expand
                ),

                new KeyFrame(Duration.seconds(4), e ->
                    instruction.setText("Exhale")
                ),
                    new KeyFrame(Duration.seconds(8),
                    new KeyValue(circle.radiusProperty(), 80, Interpolator.EASE_BOTH) // shrink back
                ),
                
                new KeyFrame(Duration.seconds(8), e -> {
                    currentRound++;
                    roundCounter.setText("Rounds: " + currentRound + " / " + totalRounds);

                    if (currentRound >= totalRounds) {
                        stopBreathing();
                        instruction.setText("Done ✅");
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void togglePause() {
        if (timeline == null) return;
        if (isPaused) {
            timeline.play();
            isPaused = false;
        } else {
            timeline.pause();
            isPaused = true;
        }
    }

    private void stopBreathing() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        instruction.setText("Press Start");
        roundCounter.setText("Rounds: 0 / " + totalRounds);
        circle.setRadius(80);
    }

    // Settings popup to choose total rounds
    private void openSettings() {
    Stage settingsStage = new Stage();
    settingsStage.setTitle("⚙ Breathing Settings");
    settingsStage.initModality(Modality.APPLICATION_MODAL);

    // Main container
    VBox box = new VBox(20);
    box.setPadding(new Insets(25));
    box.setAlignment(Pos.CENTER);
    box.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffecd2, #ec9c81ff);"
               + "-fx-background-radius: 20;"
               + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15,0,0,5);");

    // Title label
    Label label = new Label("🌬 Select number of rounds:");
    label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;"
                 + "-fx-font-family: 'Segoe UI', 'Lucida Handwriting', cursive;");

    // Choice box
    ChoiceBox<Integer> roundChoice = new ChoiceBox<>();
    roundChoice.getItems().addAll(3, 5, 8, 10, 15);
    roundChoice.setValue(totalRounds);
    roundChoice.setStyle("-fx-background-radius: 10;"
                       + "-fx-padding: 5 15 5 15;"
                       + "-fx-font-size: 14px;"
                       + "-fx-background-color: #ffffff;"
                       + "-fx-border-color: #ccc;"
                       + "-fx-border-radius: 10;");

    // Save button
    Button saveBtn = new Button("💾 Save");
    saveBtn.setStyle("-fx-background-color: linear-gradient(to right, #43cea2, #185a9d);"
                   + "-fx-text-fill: white; -fx-font-weight: bold;"
                   + "-fx-background-radius: 20; -fx-padding: 8 20 8 20;"
                   + "-fx-cursor: hand;");
    saveBtn.setOnMouseEntered(e -> saveBtn.setStyle("-fx-background-color: linear-gradient(to right, #185a9d, #43cea2);"
                                                  + "-fx-text-fill: white; -fx-font-weight: bold;"
                                                  + "-fx-background-radius: 20; -fx-padding: 8 20 8 20;"
                                                  + "-fx-cursor: hand;"));
    saveBtn.setOnMouseExited(e -> saveBtn.setStyle("-fx-background-color: linear-gradient(to right, #43cea2, #185a9d);"
                                                 + "-fx-text-fill: white; -fx-font-weight: bold;"
                                                 + "-fx-background-radius: 20; -fx-padding: 8 20 8 20;"
                                                 + "-fx-cursor: hand;"));

    saveBtn.setOnAction(e -> {
        totalRounds = roundChoice.getValue();
        roundCounter.setText("Rounds: 0 / " + totalRounds);
        settingsStage.close();
    });

    // Add everything
    box.getChildren().addAll(label, roundChoice, saveBtn);

    Scene scene = new Scene(box, 300, 200);
    settingsStage.setScene(scene);
    settingsStage.showAndWait();
}


    public static Object start(HealingActivity a) {
        
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}
