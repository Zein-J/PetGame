package com.example.controller;

import com.example.model.GameState;
import com.example.model.Pet;
import com.example.util.FileHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ParentMenuController {

    @FXML
    private ToggleButton parentModeToggle;
    @FXML
    private ComboBox<String> selectSaveDropdown;
    @FXML
    private HBox timeLimitSection;
    @FXML
    private TextField timeLimitInput;
    @FXML
    private RadioButton timeLimitToggle;
    @FXML
    private HBox viewStatsSection;
    @FXML
    private Button revivePetButton;
    @FXML
    private HBox inventorySection;
    @FXML
    private Button goBackButton;

    private boolean isParentModeEnabled = false;

    @FXML
    public void initialize() {
        FileHandler fileHandler = new FileHandler();

        // Initialize save dropdown with sample data (replace with actual saves)
        File[] saveFiles = fileHandler.getSaveFiles();
        System.out.println(Arrays.toString(saveFiles));
        if (saveFiles != null) {
            for (File file : saveFiles) {
                String fileName = file.getName();
                if (fileName.matches("slot\\d+\\.json")) {
                    int slotIndex = Integer.parseInt(fileName.replaceAll("[^0-9]", ""));
                    try {
                        GameState state = fileHandler.loadGame("slot" + slotIndex);
                        if (state != null && state.getPet() != null) {
                            Pet pet = state.getPet();
                            // Update slot with both pet name and species
                            selectSaveDropdown.getItems().addAll(pet.getName() + " " + pet.getSpecies());
                        }
                    } catch (IOException ignored) {
                        // Ignore corrupted files and leave default text
                    }
                }
            }
        }
    }

    @FXML
    private void handleToggleParentMode() {
        isParentModeEnabled = !isParentModeEnabled;
        if (isParentModeEnabled) {
            parentModeToggle.setText("ON");
            // Enable blue text sections
            selectSaveDropdown.setVisible(true);
            timeLimitSection.setVisible(true);
            viewStatsSection.setVisible(true);
            revivePetButton.setVisible(true);
            inventorySection.setVisible(true);
        } else {
            parentModeToggle.setText("OFF");
            // Hide all related controls
            selectSaveDropdown.setVisible(false);
            timeLimitSection.setVisible(false);
            timeLimitInput.setVisible(false);
            viewStatsSection.setVisible(false);
            revivePetButton.setVisible(false);
            inventorySection.setVisible(false);
        }
    }

    @FXML
    private void handleTimeLimitToggle() {
        timeLimitInput.setVisible(timeLimitToggle.isSelected());
    }

    @FXML
    private void handleGoBack() {
        if (isParentModeEnabled) {
            // Show a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Parent Mode is still active");
            alert.setContentText("Are you sure you want to leave without disabling Parent Mode?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Go back to the previous scene
                    Stage stage = (Stage) goBackButton.getScene().getWindow();
                    SceneController.getInstance().switchToSettings();
                }
            });
        } else {
            // Go back immediately
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            SceneController.getInstance().switchToSettings();
        }
    }
}
