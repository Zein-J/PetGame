package com.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import java.io.IOException;
import java.io.File;
import com.example.model.GameState;
import com.example.model.Pet;
import com.example.util.FileHandler;

/**
 * Controller for the save game menu interface.
 * Handles save slot management, including creating, editing, and deleting saves.
 */
public class SaveMenuController {
    @FXML
    private ListView<String> saveSlotList;

    @FXML
    private VBox newSaveDialogue;

    @FXML
    private TextField petNameField;

    private int selectedSlotIndex = -1;

    /**
     * Initializes the save menu interface.
     * - Creates default save slots
     * - Loads existing saves from files
     * - Sets up event handlers
     */
    @FXML
    public void initialize() {
        // Create file handler for save operations
        FileHandler fileHandler = new FileHandler();
        
        // Initialize list with default "empty" save slots
        ObservableList<String> slots = FXCollections.observableArrayList(
                "CLICK TO CREATE NEW SAVE",
                "CLICK TO CREATE NEW SAVE",
                "CLICK TO CREATE NEW SAVE",
                "CLICK TO CREATE NEW SAVE"
        );

        // Load existing save files and update slots with pet names
        File[] saveFiles = fileHandler.getSaveFiles();
        if (saveFiles != null) {
            for (File file : saveFiles) {
                // Extract slot number from filename (e.g., "slot0.json" → 0)
                String fileName = file.getName();
                if (fileName.matches("slot\\d+\\.json")) {
                    int slotIndex = Integer.parseInt(fileName.replaceAll("[^0-9]", ""));
                    try {
                        GameState state = fileHandler.loadGame("slot" + slotIndex);
                        if (state != null && state.getPet() != null) {
                            slots.set(slotIndex, state.getPet().getName());
                        }
                    } catch (IOException ignored) {
                        // If file is corrupted, keep default text
                    }
                }
            }
        }

        saveSlotList.setItems(slots);
        saveSlotList.setFocusTraversable(false);

        saveSlotList.setOnMouseClicked(event -> {
            int index = saveSlotList.getSelectionModel().getSelectedIndex();
            if (index >= 0 && "CLICK TO CREATE NEW SAVE".equals(saveSlotList.getItems().get(index))) {
                selectedSlotIndex = index;
                showNewSaveDialogue();
            }
        });

        setupCustomListCells();
    }

    /**
     * Sets up custom list cell rendering with interactive buttons.
     * Each save slot displays:
     * - Pet name or "CLICK TO CREATE NEW SAVE"
     * - PLAY button - loads the save
     * - EDIT button - allows renaming the pet
     * - DELETE button - removes the save
     */
    private void setupCustomListCells() {
        saveSlotList.setCellFactory(lv -> new ListCell<String>() {
            // Create buttons and containers for the custom cell layout
            private final Button playButton = new Button("PLAY");
            private final Button editButton = new Button("EDIT");
            private final Button deleteButton = new Button("DELETE");
            private final HBox buttons = new HBox(10, playButton, editButton, deleteButton);
            private final HBox content = new HBox(20);

            {
                // Initialize cell styling and behavior
                buttons.setVisible(false);  // Hide buttons by default
                buttons.getStyleClass().add("save-slot-buttons");
                playButton.getStyleClass().add("save-slot-button");
                editButton.getStyleClass().add("save-slot-button");
                deleteButton.getStyleClass().add("save-slot-button");

                content.setAlignment(javafx.geometry.Pos.CENTER);
                buttons.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

                // Set up button click handlers
                playButton.setOnAction(e -> {
                    e.consume();  // Prevent event bubbling
                    handlePlay(getItem());
                });
                editButton.setOnAction(e -> {
                    e.consume();
                    handleEdit(getItem());
                });
                deleteButton.setOnAction(e -> {
                    e.consume();
                    handleDelete(getItem());
                });

                // Show/hide buttons on mouse hover
                setOnMouseEntered(e -> {
                    if (getItem() != null && !"CLICK TO CREATE NEW SAVE".equals(getItem())) {
                        buttons.setVisible(true);
                    }
                });

                setOnMouseExited(e -> {
                    buttons.setVisible(false);
                });

                buttons.setOnMouseClicked(event -> event.consume());
                content.setOnMouseClicked(event -> {
                    if (getItem() != null && !"CLICK TO CREATE NEW SAVE".equals(getItem())) {
                        event.consume();
                    }
                });
            }

            /**
             * Updates the cell content when data changes
             */
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label text = new Label(item);
                    text.setMaxWidth(Double.MAX_VALUE);
                    HBox.setHgrow(text, Priority.ALWAYS);
                    content.getChildren().setAll(text, buttons);
                    setGraphic(content);
                }
            }
        });

        saveSlotList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            saveSlotList.refresh();
        });
    }

    /**
     * Displays the new save dialogue for creating/editing a save
     * - Disables the save slot list
     * - Shows the input field with default pet name
     * - Focuses the input field
     */
    private void showNewSaveDialogue() {
        saveSlotList.setDisable(true);
        newSaveDialogue.setVisible(true);
        petNameField.setText("PET " + (selectedSlotIndex + 1));
        petNameField.selectAll();
        petNameField.requestFocus();
    }

    /**
     * Creates a new save with the entered pet name
     * - Creates new GameState and Pet objects
     * - Saves to file using FileHandler
     * - Updates the UI with the new pet name
     */
    @FXML
    private void confirmNewSave() {
        String petName = petNameField.getText().trim();
        if (!petName.isEmpty()) {
            try {
                // Create new GameState with pet
                GameState gameState = new GameState();
                Pet pet = new Pet(petName);
                gameState.setPet(pet);
                
                // Save to file
                FileHandler fileHandler = new FileHandler();
                fileHandler.saveGame("slot" + selectedSlotIndex, gameState);
                
                // Update UI
                ObservableList<String> slots = saveSlotList.getItems();
                slots.set(selectedSlotIndex, petName);
                hideNewSaveDialogue();
            } catch (IOException e) {
                // TODO: Show error dialog to user
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void cancelNewSave() {
        hideNewSaveDialogue();
    }

    private void hideNewSaveDialogue() {
        newSaveDialogue.setVisible(false);
        saveSlotList.setDisable(false);
        selectedSlotIndex = -1;
    }

    @FXML
    private void goBack() {
        SceneController.getInstance().switchToMainMenu();
    }

    private void handlePlay(String saveName) {
        // TODO: Implement play functionality
        System.out.println("Playing: " + saveName);
    }

    private void handleEdit(String saveName) {
        selectedSlotIndex = saveSlotList.getItems().indexOf(saveName);
        showNewSaveDialogue();
    }

    /**
     * Handles the deletion of a save file
     * - Removes the save file
     * - Updates the UI to show empty slot
     * @param saveName The name of the save to delete
     */
    private void handleDelete(String saveName) {
        int index = saveSlotList.getItems().indexOf(saveName);
        try {
            FileHandler fileHandler = new FileHandler();
            fileHandler.deleteSave("slot" + index);
            Platform.runLater(() -> {
                saveSlotList.getItems().set(index, "CLICK TO CREATE NEW SAVE");
                saveSlotList.getSelectionModel().clearSelection();
            });
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show error dialog to user
        }
    }
}