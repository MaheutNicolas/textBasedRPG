package com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MonitorController {
    @FXML
    private Label monitorLabel;
    @FXML
    private AnchorPane monitor;
    @FXML
    private TextField monitorPlace;
    private MainController mainController;
    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setMonitorPlace(String text){
        monitorPlace.setText(text + " :");
    }

    /**
     * Pass Line and add text;
     */
    public void addText(String text) {
        passLine();
        monitorLabel.setText(getText() + text);
    }

    /**
     * Delete all current Text and replace it
     */
    public void setText(String text) {
        monitorLabel.setText(text);
    }

    /**
     * Delete all Text
     */
    public void clear(){
        setText("");
    }

    /**
     * pass a Line with a "\n"
     */
    public void passLine(){
        setText(getText() + "\n");
    }

    /**
     * @return the current text
     */
    public String getText(){
        return monitorLabel.getText();
    }
}
