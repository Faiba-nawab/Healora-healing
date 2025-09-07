package com.healora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:healora.db";

    static {
        initDatabase();
    }

    // Connect to DB
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create table if not exists
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
    }

    // Correct inner JournalEntry class
    public static class JournalEntry {
        private int page;
        private String date;
        private String content;

        public JournalEntry(int page, String date, String content) {
            this.page = page;
            this.date = date;
            this.content = content;
        }

        public int getPage() {
            return page;
        }

        public String getDate() {
            return date;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "📖 Page " + page + " (" + date + ") → " +
                   (content.length() > 30 ? content.substring(0, 30) + "..." : content);
        }
    }

    // Save or update journal entry
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
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int pageNum = rs.getInt("page_number");
                String date = rs.getString("date");
                String content = rs.getString("content");
                entries.add(new JournalEntry(pageNum, date, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return entries;
    }

    public static Connection get() {
       
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    // 🔹 Return all journal pages as "Page X: yyyy-mm-dd"
public static List<String> getAllJournalPages() {
    List<String> pages = new ArrayList<>();
    String sql = "SELECT page, date, content FROM journal ORDER BY page ASC";

    try (Connection conn = connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            int page = rs.getInt("page");
            String date = rs.getString("date");
            String content = rs.getString("content");

            // Format for list view
            pages.add("Page " + page + ": " + date + " — " +
                      (content.length() > 20 ? content.substring(0, 20) + "..." : content));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return pages;
}

// 🔹 Search journal pages by keyword
public static List<String> searchJournalPages(String keyword) {
    List<String> results = new ArrayList<>();
    String sql = "SELECT page, date, content FROM journal WHERE content LIKE ? OR date LIKE ? ORDER BY page ASC";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        String like = "%" + keyword + "%";
        pstmt.setString(1, like);
        pstmt.setString(2, like);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int page = rs.getInt("page");
            String date = rs.getString("date");
            String content = rs.getString("content");

            results.add("Page " + page + ": " + date + " — " +
                        (content.length() > 20 ? content.substring(0, 20) + "..." : content));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return results;
}


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

// 🗑 Delete journal entry from DB
public static void deleteJournalEntry(JournalEntry entry) {
    if (entry == null) return;

    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement("DELETE FROM journal WHERE page = ?")) {

        stmt.setInt(1, entry.getPage());
        stmt.executeUpdate();

        System.out.println("Deleted entry: Page " + entry.getPage());

    } catch (SQLException e) {
        e.printStackTrace();
    }
}



}
