package com.healora;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class Seeder {
    public static void runScript(String resourcePath) throws Exception {
        try (InputStream is = Seeder.class.getResourceAsStream(resourcePath)) {
            if (is == null) throw new RuntimeException("Resource not found: " + resourcePath);
            String sql = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            try (Connection c = DatabaseManager.get();
                 Statement s = c.createStatement()) {
                s.executeUpdate("PRAGMA foreign_keys = ON;");
                for (String stmt : sql.split(";")) {
                    if (stmt.trim().isEmpty()) continue;
                    s.execute(stmt);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Applying schema...");
        runScript("/db/schema.sql");
        System.out.println("Seeding data...");
        runScript("/db/seed.sql");
        System.out.println("DB schema & seed applied. healora.db created in project root.");
    }
}
