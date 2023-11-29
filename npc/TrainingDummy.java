package com.npc;

import com.controller.MonitorController;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class TrainingDummy extends NPC{

    public TrainingDummy(FlowPane buttonPane, Player player, MonitorController monitor) {

        super(buttonPane, player, monitor);

        Button quitButton = new Button("Retour");

        quitButton.setOnAction(ActionEvent -> onStopSpeacking.run());

    }
    @Override
    public void startSpeaking() {

    }

    @Override
    public void endSpeaking() {

    }
}
