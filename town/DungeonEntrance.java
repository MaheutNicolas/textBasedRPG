package com.town;

import com.controller.MonitorController;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class DungeonEntrance extends Place{

    private final FlowPane buttonPane;
    private final MonitorController monitor;
    private final Player player;
    private Button exitButton;
    private Button djEnterButton;

    private Runnable onEnterDungeon;

    public DungeonEntrance(FlowPane buttonPane, MonitorController monitor, Player player) {
        this.buttonPane = buttonPane;
        this.monitor = monitor;
        this.player = player;

        this.exitButton = new Button("Sortir");
        this.djEnterButton = new Button("Entrée dans le donjon");

        this.exitButton.setOnAction(ActionEvent -> onExit.run());
        this.djEnterButton.setOnAction(ActionEvent -> onEnterDungeon.run());
    }


    @Override
    public void enter() {
        this.player.setCurrentPosition(Player.TypePlace.DUNGEONENTRANCE);
        this.monitor.setMonitorPlace("Entrée du Donjons");
        this.monitor.setText("Vous approchez l'entrée du donjons");
        this.buttonPane.getChildren().addAll(exitButton, djEnterButton);
    }

    @Override
    public void exit() {
        buttonPane.getChildren().clear();
    }

    public void setOnEnterDungeon(Runnable onEnterDungeon) {
        this.onEnterDungeon = onEnterDungeon;
    }
}
