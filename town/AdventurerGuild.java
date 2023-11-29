package com.town;

import com.controller.MonitorController;
import com.item.ItemTemplate;
import com.rpg.DataBase;
import com.rpg.Player;
import com.town.adventurerGuildRoom.GuildQuest;
import com.town.adventurerGuildRoom.Library;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.Map;

public class AdventurerGuild extends Place{
    private final Player player;
    private final Library library;
    private final FlowPane buttonPane;
    private final MonitorController monitor;
    private final Button exitButton;
    private final Button libraryButton;
    private final GuildQuest questBoard;
    private final Button questButton;

    public AdventurerGuild(FlowPane buttonPane, MonitorController monitor, Player player, DataBase database, Map<Short, ItemTemplate> itemTemplateMap) {
        this.buttonPane = buttonPane;
        this.monitor = monitor;
        this.player = player;
        this.library = new Library( player, buttonPane, monitor );
        this.questBoard = new GuildQuest( player, buttonPane, monitor, database, itemTemplateMap);


        this.library.setOnExit(() -> {
            library.exit();
            exitRoom();
        });

        this.questBoard.setOnExit(() -> {
            questBoard.exit();
            exitRoom();
        });

        this.exitButton = new Button("Sortir");
        this.libraryButton = new Button("Bibliothèque");
        this.questButton = new Button("Tableau des Quêtes");

        this.exitButton.setOnAction(ActionEvent -> onExit.run());
        this.libraryButton.setOnAction(ActionEvent -> enterLibraryRoom());
        this.questButton.setOnAction(ActionEvent -> enterQuestBoard());
    }

    private void enterQuestBoard() {
        exit();
        questBoard.enter();
    }

    private void enterLibraryRoom(){
        exit();
        library.enter();
    }
    private void exitRoom() {
        this.player.setCurrentPosition(Player.TypePlace.ADVENTURERGUILD);
        monitor.addText("Vous revenez a l'entrée de la guild");
        buttonPane.getChildren().addAll(exitButton, libraryButton, questButton);
    }
    @Override
    public void enter() {
        this.player.setCurrentPosition(Player.TypePlace.ADVENTURERGUILD);
        this.monitor.setMonitorPlace("Guilde des aventuriers");
        this.monitor.setText("Vous rentrez dans la guilde des Aventuriers");
        this.buttonPane.getChildren().addAll(exitButton, libraryButton, questButton);
    }
    @Override
    public void exit() {
        buttonPane.getChildren().clear();
    }
}
