package com.fight;

import com.controller.MonitorController;
import com.phase.FightPhase;
import com.rpg.Enemy;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import java.util.ArrayList;

public class SkillManager {

    private final Player player;
    private final MonitorController monitor;
    private final FlowPane buttonPane;
    private final Skills[] attacksDictionary;
    private FightPhase fightPhase;
    private final ArrayList<Skills> skills;
    private final FightAction[] actions;
    private Enemy enemy;

    public SkillManager(Player player, MonitorController monitor, FlowPane buttonPane) {
        this.player = player;
        this.monitor = monitor;
        this.buttonPane = buttonPane;
        this.skills = new ArrayList<>();

        this.actions = new FightAction[]{
                new MissSkill(),
                new Damage(),
        };

        this.attacksDictionary = new Skills[]{
                (new Skills("coups rapide", "Vous effectuez un coups simple au corps à corps", new int[][]{{1,2,2}}, 0)),
                (new Skills("estoc", "Vous effectuez une attaque de précision", new int[][]{{1,3,3}}, 0))
        };

        for(int i = 0; i <  this.attacksDictionary.length; i++){
            final Skills skills =  this.attacksDictionary[i];
            Button button = new Button(skills.getName());
            skills.setButton(button);
            button.setOnAction(actionEvent -> execute(skills));
        }
    }
    public void execute(Skills skills) {
        monitor.setText(skills.getName()+ " :");
        if(skills.getManaCost() > player.getMana()){
            monitor.addText("Vous n'avez pas assez de mana");
            return;
        }
        monitor.addText(skills.getDescriptions());

        boolean canContinue = true;
        for(int[] param : skills.getParams() ){
            //get player stat of references
            int playerPoint = 0;
            switch (param[1]){
                case 1:
                    playerPoint = player.getSpeed();
                    break;
                case 2:
                    playerPoint = player.getStrength();
                    break;
                case 3:
                    playerPoint = player.getAccuracy();
                    break;
                case 4:
                    playerPoint = player.getIntelligence();
                    break;
                default:
                    playerPoint = param[1];
            }
            //get enemy stat of reference
            int enemyPoint = 0;
            switch (param[2]){
                case 1:
                    enemyPoint = enemy.getSpeed();
                    break;
                case 2:
                    enemyPoint = enemy.getStrength();
                    break;
                case 3:
                    enemyPoint = enemy.getAccuracy();
                    break;
                case 4:
                    enemyPoint = enemy.getIntelligence();
                    break;
                default:
                    enemyPoint = param[2];
            }
            boolean terminated = actions[param[0]].execute(skills, playerPoint, enemyPoint);
            canContinue = canContinue && terminated;
        }
        if(skills.getManaCost() > 0){
            player.setMana(player.getMana() - skills.getManaCost());
            monitor.setText("Vous avez consommer "+ skills.getManaCost()+ " point de mana");
        }
        this.fightPhase.switchTurn(canContinue);
    }
    //------ ENEMY METHOD --------
    public void executeEnemyAttack(Skills skills){
        monitor.addText(skills.getName()+ " :");
        if(skills.getManaCost() > enemy.getMana()){
            monitor.addText(enemy.getName() +" essaye de lancer son attaque mais échoue");

            return;
        }
        monitor.addText(skills.getDescriptions());

        boolean canContinue = true;
        for(int[] param : skills.getParams() ){
            //get player stat of references
            int playerPoint = 0;
            //get enemy stat of reference
            int enemyPoint = 0;

            switch (param[1]){
                case 1:
                    enemyPoint = enemy.getSpeed();
                    break;
                case 2:
                    enemyPoint = enemy.getStrength();
                    break;
                case 3:
                    enemyPoint = enemy.getAccuracy();
                    break;
                case 4:
                    enemyPoint = enemy.getIntelligence();
                    break;
                default:
                    enemyPoint = param[2];
            }
            switch (param[2]){
                case 1:
                    playerPoint = player.getSpeed();
                    break;
                case 2:
                    playerPoint = player.getStrength();
                    break;
                case 3:
                    playerPoint = player.getAccuracy();
                    break;
                case 4:
                    playerPoint = player.getIntelligence();
                    break;
                default:
                    playerPoint = param[1];
            }
            boolean terminated = actions[param[0]].enemyExecute(skills,  enemyPoint, playerPoint);
            canContinue = canContinue && terminated;
        }
        if(skills.getManaCost() > 0){
            enemy.setMana(enemy.getMana() - skills.getManaCost());
            monitor.addText(enemy.getName()+ " a consommé "+ skills.getManaCost()+ " point de mana");
        }
        this.fightPhase.checkPlayerState();
    }

    //-------setter / getter -------
    public void setSkills(ArrayList<Integer> attacksRef){
        this.skills.clear();
        for(int i = 0; i < attacksRef.size(); i++){
            this.skills.add( attacksDictionary[ attacksRef.get(i) ]);
        }
    }
    public void getButtons() {
        this.buttonPane.getChildren().clear();
        for(Skills skills : this.skills){
            buttonPane.getChildren().add(skills.getButton());
        }
    }
    public void setFightPhase(FightPhase fightPhase) {
        this.fightPhase = fightPhase;

    }
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        for(FightAction action : actions){
            action.setEntity(player, enemy);
            action.setMonitor(monitor);
        }
    }
}
