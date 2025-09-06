package com.healora.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StretchingDialog {
    private final Stage window;

    public StretchingDialog(Stage parent) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(parent);
        window.setTitle("Stretching Exercise");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label instruction = new Label("🌿 Quick Stretch:\n\n" +
            "1. Stand up and roll your shoulders.\n" +
            "2. Stretch arms overhead and breathe deeply.\n" +
            "3. Touch your toes slowly, hold for 10 seconds.\n" +
            "4. Roll your neck gently side to side.\n\n" +
            "Repeat twice for relaxation!");

        layout.getChildren().add(instruction);

        Scene scene = new Scene(layout, 350, 250);
        window.setScene(scene);
    }

    public void show() {
        window.showAndWait();
    }
}
