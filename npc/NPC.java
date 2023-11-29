package com.npc;

import com.controller.MonitorController;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.Random;

abstract public class NPC {
    private final FlowPane buttonPane;
    private final Player player;
    private final MonitorController monitor;
    private final Button quitButton;

    NPC(FlowPane buttonPane, Player player, MonitorController monitor) {
            this.buttonPane = buttonPane;
            this.player = player;
            this.monitor = monitor;
            this.quitButton = new Button("Retour");
            this.quitButton.setOnAction(ActionEvent -> onStopSpeacking.run());
    }
    protected Runnable onStopSpeacking;
    abstract public void startSpeaking();
    abstract public void endSpeaking();

    public void setOnStopSpeacking(Runnable onStopSpeacking) {
        this.onStopSpeacking = onStopSpeacking;
    }

    public Player getPlayer() {
        return player;
    }

    public MonitorController getMonitor() {
        return monitor;
    }

    public FlowPane getButtonPane() {
        return buttonPane;
    }

    public Button getQuitButton() {
        return quitButton;
    }
}
