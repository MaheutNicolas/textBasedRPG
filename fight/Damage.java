package com.fight;

public class Damage extends FightAction{
    Damage(){

    }@Override
    boolean execute(Skills skills, int playerPoint, int enemyPoint) {

        int damage = 0;
        if(enemyPoint >= playerPoint){
            damage = (int) Math.round(playerPoint*0.2);
        }
        else {
            damage = (int) Math.round(playerPoint - (enemyPoint * 80/100));
        }
        int newHP = getEnemy().getHp() - damage;
        getEnemy().setHp(newHP);
        getMonitor().addText( damage +" point de dégat ont été infligez");

        return true;
    }

    @Override
    boolean enemyExecute(Skills skills, int enemyPoint, int playerPoint) {
        int damage = 0;
        if( playerPoint >= enemyPoint ){
            damage = (int) Math.round(enemyPoint*0.2);
        }
        else {
            damage = (int) Math.round(enemyPoint - (playerPoint * 80/100));
        }
        int newHP = getPlayer().getHp() - damage;
        getPlayer().setHp(newHP);
        getMonitor().addText( damage +" point de dégat ont été infligez");

        return false;
    }
}

