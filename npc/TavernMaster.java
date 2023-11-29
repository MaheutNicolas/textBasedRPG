package com.npc;

import com.controller.MonitorController;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.nio.file.Path;
import java.util.Random;

public class TavernMaster extends NPC {
    private final Random random;
    private final Button rumorButton;

    public TavernMaster(FlowPane buttonPane, Player player, MonitorController monitor) {
        super(buttonPane, player, monitor);

        this.random = new Random();

        rumorButton = new Button("Rumeurs");

        rumorButton.setOnAction(ActionEvent -> writeRandomRumor(random.nextInt(2)));
    }
    @Override
    public void startSpeaking() {
        getButtonPane().getChildren().addAll(getQuitButton(), rumorButton);
    }
    private void writeRandomRumor(int i) {
        switch (i){
            case 0:
                getMonitor().setText("Les aventuriers se font rare ces temps ci.  ");
                break;
            case 1:
                break;
        }
        getMonitor().setText("");
    }
    @Override
    public void endSpeaking() {
        getButtonPane().getChildren().clear();
    }
}
