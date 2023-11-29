package com.town;

import com.controller.MonitorController;
import com.rpg.Player;
import com.town.churchRoom.Altar;
import com.town.churchRoom.Fountain;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class Church extends Place{
    private final Player player;
    private final Fountain fountain;
    private final Altar altar;
    private FlowPane buttonPane;
    private MonitorController monitor;
    private Button exitButton;
    private Button fontainButton;
    private Button altarButton;
    public Church(FlowPane buttonPane, MonitorController monitor, Player player) {
        this.buttonPane = buttonPane;
        this.monitor = monitor;
        this.player = player;
        this.fountain = new Fountain(player, buttonPane, monitor);
        this.altar = new Altar(player, buttonPane, monitor);

        this.altar.setOnExit(() -> {
            altar.exit();
            exitRoom();
        });

        this.fountain.setOnExit(() -> {
            fountain.exit();
            exitRoom();
        });

        this.exitButton = new Button("Sortir");
        this.fontainButton = new Button("Fontaine");
        this.altarButton = new Button("Autel");

        this.exitButton.setOnAction(ActionEvent -> onExit.run());
        this.fontainButton.setOnAction(ActionEvent -> enterFountainRoom());
        this.altarButton.setOnAction(ActionEvent -> enterAltarRoom());
    }
    @Override
    public void enter() {
        this.player.setCurrentPosition(Player.TypePlace.CHURCH);
        monitor.setMonitorPlace("Eglise");
        monitor.setText("Vous rentrez dans l'église");
        buttonPane.getChildren().addAll(exitButton, fontainButton, altarButton);
    }
    private void enterFountainRoom(){
        exit();
        fountain.enter();

    }
    private void enterAltarRoom(){
        exit();
        altar.enter();

    }
    private void exitRoom() {
        this.player.setCurrentPosition(Player.TypePlace.CHURCH);
        monitor.addText("Vous revenez a l'entrée de l'église");
        buttonPane.getChildren().addAll(exitButton, fontainButton, altarButton);
    }
    @Override
    public void exit() {
        buttonPane.getChildren().clear();
    }
}
