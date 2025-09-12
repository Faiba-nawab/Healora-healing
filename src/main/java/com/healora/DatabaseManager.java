package com.healora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:healora.db";

    static {
        initDatabase();
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create correct table
    public static void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS journal_entries (" +
                "page_number INTEGER PRIMARY KEY, " +
                "date TEXT NOT NULL, " +
                "content TEXT NOT NULL)";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
        String sqlMoods = "CREATE TABLE IF NOT EXISTS moods (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "mood TEXT NOT NULL, " +
            "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
        // Create activities_log table if not exists
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlMoods);
        } catch (SQLException e) {
            e.printStackTrace();
        }

       String activitySql = "CREATE TABLE IF NOT EXISTS activities_log (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "mood TEXT NOT NULL, " +
        "activity TEXT NOT NULL, " +
        "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
        ")";
     try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
    stmt.execute(activitySql);
} catch (SQLException e) {
    e.printStackTrace();
}

    }

    // Data model
    public static class JournalEntry {
        private int page;
        private String date;
        private String content;

        public JournalEntry(int page, String date, String content) {
            this.page = page;
            this.date = date;
            this.content = content;
        }

        public int getPage() { return page; }
        public String getDate() { return date; }
        public String getContent() { return content; }

        @Override
        public String toString() {
            return "📖 Page " + page + " (" + date + ") → " +
                   (content.length() > 30 ? content.substring(0, 30) + "..." : content);
        }

        public Object[] split(String string) {
            throw new UnsupportedOperationException("Unimplemented method 'split'");
        }
    }

    // Save/update
    public static void saveJournalEntry(int page, String date, String content) {
        String sql = "INSERT INTO journal_entries(page_number, date, content) VALUES(?,?,?) " +
                "ON CONFLICT(page_number) DO UPDATE SET date=excluded.date, content=excluded.content";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, page);
            pstmt.setString(2, date);
            pstmt.setString(3, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all entries
    public static List<JournalEntry> getAllEntries() {
        List<JournalEntry> entries = new ArrayList<>();
        String sql = "SELECT page_number, date, content FROM journal_entries ORDER BY page_number ASC";
        try (Connection conn = connect(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entries.add(new JournalEntry(
                        rs.getInt("page_number"),
                        rs.getString("date"),
                        rs.getString("content")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

        /** Insert a new journal entry */
    public static void addJournalEntry(int page, String date, String content) {
        String sql = "INSERT OR REPLACE INTO journal (page, date, content) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, page);
            pstmt.setString(2, date);
            pstmt.setString(3, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Get single entry
    public static JournalEntry getJournalEntry(int pageNumber) {
        String sql = "SELECT page_number, date, content FROM journal_entries WHERE page_number=?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pageNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new JournalEntry(
                        rs.getInt("page_number"),
                        rs.getString("date"),
                        rs.getString("content")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete entry
    public static void deleteJournalEntry(int page) {
        String sql = "DELETE FROM journal_entries WHERE page_number=?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, page);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get preview strings
    public static List<String> getAllJournalPages() {
        List<String> pages = new ArrayList<>();
        String sql = "SELECT page_number, date, substr(content,1,30) as preview FROM journal_entries ORDER BY page_number ASC";
        try (Connection conn = connect(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int page = rs.getInt("page_number");
                String date = rs.getString("date");
                String preview = rs.getString("preview");
                pages.add("Page " + page + ": " + date + " — " + (preview == null ? "" : preview));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pages;
    }

    // Search by keyword
    public static List<String> searchJournalPages(String query) {
        List<String> results = new ArrayList<>();
        String sql = "SELECT page_number, date, substr(content,1,30) as preview FROM journal_entries " +
                "WHERE content LIKE ? OR date LIKE ? ORDER BY page_number ASC";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int page = rs.getInt("page_number");
                String date = rs.getString("date");
                String preview = rs.getString("preview");
                results.add("Page " + page + ": " + date + " — " + (preview == null ? "" : preview));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Get full content by page
    public static String getJournalContent(int selected) {
        String sql = "SELECT content FROM journal_entries WHERE page_number=?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selected);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("content");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Connection get() {
        
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    public static String getJournalContent(String selected) {
       
        throw new UnsupportedOperationException("Unimplemented method 'getJournalContent'");
    }
    
    // Save a mood selection
public static void saveMood(String mood) {
    String sql = "INSERT INTO moods(mood) VALUES(?)";
    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, mood);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static String getRecentMood() {
    String sql = "SELECT mood FROM moods ORDER BY timestamp DESC LIMIT 1";
    try (Connection conn = connect(); Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
            String m = rs.getString("mood");
            return m != null ? m : "Neutral";
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "Neutral";
}

    public static void saveActivity(String mood, String activity) {
    String sql = "INSERT INTO activities_log(mood, activity) VALUES(?, ?)";
    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, mood);
        pstmt.setString(2, activity);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    

    public static List<String> getAllSavedActivities() {
    List<String> activities = new ArrayList<>();
    String sql = "SELECT mood, activity, timestamp FROM activities_log ORDER BY timestamp DESC";
    try (Connection conn = connect(); Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            String mood = rs.getString("mood");
            String activity = rs.getString("activity");
            String time = rs.getString("timestamp");
            activities.add("[" + time + "] " + mood + " → " + activity);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return activities;
}
    
    public static void deleteActivity(int id) {
    String sql = "DELETE FROM activities_log WHERE id=?";
    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}
