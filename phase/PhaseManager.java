package com.phase;

import com.controller.MainController;
import com.fight.SkillManager;
import com.item.ItemTemplate;
import com.rpg.Enemy;
import com.rpg.DataBase;
import com.rpg.Player;

import java.util.Map;

public class PhaseManager {

    Player player;
    Phase currentPhase;
    Phase newPhase;
    Enemy enemy;
    final PeacePhase peacePhase;
    final ExplorationPhase explorationPhase;
    final FightPhase fightPhase;
    final MainController mainController;
    final EventPhase eventPhase;


    public PhaseManager(MainController mainController, Player player, Enemy enemy, DataBase dataBase, SkillManager attack, Map<Short, ItemTemplate> itemTemplateMap) {
        this.player = player;
        this.enemy = enemy;

        this.mainController = mainController;
        this.peacePhase = new PeacePhase( player, mainController.getActionController().getButtonPane(), mainController.getMonitorController(), dataBase, itemTemplateMap);
        this.explorationPhase = new ExplorationPhase( player, mainController.getActionController().getButtonPane(), mainController.getMonitorController());
        this.fightPhase = new FightPhase( player, mainController.getMonitorController(), mainController.getActionController(), enemy, attack);
        this.eventPhase = new EventPhase( player, mainController.getActionController().getButtonPane(), mainController.getMonitorController());
        this.currentPhase = this.peacePhase;

        this.peacePhase.setOnExit(phase -> switchPhase(phase));
        this.fightPhase.setOnExit(phase -> switchPhase(phase));
        this.eventPhase.setOnExit(phase -> switchPhase(phase));
        this.explorationPhase.setOnExit(phase -> switchPhase(phase));

        start();
    }
    public void changePhase() {
        this.currentPhase.onEnd();
        this.currentPhase = newPhase;
        this.currentPhase.onStart();
    }
    public void start() {
        peacePhase.setFirstLocationLoading();
    }
    public void switchPhase(TypePhase typePhase) {
        switch(typePhase) {
            case FIGHT:
                this.newPhase = this.fightPhase;
                break;
            case PASSIVE:
                this.newPhase = this.peacePhase;
                break;
            case EXPLORATION:
                this.newPhase = this.explorationPhase;
                break;
            case EVENT:
                this.newPhase = this.eventPhase;
                break;
            case NEXTROOM:
                this.currentPhase = this.explorationPhase;
                explorationPhase.setNextDungeonRoom();
                return;
        }
        changePhase();
    }
    //TODO mettre en place peacePhase
    //TODO creer des etage
    //TODO mettre en place un effet machine a ecrire
    //TODO mettre en place un inventaire
    //TODO mettre en place un systeme de craft et utilisation d'objet
    //TODO mettre un systeme de couleur
    public enum TypePhase {
        FIGHT,
        PASSIVE,
        EXPLORATION,
        EVENT,
        NEXTROOM
    }
    public FightPhase getFightPhase(){
        return fightPhase;
    }

}

