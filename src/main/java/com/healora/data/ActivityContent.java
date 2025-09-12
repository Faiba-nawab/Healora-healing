package com.healora.data;

import javafx.scene.control.Label;
import java.util.*;

public class ActivityContent {
    private static final Map<String, List<String>> activities = new HashMap<>();

    static {
        activities.put("Happy", Arrays.asList(
            "Write down 3 things you’re grateful for 🌸",
            "Celebrate by calling or texting a friend 📞",
            "Dance to your favorite upbeat song 🎶",
            "Share your joy with someone you love 💕"
        ));

        activities.put("Sad", Arrays.asList(
            "Try journaling: 'Today I feel…' ✍️",
            "Listen to calming or uplifting music 🎧",
            "Take a short mindful walk outside 🚶‍♂️",
            "Write down one positive thing that happened 🌟"
        ));

        activities.put("Angry", Arrays.asList(
            "Do 10 deep breaths: Inhale 4s, Exhale 6s 🧘",
            "Write your feelings on paper and tear it ✍️🗑️",
            "Do a quick physical release: push-ups/jumping jacks 🏋️",
            "Drink a glass of cold water 💧"
        ));

        activities.put("Stressed", Arrays.asList(
            "Try a 5-min guided meditation 🕯️",
            "Do progressive muscle relaxation 💆",
            "Break your task into smaller steps 🗂️",
            "Write a short to-do list with only 3 priorities 📝"
        ));

        activities.put("Anxious", Arrays.asList(
            "Try the 5-4-3-2-1 grounding exercise 👀👂👃👄✋",
            "Stretch your neck & shoulders for 2 minutes 🧘",
            "Write one worry and one possible solution 💡",
            "Sip warm tea slowly ☕"
        ));

        activities.put("Tired", Arrays.asList(
            "Close your eyes and rest for 5 minutes 😴",
            "Drink a glass of water or healthy juice 💧",
            "Listen to relaxing background sounds 🌊",
            "Take a power nap if possible 🛌"
        ));

        activities.put("Neutral", Arrays.asList(
            "Read a short article or book 📚",
            "Listen to light instrumental music 🎶",
            "Do a quick stretch or walk 🚶‍♀️",
            "Plan one small thing to look forward to tomorrow ✨"
        ));
    }

    public static List<String> getActivities(String mood) {
        return activities.getOrDefault(
            capitalize(mood),
            Arrays.asList("Take a deep breath 🌿", "Drink some water 💧")
        );
    }

     // ✅ New method that returns styled Labels (emoji-safe)
    public static List<Label> getActivityLabels(String mood) {
        List<String> acts = getActivities(mood);
        List<Label> labels = new ArrayList<>();

        for (String act : acts) {
            Label lbl = new Label(act);
            lbl.getStyleClass().add("activity-label");
        }
        return labels;
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
