package com.healora.activities;

import java.util.Map;

public class DummyMoodStatsProvider implements MoodStatsProvider {
    @Override
    public Map<MoodType, Integer> getRecentMoodCounts(int userId, int days) {
        return Map.of(MoodType.STRESSED, 3, MoodType.SAD, 1);
    }
}
