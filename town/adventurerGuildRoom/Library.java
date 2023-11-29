package com.town.adventurerGuildRoom;

import com.controller.MonitorController;
import com.rpg.Player;
import com.town.Place;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class Library extends Place {
    private final Player player;
    private final FlowPane buttonPane;
    private final MonitorController monitor;
    private Button exitButton;

    public Library(Player player, FlowPane buttonPane, MonitorController monitor) {
        this.player = player;
        this.buttonPane = buttonPane;
        this.monitor = monitor;

        this.exitButton = new Button("Sortir");

        this.exitButton.setOnAction(ActionEvent -> onExit.run());

    }

    @Override
    public void enter() {
        this.player.setCurrentPosition(Player.TypePlace.LIBRARY);
        monitor.setText("Vous approchez de la bibliothèque.");
        monitor.addText("Vous aperçevez de grandes étagère remplis de livres");
        buttonPane.getChildren().addAll(exitButton);
    }

    @Override
    public void exit() {
        monitor.setText("Vous vous éloignez de la bibliothèque");
        buttonPane.getChildren().clear();
    }
}
