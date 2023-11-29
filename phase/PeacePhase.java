package com.phase;

import com.controller.MonitorController;
import com.item.ItemTemplate;
import com.rpg.DataBase;
import com.rpg.Player;
import com.town.*;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.Map;

public class PeacePhase extends Phase{
    private final Player player;
    private final FlowPane buttonPane;
    private final MonitorController monitor;
    private final Button adventureButton;
    private final Button churchButton;
    private final Button dungeonButton;
    private final Button tavernButton;
    private final Button trainingButton;
    private final AdventurerGuild adventurerGuild;
    private final Church church;
    private final DungeonEntrance dungeonEntrance;
    private final Tavern tavern;
    private final TrainingField trainingField;

    Place currentPlace;

    public PeacePhase(Player player, FlowPane buttonPane, MonitorController monitor, DataBase dataBase, Map<Short, ItemTemplate> itemTemplateMap){

        super(buttonPane);
        this.buttonPane = buttonPane;
        this.player = player;
        this.monitor = monitor;

        this.adventurerGuild = new AdventurerGuild(buttonPane, monitor, player, dataBase, itemTemplateMap);
        this.church = new Church(buttonPane, monitor, player);
        this.dungeonEntrance = new DungeonEntrance(buttonPane, monitor, player);
        this.tavern = new Tavern(buttonPane, monitor, player, dataBase);
        this.trainingField = new TrainingField(buttonPane, monitor, player);

        this.adventurerGuild.setOnExit(() -> exitCurrentPlace());
        this.church.setOnExit(() -> exitCurrentPlace());
        this.dungeonEntrance.setOnExit(() -> exitCurrentPlace());
        this.dungeonEntrance.setOnEnterDungeon(()-> onExit.accept(PhaseManager.TypePhase.EXPLORATION));
        this.tavern.setOnExit(() -> exitCurrentPlace());
        this.trainingField.setOnExit(() -> exitCurrentPlace());

        this.adventureButton = new Button("Guilde des Aventuriers");
        this.churchButton = new Button("Eglise");
        this.dungeonButton = new Button("Entrée du Donjon");
        this.tavernButton = new Button("Taverne");
        this.trainingButton = new Button("Terrain d'entrainement");

        Button additemS = new Button("ajouter slime");
        Button additemW = new Button("ajouter loups");
        Button additemB= new Button("ajouter Os");
        Button delitemS = new Button("delete slime");
        Button delitemW = new Button("delete loups");
        Button delitemB = new Button("delete Os");


        this.adventureButton.setOnAction(ActionEvent -> enterAdventurer());
        this.churchButton.setOnAction(ActionEvent -> enterChurch());
        this.dungeonButton.setOnAction(ActionEvent -> enterDungeon());
        this.tavernButton.setOnAction(ActionEvent -> enterTavern());
        this.trainingButton.setOnAction(ActionEvent -> enterTraining());

        additemS.setOnAction(E -> player.addItem((short) 1, 1));
        additemW.setOnAction(E -> player.addItem((short) 2, 1));
        additemB.setOnAction(E -> player.addItem((short) 4, 1));
        delitemS.setOnAction(E -> player.removeItem((short) 1, 1));
        delitemW.setOnAction(E -> player.removeItem((short) 2, 1));
        delitemB.setOnAction(E -> player.removeItem((short) 4, 1));
        buttonPane.getChildren().addAll(additemS,additemW, additemB,delitemS,delitemW, delitemB);
    }
    protected void setFirstLocationLoading(){
        this.currentPlace = this.tavern;
        this.tavern.setFirstLocationLoading();

    }
    @Override
    void onStart() {
        this.player.setCurrentPosition(Player.TypePlace.TOWN);
        this.monitor.setMonitorPlace("Ville");
        this.monitor.setText("Vous rentrez dans la ville");
        this.monitor.addText("Où souhaitez vous aller ?");
        this.buttonPane.getChildren().addAll(adventureButton, churchButton, dungeonButton, tavernButton, trainingButton);
    }
    @Override
    void onEnd() {
        this.buttonPane.getChildren().clear();
        this.monitor.setMonitorPlace("étage "+ player.getDungeonStage());
        this.monitor.addText("Vous rentrez dans le donjon");
    }
    private void exitCurrentPlace() {
        this.currentPlace.exit();
        onStart();
    }
    private void enterAdventurer(){
        this.currentPlace = this.adventurerGuild;
        enterAction();
    }
    private void enterChurch(){
        this.currentPlace = this.church;
        enterAction();
    }
    private void enterDungeon(){
        this.currentPlace = this.dungeonEntrance;
        enterAction();
    }
    private void enterTavern(){
        this.currentPlace = this.tavern;
        enterAction();
    }
    private void enterTraining() {
        this.currentPlace = this.trainingField;
        enterAction();
    }

    private  void enterAction(){
        this.buttonPane.getChildren().clear();
        this.currentPlace.enter();
    }

}
