package com.fight;

import com.controller.MonitorController;
import com.rpg.Enemy;
import com.rpg.Player;

abstract public class FightAction {
    private Player player;
    private Enemy enemy;
    private MonitorController monitor;

    abstract boolean execute(Skills skills, int playerPoint, int enemyPoint);
    abstract boolean enemyExecute(Skills skills, int playerPoint, int enemyPoint);

    public void setEntity(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }
    public void setMonitor(MonitorController monitor) {
        this.monitor = monitor;
    }
    public Player getPlayer() {
        return player;
    }
    public Enemy getEnemy() {
        return enemy;
    }

    public MonitorController getMonitor() {
        return monitor;
    }
}
