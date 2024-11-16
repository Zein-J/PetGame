package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaveMenuController {
    @FXML
    private ListView<String> saveSlotList;
    
    @FXML
    public void initialize() {
        ObservableList<String> slots = FXCollections.observableArrayList(
            "CLICK TO CREATE NEW SAVE",
            "CLICK TO CREATE NEW SAVE",
            "CLICK TO CREATE NEW SAVE",
            "CLICK TO CREATE NEW SAVE"
        );
        saveSlotList.setItems(slots);
        
        // selection visual disable
        saveSlotList.setFocusTraversable(false);
        
        // click events
        saveSlotList.setOnMouseClicked(event -> {
            int index = saveSlotList.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                // print to console for debugging; replace with real code later
                System.out.println("Clicked save slot " + (index + 1));
            }
        });
    }
    
    @FXML
    private void goBack() {
        SceneController.getInstance().switchToMainMenu();
    }
}