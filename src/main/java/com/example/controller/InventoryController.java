package com.example.controller;

import com.example.model.GameState;
import com.example.model.Pet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InventoryController {

    @FXML
    private ImageView item1Image;
    @FXML
    private ImageView item2Image;
    @FXML
    private ImageView item3Image;
    @FXML
    private ImageView item4Image;

    @FXML
    private Label item1Quantity;
    @FXML
    private Label item2Quantity;
    @FXML
    private Label item3Quantity;
    @FXML
    private Label item4Quantity;

    @FXML
    public void initialize() {
        // Set default images and quantities
        item1Image.setImage(new Image("/images/item1.png"));
        item2Image.setImage(new Image("/images/item2.png"));
        item3Image.setImage(new Image("/images/item3.png"));
        item4Image.setImage(new Image("/images/item4.png"));
        GameState gameState = GameState.getCurrentState();
        Pet pet = gameState.getPet(); // Retrieve the Pet object

        item1Quantity.setText(Integer.toString(pet.getInventory().getItem1())); // Example: 5 of item1
        item2Quantity.setText(Integer.toString(pet.getInventory().getItem2())); // Example: 3 of item2
        item3Quantity.setText(Integer.toString(pet.getInventory().getItem3())); // Example: 2 of item3
        item4Quantity.setText(Integer.toString(pet.getInventory().getItem4())); // Example: 7 of item4
    }

    @FXML
    private void goBack() {
        // Handle navigation back to the previous screen
        SceneController.getInstance().switchToMainMenu();
    }
}
