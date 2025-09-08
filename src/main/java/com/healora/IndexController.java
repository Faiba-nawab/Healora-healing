package com.healora;

import java.util.List;

import com.healora.DatabaseManager;
import com.healora.DatabaseManager.JournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

public class IndexController {

    @FXML private ListView<JournalEntry> listView;

    @FXML private TextField searchField;

    @FXML private Button viewButton;

    @FXML private Button deleteButton;

    private ObservableList<JournalEntry> journalEntries;

    @FXML
    public void initialize() {
        loadEntries();

        // Search field listener
        searchField.textProperty().addListener((obs, oldVal, newVal) -> searchEntries());
    }

    /** Load all entries into the list */
    /** Load all entries into the list */
private void loadEntries() {
    journalEntries = FXCollections.observableArrayList(DatabaseManager.getAllEntries());

    // 🔹 Show "Page X: date - preview"
    listView.setItems(journalEntries);
    listView.setCellFactory(param -> new ListCell<JournalEntry>() {
        @Override
        protected void updateItem(JournalEntry entry, boolean empty) {
            super.updateItem(entry, empty);
            if (empty || entry == null) {
                setText(null);
            } else {
                String preview = entry.getContent().length() > 20
                        ? entry.getContent().substring(0, 20) + "..."
                        : entry.getContent();
                setText("Page " + entry.getPage() + " (" + entry.getDate() + ") - " + preview);
            }
        }
    });
}


    /** Search entries by keyword */
    private void searchEntries() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadEntries();
        } else {
            ObservableList<JournalEntry> results = FXCollections.observableArrayList();
            for (JournalEntry entry : DatabaseManager.getAllEntries()) {
                if (entry.getContent().toLowerCase().contains(keyword.toLowerCase())
                        || entry.getDate().contains(keyword)) {
                    results.add(entry);
                }
            }
            listView.setItems(results);
        }
    }
    @FXML
    private void handleView() {
        JournalEntry selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a journal entry to view.");
            return;
        }

        int pageNumber = extractPageNumber(selected);
        String content = DatabaseManager.getJournalContent(pageNumber);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Journal Entry");
        alert.setHeaderText("📖 Page " + pageNumber);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void handleDelete() {
        JournalEntry selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a journal entry to delete.");
            return;
        }

        int pageNumber = extractPageNumber(selected);

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete Page " + pageNumber + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Delete");
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            DatabaseManager.deleteJournalEntry(pageNumber);
            refreshList();
        }
    }

    // 🔄 Refresh list after delete
private void refreshList() {
        List<JournalEntry> entries = DatabaseManager.getAllEntries();
        journalEntries.setAll(entries);
        listView.setItems(journalEntries);
    }


    // Helper → Extract page number from "Page X: ..."
    private int extractPageNumber(JournalEntry selected) {
        try {
            String[] parts = (String[]) ((JournalEntry) selected.split(":")[0]).split(" ");
            return Integer.parseInt(parts[1]);
        } catch (Exception e) {
            return -1;
        }
    }
    
    /** Generic alert */
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
