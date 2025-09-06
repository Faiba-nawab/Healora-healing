package com.healora.activities;

public class HealingActivity {
    public int id;
    public String title;
    public ActivityType type;
    public MoodType moodTag;
    public int difficulty;
    public int durationSec;
    public String content;
    public boolean active;

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - %ds", id, title, type, durationSec);
    }
}

