package com.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ActionController implements Initializable {
    @FXML
    AnchorPane action;
    @FXML
    FlowPane buttonPane;
    @FXML
    Button switchButton;
    private boolean inventoryOpen = true;
    private MainController mainController;
    private Consumer<Boolean> listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchButton.setOnAction(actionEvent -> this.setInventoryOpen(!this.isInventoryOpen()));

    }
    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public FlowPane getButtonPane() {
        return buttonPane;
    }

    /**
     * Set the panel inventory
     * @param inventoryOpen true = the inventory is open / false = the enemy scroll is open
     */
    public void setInventoryOpen(boolean inventoryOpen) {
        this.inventoryOpen = inventoryOpen;
        listener.accept(inventoryOpen);
    }

    public boolean isInventoryOpen() {
        return inventoryOpen;
    }

    public void addListener(Consumer<Boolean> listener) {
        this.listener = listener;
    }

}
