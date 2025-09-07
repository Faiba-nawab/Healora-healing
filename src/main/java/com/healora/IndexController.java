package com.healora;

import com.healora.DatabaseManager;
import com.healora.DatabaseManager.JournalEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

public class IndexController {

    @FXML
    private ListView<JournalEntry> listView;

    @FXML
    private TextField searchField;

    @FXML
    private Button viewButton;

    @FXML
    private Button deleteButton;

    private ObservableList<JournalEntry> journalEntries;

    @FXML
    public void initialize() {
        loadEntries();

        // Search field listener
        searchField.textProperty().addListener((obs, oldVal, newVal) -> searchEntries());

        // Buttons
        viewButton.setOnAction(e -> handleView());
        deleteButton.setOnAction(e -> handleDelete());

        // ListView display (page + date)
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(JournalEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText("📖 Page " + entry.getPage() + " — " + entry.getDate());
                }
            }
        });
    }

    /** Load all entries into the list */
    private void loadEntries() {
        journalEntries = FXCollections.observableArrayList(DatabaseManager.getAllEntries());
        listView.setItems(journalEntries);
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

    private void handleView() {
        JournalEntry selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showEntryDialog(selected);
        } else {
            showAlert("No Selection", "Please select an entry to view.");
        }
    }

    private void handleDelete() {
        JournalEntry selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DatabaseManager.deleteJournalEntry(selected);
            loadEntries();
            showAlert("Deleted", "Entry deleted successfully.");
        } else {
            showAlert("No Selection", "Please select an entry to delete.");
        }
    }

    /** Show full entry in dialog */
    private void showEntryDialog(JournalEntry entry) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Journal Entry");
        alert.setHeaderText("📖 Page " + entry.getPage() + " (" + entry.getDate() + ")");
        alert.setContentText(entry.getContent());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
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
