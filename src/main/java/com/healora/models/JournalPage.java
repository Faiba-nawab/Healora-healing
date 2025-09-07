package com.healora.models;

public class JournalPage {
    private int id;
    private String title;
    private String content;

    public JournalPage(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return id + ": " + title;
    }
}

