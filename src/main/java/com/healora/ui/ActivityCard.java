package com.healora.ui;

import org.kordamp.ikonli.javafx.FontIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ActivityCard extends VBox {
    private Runnable onStart;

    // Emoji-based card
    public ActivityCard(String title, String desc, String cta, String emoji) {
        Text emojiText = new Text(emoji);
        // Force system emoji font
        emojiText.setStyle(
            "-fx-font-family: 'Segoe UI Emoji','Apple Color Emoji','Noto Color Emoji';" +
            "-fx-font-size: 28px;"
        );
        buildCard(title, desc, cta, emojiText, emoji);
    }

    // Icon-based card
     public ActivityCard(String title, String desc, String cta, FontIcon icon) {
        icon.getStyleClass().add("card-icon");
        buildCard(title, desc, cta, icon, cta);
    }

    /** Common builder for card layout */
    private void buildCard(String title, String desc, String cta, Object iconNode, String hexColor) {
        getStyleClass().add("card");
        setSpacing(10);
        setPadding(new Insets(18));
        setAlignment(Pos.TOP_LEFT);

        // Icon (emoji or FontIcon)
        if (iconNode instanceof Text) {
            ((Text) iconNode).getStyleClass().add("card-emoji");
        } else if (iconNode instanceof FontIcon) {
             
            FontIcon fi = (FontIcon) iconNode;
            fi.getStyleClass().add("card-icon");
            if (hexColor != null) {
                try {
                    fi.setIconColor(Color.web(hexColor)); // set original color
                } catch (Exception ignored) { }
            }
        }

        Text t = new Text(title);
        t.getStyleClass().add("card-title");

        Text d = new Text(desc);
        d.getStyleClass().add("card-desc");

        Button action = new Button(cta);
        action.getStyleClass().add("primary-btn");
        action.setOnAction(e -> { if (onStart != null) onStart.run(); });

        getChildren().addAll((javafx.scene.Node) iconNode, t, d, action);
    }

    public void setOnStart(Runnable onStart) {
        this.onStart = onStart;
    }
}
