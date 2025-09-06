package com.healora.activities;

import com.healora.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealingActivityDAO {
    public List<HealingActivity> findByMood(MoodType mood, int limit) {
        String sql = "SELECT activity_id,title,type,mood_tag,difficulty,duration_sec,content,active FROM healing_activities WHERE active=1 AND mood_tag=? ORDER BY difficulty ASC, activity_id DESC LIMIT ?";
        List<HealingActivity> list = new ArrayList<>();
        try (Connection c = DatabaseManager.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, mood.name());
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HealingActivity a = new HealingActivity();
                    a.id = rs.getInt("activity_id");
                    a.title = rs.getString("title");
                    a.type = ActivityType.valueOf(rs.getString("type"));
                    a.moodTag = MoodType.valueOf(rs.getString("mood_tag"));
                    a.difficulty = rs.getInt("difficulty");
                    a.durationSec = rs.getInt("duration_sec");
                    a.content = rs.getString("content");
                    a.active = rs.getInt("active") == 1;
                    list.add(a);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public HealingActivity findById(int id) {
        String sql = "SELECT activity_id,title,type,mood_tag,difficulty,duration_sec,content,active FROM healing_activities WHERE activity_id=?";
        try (Connection c = DatabaseManager.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HealingActivity a = new HealingActivity();
                    a.id = rs.getInt("activity_id");
                    a.title = rs.getString("title");
                    a.type = ActivityType.valueOf(rs.getString("type"));
                    a.moodTag = MoodType.valueOf(rs.getString("mood_tag"));
                    a.difficulty = rs.getInt("difficulty");
                    a.durationSec = rs.getInt("duration_sec");
                    a.content = rs.getString("content");
                    a.active = rs.getInt("active") == 1;
                    return a;
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }
}

