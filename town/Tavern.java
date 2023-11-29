package com.town;

import com.controller.MonitorController;
import com.npc.TavernMaster;
import com.rpg.DataBase;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class Tavern extends Place {
    private final Player player;
    private final FlowPane buttonPane;
    private final Button exitButton;
    private final Button saveButton;
    private final Button restButton;
    private final MonitorController monitor;
    private final DataBase dataBase;
    private TavernMaster tavernMaster;
    private Button tavernMasterButton;
    public Tavern(FlowPane buttonPane, MonitorController monitor, Player player, DataBase dataBase){
        this.buttonPane = buttonPane;
        this.monitor = monitor;
        this.player = player;
        this.dataBase = dataBase;
        this.tavernMaster = new TavernMaster(buttonPane, player, monitor);
        this.tavernMaster.setOnStopSpeacking(() -> {
            tavernMaster.endSpeaking();
            returnToTavern();
        });

        this.exitButton = new Button("Sortir");
        this.saveButton = new Button("Sauvegarder");
        this.restButton = new Button("Se reposer");
        this.tavernMasterButton = new Button("Tavernier");


        this.exitButton.setOnAction(ActionEvent -> onExit.run());
        this.saveButton.setOnAction(ActionEvent -> savePlayerToFile());
        this.restButton.setOnAction(ActionEvent -> {
            monitor.setText("Vous vous êtes reposé confortablement\net regagnez vos force");
            player.healHp();
            player.healMana();
        });
        this.tavernMasterButton.setOnAction(ActionEvent -> speakToTavernMaster());

    }
    public void setFirstLocationLoading(){
        player.setCurrentPosition(Player.TypePlace.TAVERN);
        monitor.setMonitorPlace("Taverne");
        monitor.setText("Vous vous réveillez en pleine Forme dans la Taverne");
        setTavernButton();
    }
    @Override
    public void enter() {
        this.player.setCurrentPosition(Player.TypePlace.TAVERN);
        monitor.setText("Vous rentrez dans la taverne");
        setTavernButton();
    }
    private void speakToTavernMaster() {
        buttonPane.getChildren().clear();
        tavernMaster.startSpeaking();
    }
    private void returnToTavern(){
        setTavernButton();
    }
    @Override
    public void exit() {
        buttonPane.getChildren().clear();
    }
    public void setTavernButton(){
        buttonPane.getChildren().addAll(exitButton, saveButton, restButton, tavernMasterButton);
    }
    private void savePlayerToFile() {
        monitor.addText("Vous vous reposez dans vos appartements");
        if(!dataBase.save(player)) {
            monitor.setText("*ERROR* La sauvegarde n'a pas pu s'éffectuer *ERROR*");
            return;
        }
        monitor.addText("*Vous avez sauvegarder avec succès*");
    }
}

