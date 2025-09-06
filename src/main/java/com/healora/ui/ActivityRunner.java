package com.healora.ui;

import com.healora.activities.HealingActivity;
import com.healora.activities.ActivityType;

public class ActivityRunner {
    public static void run(HealingActivity a) {
        if (a == null) return;
        ActivityType t = a.type;
        switch (t) {
            case BREATHING -> BreathingDialog.start(a);
            case JOURNAL -> JournalDialog.open(a);
            case QUOTE -> {
                // show as info alert
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    alert.setHeaderText(a.title);
                    alert.setContentText(a.content);
                    alert.showAndWait();
                });
            }
            case MUSIC -> MusicLauncher.openLink(a.content);
            case EXERCISE -> SimpleTimerDialog.start(a.title, a.durationSec, a.content);
        }
    }
}
