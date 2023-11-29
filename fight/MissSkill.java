package com.fight;

public class MissSkill extends FightAction {
    @Override
    boolean execute(Skills skills, int playerPoint, int enemyPoint) {
        getMonitor().addText("Aucun dégat n'a été infligé");
        return true;
    }

    @Override
    boolean enemyExecute(Skills skills, int playerPoint, int enemyPoint) {
        return true;
    }
}
