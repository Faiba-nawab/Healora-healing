package com.healora.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;

public class ActivityPane extends VBox {

    public ActivityPane() {
        getStyleClass().add("content-area");
        setSpacing(18);
        setPadding(new Insets(24));

        Text heading = new Text("Recommended Activities");
        heading.getStyleClass().add("section-title");

        // Grid of activity cards
        GridPane grid = new GridPane();
        grid.setHgap(18);
        grid.setVgap(18);

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

        grid.add(c1, 0, 0);
        grid.add(c2, 1, 0);
        grid.add(c3, 0, 1);
        grid.add(c4, 1, 1);

        getChildren().addAll(heading, grid);
    }
}
