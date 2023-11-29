package com.town.churchRoom;

import com.controller.MonitorController;
import com.rpg.Player;
import com.town.Place;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class Fountain extends Place {
    private final Player player;
    private final FlowPane buttonPane;
    private final MonitorController monitor;
    private Button exitButton;
    public Fountain(Player player, FlowPane buttonPane, MonitorController monitor) {
        this.player = player;
        this.buttonPane = buttonPane;
        this.monitor = monitor;

        this.exitButton = new Button("Sortir");

        this.exitButton.setOnAction(ActionEvent -> onExit.run());

    }

    @Override
    public void enter() {
        this.player.setCurrentPosition(Player.TypePlace.FOUTAIN);
        monitor.setText("Vous approchez de la fontaine.");
        monitor.addText("Elle se situe dans une petit pièce a droite de l'entrée");
        buttonPane.getChildren().addAll(exitButton);
    }

    @Override
    public void exit() {
        monitor.setText("Vous vous éloignez de la Fontaine");
        buttonPane.getChildren().clear();
    }
}
