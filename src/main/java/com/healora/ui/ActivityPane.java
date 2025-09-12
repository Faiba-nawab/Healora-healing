package com.healora.ui;

import com.healora.DatabaseManager;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

import org.kordamp.ikonli.javafx.FontIcon;

import com.healora.data.ActivityContent;

public class ActivityPane extends VBox {

     public ActivityPane() {
        this(DatabaseManager.getRecentMood()); // delegates to main constructor
    }
    public ActivityPane(String mood) {
        getStyleClass().add("content-area");
        setSpacing(18);
        setPadding(new Insets(24));

         // Scrollable container
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");


        VBox container = new VBox(8);
        container.setPadding(new Insets(24));


        // Grid of activity cards
        GridPane coreGrid = new GridPane();
        coreGrid.setHgap(18);
        coreGrid.setVgap(18);

        int col = 0, row = 0;

        // 4 sample cards -> hook to your existing dialogs
        
        ActivityCard c1 = new ActivityCard("Guided Breathing", "Calm your nervous system with paced breathing", "Start", "🫁");
        c1.setOnStart(() -> {
            BreathingDialog dialog = new BreathingDialog();
            dialog.initOwner(AppStageProvider.primary());
            dialog.show();
        });

        ActivityCard c2 = new ActivityCard("Write Journal", "Reflect and release thoughts into words", "Write","📓" );
        c2.setOnStart(() -> JournalDialog.open(1));

        ActivityCard c3 = new ActivityCard("Relaxing Music", "Soothing sounds for focus or rest", "Play","🎶" );
        c3.setOnStart(() -> MusicLauncher.show());

        ActivityCard c4 = new ActivityCard("Mindful Break", "Step away for 5 minutes. Breathe. Reset.", "Begin 5m", "⏲️");
        c4.setOnStart(() -> new SimpleTimerDialog(5).show(AppStageProvider.primary()));
        
        // Add core activities to grid
        ActivityCard[] coreCards = {c1, c2, c3, c4};
        for (ActivityCard card : coreCards) {
            coreGrid.add(card, col, row);
            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
        }

        // Wrap core grid in VBox with glass-card style
        VBox coreBox = new VBox(coreGrid);
        coreBox.setPadding(new Insets(16));
        coreBox.getStyleClass().add("card"); // ← gives glass effect

        TitledPane corePane = new TitledPane("🌟 Core Activities", coreGrid);
        corePane.setExpanded(true); 
        corePane.getStyleClass().add("section-title");// initially expanded
         // --- Recommended for current mood ---
         // Fetch activities for the mood from ActivityContent
         // and create cards similarly to above

        

        GridPane recGrid = new GridPane();
        recGrid.setHgap(18);
        recGrid.setVgap(18);

        col = 0;
        row = 0;

        List<ActivityCard> activities = ActivityContent.getActivityCards(mood);
        for (ActivityCard card : activities) {
        recGrid.add(card, col, row);

             col++;
             if (col > 1) {
             col = 0;
             row++;
             }
}
        VBox recBox = new VBox(recGrid);
        recBox.setPadding(new Insets(16));
        recBox.getStyleClass().add("card"); // ← glass effect

        TitledPane recPane = new TitledPane("✨ Recommended for " + mood, recGrid);
        recPane.setExpanded(true);
        recPane.getStyleClass().add("section-title");

        // Add both sections to scrollable container
        container.getChildren().addAll(corePane, recPane);
        scrollPane.setContent(container);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        
         // Add scroll pane to main VBox
        getChildren().add(scrollPane);
    }
}