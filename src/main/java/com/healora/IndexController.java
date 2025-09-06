package com.healora;

import com.healora.DatabaseManager.JournalEntry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

        // When search field changes
        searchField.textProperty().addListener((obs, oldVal, newVal) -> searchEntries());

        // Wire buttons
        viewButton.setOnAction(e -> handleView());
        deleteButton.setOnAction(e -> handleDelete());
    }

    /** Load all entries into the list */
    private void loadEntries() {
        journalEntries = FXCollections.observableArrayList(DatabaseManager.getAllEntries());
        listView.setItems(journalEntries);
    }

    /** Search entries by keyword */
    private void searchEntries() {
        String keyword = searchField.getText().trim(); // ✅ must be String
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
        DatabaseManager.JournalEntry selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showEntryDialog(selected);
        } else {
            showAlert("No Selection", "Please select an entry to view.");
        }
    }

    
    private void handleDelete() {
        DatabaseManager.JournalEntry selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            listView.getItems().remove(selected);
            DatabaseManager.deleteJournalEntry(selected); // Remove from DB
            showAlert("Deleted", "Entry deleted successfully.");
        } else {
            showAlert("No Selection", "Please select an entry to delete.");
        }
    }

    /** Show full entry in dialog */
    public static void showEntryDialog(DatabaseManager.JournalEntry entry) {
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
