package com.healora.activities;

import com.healora.DatabaseManager;
import java.sql.*;

public class QuoteDAO {
    public String randomQuote(MoodType mood) {
        String sql = "SELECT text FROM quotes WHERE mood_tag=? ORDER BY RANDOM() LIMIT 1";
        try (Connection c = DatabaseManager.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, mood.name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("text");
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }
}

