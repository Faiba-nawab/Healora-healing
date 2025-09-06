package com.healora.activities;

import java.util.Map;

public interface MoodStatsProvider {
    // returns counts of moods in last N days, e.g. {STRESSED:3, SAD:1}
    Map<MoodType,Integer> getRecentMoodCounts(int userId, int days);
}

