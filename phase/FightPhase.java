package com.phase;

import com.controller.ActionController;
import com.controller.MonitorController;
import com.fight.*;
import com.rpg.Enemy;
import com.rpg.Player;
import javafx.scene.control.Button;
import java.util.Random;

public class FightPhase extends Phase {
    private final Random random = new Random();
    private final Player player;
    private final Enemy enemy;
    private final Button fightButton;
    private final MonitorController monitor;
    private final ActionController actionController;
    private final SkillManager attack;
    private final Button escapeButton;
    private final FightEvent fightEvent;
    private final int eventPercentage = 33;

    public FightPhase(Player player, MonitorController monitor, ActionController actionController, Enemy enemy, SkillManager attack) {
        super(actionController.getButtonPane());
        this.player = player;
        this.enemy = enemy;
        this.monitor = monitor;
        this.actionController = actionController;
        this.attack = attack;
        this.fightEvent = new FightEvent(monitor, player, enemy, attack, this, actionController.getButtonPane());

        this.fightButton = new Button("Combat");
        this.escapeButton = new Button("Fuire");

        this.fightButton.setOnAction(actionEvent -> startFight());
        this.escapeButton.setOnAction(actionEvent -> escapeFight());

        fightEvent.addCallBack(() -> endEventPhase());
    }

    @Override
    public void onStart() {
        this.player.setCurrentPosition(Player.TypePlace.FIGHTSTART);
        this.setRandomEnemy();
        this.attack.setEnemy(enemy);
        this.buttonPane.getChildren().addAll(fightButton, escapeButton);
        this.monitor.addText(enemy.getEnemyText()[0]);
        this.actionController.setInventoryOpen(true);
    }
    private void startFight(){

        this.player.setCurrentPosition(Player.TypePlace.FIGHT);
        this.monitor.addText("Début du combat");

        if (random.nextInt(100) < eventPercentage){
            this.fightEvent.getSituation();
            return;
        }

        addFightButtons();
        if (enemy.getSpeed() > player.getSpeed()) {
            attack.executeEnemyAttack(enemy.getAttackArray()[random.nextInt(enemy.getAttackArray().length)]);
        }
    }
    @Override
    public void onEnd () {
        clearFightButtons();
        actionController.setInventoryOpen(false);
    }
    private void setRandomEnemy () {
        final int stage = player.getDungeonStage();
        switch (stage) {
            case 0:
                int enemyRandom = random.nextInt(2);
                if (enemyRandom == 0) {
                    enemy.setEnemy(0);
                } else {
                    enemy.setEnemy(1);
                }
                break;
            case 1:
                break;
        }
    }
    public void switchTurn(boolean enemyTurn) {
        boolean isAlive = checkEnemyState();
        enemyTurn = enemyTurn && isAlive;
        if(!enemyTurn) return;
        if (random.nextInt(100) < eventPercentage){
            this.fightEvent.getSituation();
            return;
        }
        attack.executeEnemyAttack(enemy.getAttackArray()[random.nextInt(enemy.getAttackArray().length)]);
    }
    public boolean checkEnemyState(){
        if(enemy.getHp() <= 0){
            monitor.addText("VICTOIRE :\n"+
                    enemy.getEnemyText()[1]);
            gainExperience();
            gainItem();
            onExit.accept(PhaseManager.TypePhase.EXPLORATION);
            return false;
        }
        return true;
    }
    public void checkPlayerState(){
        if(player.getHp() <= 0){
            monitor.addText("DEFAITE : \n"+
                    "Vous avez été vaincu, et retourner à la salle des téléporteurs");
            player.setDungeonEventNumber(0);
            onExit.accept(PhaseManager.TypePhase.EXPLORATION);
        }
    }
    private void gainExperience(){
        int expGain = random.nextInt(enemy.getEnemyExperience()) + enemy.getEnemyExperience();
        player.setExperience(player.getExperience()+ expGain);
        monitor.addText("Vous avez gagné " + expGain + " expérience");
        player.expMaxForLevelUp();
    }
    private void gainItem() {
        if(random.nextInt(10) > 3){
            player.addItem(enemy.getItemDrop(), 1);
            monitor.addText(enemy.getName()+ " vous a drop son item");
            return;
        }
        monitor.addText("Vous n'avez rien drop");
    }
    public void addFightButtons(){
        this.attack.getButtons();
    }
    public void clearFightButtons(){
        this.buttonPane.getChildren().clear();
    }
    private void escapeFight() {
        if(player.getSpeed() < enemy.getSpeed()){
            monitor.addText("Vous essayer de fuire mais "+enemy.getName()+" a été plus rapide.");
        }
        monitor.addText("Vous fuyez le combat et passez la porte suivante");
        onExit.accept(PhaseManager.TypePhase.EXPLORATION);
    }

    private void endEventPhase() {
        clearFightButtons();
        addFightButtons();
        player.setCurrentPosition(Player.TypePlace.FIGHT);
    }

}

