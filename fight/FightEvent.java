package com.fight;

import com.controller.MonitorController;
import com.phase.FightPhase;
import com.rpg.Enemy;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.Random;

public class FightEvent {
    private final FightPhase fightPhase;
    private final Random random = new Random();
    private final MonitorController monitor;
    private final Player player;
    private final Enemy enemy;
    private final SkillManager attack;
    private final Button rushButton;
    private final Button blockButton;
    private final Button dodgeButton;
    private final FlowPane buttonPane;
    private int enemyChoice;
    private Runnable endEvent;

    public FightEvent(MonitorController monitor, Player player, Enemy enemy, SkillManager attack, FightPhase fightPhase, FlowPane buttonPane) {
        this.monitor = monitor;
        this.enemy = enemy;
        this.player = player;
        this.attack = attack;
        this.fightPhase = fightPhase;
        this.buttonPane = buttonPane;

        this.rushButton = new Button("Charger");
        this.dodgeButton = new Button("Esquiver");
        this.blockButton = new Button("Parer");

        rushButton.setOnAction(ActionEvent -> rushAction());
        blockButton.setOnAction(ActionEvent -> blockAction());
        dodgeButton.setOnAction(ActionEvent -> dodgeAction());

    }
    public void getSituation() {
        player.setCurrentPosition(Player.TypePlace.FIGHTEVENT);
        fightPhase.clearFightButtons();
        this.enemyChoice = random.nextInt(3);
        monitor.addText(enemy.getName() + " concentre le mana\n"+
                enemy.getEnemyText()[enemyChoice+2]);
        getButton();
    }
    private void getButton() {
        this.buttonPane.getChildren().addAll(rushButton,  dodgeButton, blockButton);
    }
    // 0
    private void rushAction()  {
        monitor.setText("Vous foncez sur "+ enemy.getName());
        switch (enemyChoice){
            case 0:
                monitor.addText(enemy.getName()+ " a été plus rapide que vous mais l'attaque échoue\n" +
                        "Match null");
                endEvent.run();
                break;
            case 1:
                monitor.addText(enemy.getName()+ " met trop de temps a charger son attaque\n" +
                        "Victoire");
                eventVictory();
                break;

            case 2:
                monitor.addText(enemy.getName()+ " tire immédiatement et vous blesse\n" +
                    "Défaite");
                eventDefeate();
                break;
        }

    }
    // 1
    private void dodgeAction() {
        monitor.setText("Vous esquivez l'attaque qui approche");
        switch (enemyChoice){
            case 0:
                monitor.addText(enemy.getName()+ " tire une attaque qui vous blesse\n" +
                        "Défaite");
                eventDefeate();
                break;
            case 1:
                monitor.addText("L'impact vous repousse simplement\n" +
                        "Match null");
                endEvent.run();
                break;

            case 2:
                monitor.addText(enemy.getName()+ " vous rate et vous en profitez pour charger\n" +
                        "Victoire");
                eventVictory();
                break;
        }
    }
    // 2
    private void blockAction() {
        monitor.setText("Vous essayer de parez");
        switch (enemyChoice){
            case 0:
                monitor.addText("Vous parez l'attaque avec succès et contre-attaquez\n" +
                        "Victoire");
                eventVictory();
                break;
            case 1:
                monitor.addText("Vous n'arrivez pas à bloquez et vous êtes blessez\n" +
                        "Défaite");
                eventDefeate();
                break;
            case 2:
                monitor.addText(enemy.getName()+ " vous destabilise sans vous blessez\n" +
                        "Match Null");
                endEvent.run();
                break;
        }
    }

    public void eventVictory(){
        int stat = getBestPlayerStat();
        int enemyStat = getWorstEnemyStat();
        int damage = 0;
        if(stat <= enemyStat){
            damage = (int) Math.round(stat*0.2);
        }
        else {

            damage = Math.round(stat - (enemyStat * 80/100));
        }
        int newHP = enemy.getHp() - damage;
        enemy.setHp(newHP);
        monitor.addText( damage +" point de dégat ont été infligez");
        endEvent.run();
    }
    public void eventDefeate(){
        int stat = getWorstPlayerStat();
        int enemyStat = getBestEnemyStat();
        int damage = 0;
        if(enemyStat <= stat){
            damage = (int) Math.round(enemyStat*0.2);
        }
        else {
            damage = (int) Math.round(enemyStat - (stat * 80/100));
        }
        int newHP = player.getHp() - damage;
        player.setHp(newHP);
        monitor.addText( damage +" point de dégat ont été infligez");
        endEvent.run();
    }
    public void addCallBack(Runnable cb) {
        this.endEvent = cb;
    }
    private int getBestPlayerStat(){
        int stat = player.getSpeed();
        if(stat < player.getStrength()){
            stat = player.getStrength();
        }
        if(stat < player.getAccuracy()){
            stat = player.getAccuracy();
        }
        if(stat < player.getIntelligence()){
            stat = player.getStrength();
        }
        return stat;
    }
    private int getWorstPlayerStat(){
        int stat = player.getSpeed();
        if(stat > player.getStrength()){
            stat = player.getStrength();
        }
        if(stat > player.getAccuracy()){
            stat = player.getAccuracy();
        }
        if(stat > player.getIntelligence()){
            stat = player.getIntelligence();
        }
        return stat;
    }
    private int getBestEnemyStat(){
        int stat = enemy.getSpeed();
        if(stat < enemy.getStrength()){
            stat = enemy.getStrength();
        }
        if(stat < enemy.getAccuracy()){
            stat = enemy.getAccuracy();
        }
        if(stat < enemy.getIntelligence()){
            stat = enemy.getIntelligence();
        }
        return stat;
    }
    private int getWorstEnemyStat(){
        int stat = enemy.getSpeed();
        if(stat > enemy.getStrength()){
            stat = enemy.getStrength();
        }
        if(stat > enemy.getAccuracy()){
            stat = enemy.getAccuracy();
        }
        if(stat > enemy.getIntelligence()){
            stat = enemy.getIntelligence();
        }
        return stat;
    }
}
