package com.controller;

import com.item.*;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import java.util.HashMap;
import java.util.Map;

public class InventoryController {
    @FXML
    private FlowPane inventoryPane;
    @FXML
    private ScrollPane scrollPane;
    private ScrollController scrollController;

    public void injectScrollController(ScrollController scrollController) {
        this.scrollController = scrollController;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public FlowPane getInventoryPane() {
        return inventoryPane;
    }
}
