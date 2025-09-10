package com.healora.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {
    public Sidebar() {
        getStyleClass().add("sidebar");
        setSpacing(12);
        setPadding(new Insets(16));
        setAlignment(Pos.TOP_CENTER);

        Button home = nav("🏠 Home");
        Button activities = nav("🧘 Activities");
        Button history = nav("📈 History");
        Button settings = nav("⚙ Settings");

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

