package com.item;

import com.fight.SkillManager;
import com.rpg.Enemy;
import com.rpg.Player;

public final class ItemTemplate {
    private final String name;
    private final short id;
    private final int price;
    private final ItemAction[] actions;
    private Player player;

    public ItemTemplate(String name, short id, String[] actions, String[] args, String[] conditions, int price){
        this.name = name;
        this.id = id;
        this.price = price;
        this.actions = new ItemAction[actions.length];
        int i = 0;
        for(String action : actions){
            this.actions[i] = new ItemAction(name, Integer.parseInt(action), getId(), args[i], conditions[i]);
            i++;
        }
    }
    public void executeActions() {
        for(final ItemAction action : this.actions) action.execute();
    }
    public String getName() {
        return name;
    }

    public short getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setParams(SkillManager skills, Player player, Enemy enemy) {
        this.player = player;
        for(ItemAction action : actions){
            action.setParams(skills, player, enemy);
        }
    }
    public Player getPlayer() {
        return player;
    }
}
