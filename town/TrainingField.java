package com.town;

import com.controller.MonitorController;
import com.npc.TrainingDummy;
import com.rpg.Player;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class TrainingField extends Place{
    private final Player player;
    private final MonitorController monitor;
    private final FlowPane buttonPane;
    private final Button exitButton;
    private final TrainingDummy trainingDummy;
    private final Button trainingButton;

    public TrainingField(FlowPane buttonPane, MonitorController monitor, Player player){
        this.buttonPane = buttonPane;
        this.monitor = monitor;
        this.player = player;
        this.trainingDummy = new TrainingDummy(buttonPane, player, monitor);
        this.trainingDummy.setOnStopSpeacking(() -> {
            trainingDummy.endSpeaking();
            returnToTrainingField();
        });
        this.trainingButton = new Button("Mannequin");
        this.exitButton = new Button("Sortir");

        this.trainingButton.setOnAction(ActionEvent -> startTraining());
        this.exitButton.setOnAction(ActionEvent -> onExit.run());
    }

    private void returnToTrainingField() {
        monitor.setText("Vous retourner à l'entrée du terrain d'entrainement");
        addFieldButtons();
    }

    private void addFieldButtons() {
        buttonPane.getChildren().addAll(exitButton, trainingButton);
    }

    private void startTraining() {
        trainingDummy.startSpeaking();
    }

    @Override
    public void enter() {
        monitor.setMonitorPlace("Terrain d'entrainement");
        this.player.setCurrentPosition(Player.TypePlace.TRAININGFIELD);
        monitor.setText("Vous avancez sur le terrain d'entrainement");
        addFieldButtons();
    }

    @Override
    public void exit() {
        buttonPane.getChildren().clear();
    }
}
