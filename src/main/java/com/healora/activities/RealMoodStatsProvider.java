package com.healora.activities;

import com.healora.DatabaseManager;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class RealMoodStatsProvider implements MoodStatsProvider {

    @Override
    public Map<MoodType, Integer> getRecentMoodCounts(int userId, int days) {
        Map<MoodType, Integer> counts = new HashMap<>();
        String sql = "SELECT mood_tag, COUNT(*) AS count FROM user_moods " +
                     "WHERE user_id=? AND date >= date('now','-" + days + " days') " +
                     "GROUP BY mood_tag";

        try (Connection c = DatabaseManager.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MoodType mood = MoodType.valueOf(rs.getString("mood_tag"));
                    counts.put(mood, rs.getInt("count"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return counts;
    }
}
