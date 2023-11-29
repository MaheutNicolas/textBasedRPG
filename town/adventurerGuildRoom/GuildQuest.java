package com.town.adventurerGuildRoom;

import com.controller.MonitorController;
import com.item.ItemTemplate;
import com.rpg.DataBase;
import com.rpg.Player;
import com.rpg.Quest;
import com.town.Place;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GuildQuest extends Place {
    private final Player player;
    private final FlowPane buttonPane;
    private final MonitorController monitor;
    private final Button exitButton;
    private Button[] questButtons;
    private final Random random = new Random();
    private final DataBase dataBase;
    private HashMap<Short, ItemTemplate> itemTemplateMap;
    private final ArrayList<Quest> questList;
    private final int QUANTITYMAX = 40;
    private final int QUESTMAX = 5;

    public GuildQuest(Player player, FlowPane buttonPane, MonitorController monitor, DataBase dataBase, Map<Short, ItemTemplate> itemTemplateMap) {
        this.player = player;
        this.buttonPane = buttonPane;
        this.monitor = monitor;
        this.dataBase = dataBase;
        this.itemTemplateMap = dataBase.getItemTemplateMap();
        this.questList = dataBase.loadQuest();
        if(questList.size() == 0){
            generateQuest();
        }
        this.exitButton = new Button("Sortir");

        this.exitButton.setOnAction(ActionEvent -> onExit.run());
    }

    @Override
    public void enter() {
        if(questList.size() == 0){
            generateQuest();
        }
        setButton();
        this.player.setCurrentPosition(Player.TypePlace.LIBRARY);
        monitor.setText("Vous approchez du Tableau des Quête.");
        monitor.addText("Vous aperçevez un Tableau remplis de Quête");
        monitor.addText("Listes des quêtes :");
        for(Quest quest : questList){
            monitor.addText(quest.getName()+ " : "+quest.getQuantity()+" / Récompense : "+quest.getGold()+ " Pièces d'or");
        }
    }

    @Override
    public void exit() {
        buttonPane.getChildren().clear();
    }
    private void completeQuest(int index){

        if(!player.containItem(questList.get(index).getItemId(), questList.get(index).getQuantity()))return;
        player.setGold(player.getGold()+questList.get(index).getGold());
        player.removeItem(questList.get(index).getItemId(), questList.get(index).getQuantity());
        removeQuest(index);
        setButton();

    }
    private void generateQuest() {
        itemTemplateMap = dataBase.getItemTemplateMap();
        for(short i = 1; i < random.nextInt(QUESTMAX)+1; i++){
            createQuest();
        }
        dataBase.saveQuest(questList);
    }
    private void createQuest(){
        short ref = (short) (random.nextInt(itemTemplateMap.size())+1);
        int quantity = random.nextInt(QUANTITYMAX)+1;
        questList.add( new Quest( questList.size() , itemTemplateMap.get(ref).getName(), ref, quantity, (int)((itemTemplateMap.get(ref).getPrice()*quantity)*1.20)));
    }
    private void removeQuest(int index){
        questList.remove(index);
        updateQuestId();

    }
    void updateQuestId(){
        int i = 0;
        for(Quest quest : questList){
            quest.setId(i);
            i++;
        }
        dataBase.saveQuest(questList);
    }
    void setButton(){
        buttonPane.getChildren().clear();
        questButtons = new Button[questList.size()];
        int i = 0;
        for(Quest quest : questList){
            questButtons[i] = new Button(quest.getName());
            questButtons[i].setOnAction(ActionEvent -> completeQuest(quest.getId()));
            i++;
        }
        buttonPane.getChildren().addAll(exitButton);
        for(Button button : questButtons){
            buttonPane.getChildren().add(button);
        }
    }
}
