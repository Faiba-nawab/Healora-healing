package com.healora.ui;

import javafx.stage.Stage;

public class AppStageProvider {
    private static Stage primary;
    public static void setPrimary(Stage s) { primary = s; }
    public static Stage primary() { return primary; }
}