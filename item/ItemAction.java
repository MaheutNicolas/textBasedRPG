package com.item;

import com.fight.SkillManager;
import com.fight.Skills;
import com.rpg.Enemy;
import com.rpg.Player;

public class ItemAction {
    private final int action;
    private final String args;
    private final String conditions;
    private final String name;
    private short idTemplate;
    private SkillManager skills;
    private Player player;
    private Enemy enemy;

    public ItemAction(String name, int action, short idTemplate, String args, String conditions){
        this.name = name;
        this.action = action;
        this.idTemplate = idTemplate;
        this.args = args;
        this.conditions = conditions;

    }
    public void execute(){
        switch(action){
            case 0:
                break;
            case 1:
                craft();
                break;
            case 2:
                Dommage();
                break;
        }

    }

    /**
     * conditions = Conditions in String format
     * args = String  Imput item Quantity, Output item Quantity, id Template of the item crafted
     */
    private void craft() {

        final String[] sCdts = conditions.split(",");
        int[] cdtsArray = new int[sCdts.length];
        int i = 0;
        for(String cdt : sCdts){
            cdtsArray[i] = Integer.parseInt(cdt);
            i++;
        }
        if ( this.player.getCurrentPosition().getValue() != cdtsArray[0] ) return;

        final String[] sArgs = args.split(",");
        int[] argsArray = new int[sArgs.length];
        i = 0;
        for(String arg : sArgs){
            argsArray[i] = Integer.parseInt(arg);
            i++;
        }
        this.player.removeItem(idTemplate,  argsArray[0]);
        this.player.addItem((short) argsArray[2], argsArray[1]);
    }

    /**
     * cdts :  integer array of the value of enemyType   (0 = allType)
     * args :  Raw Damage
     */
    private void Dommage() {

        if ( player.getCurrentPosition().getValue() != Player.TypePlace.FIGHT.getValue()) return;

        player.removeItem(idTemplate,  1);

        final String[] sCdts = conditions.split(",");

        final String[] sArgs = args.split(",");
        int[] argsArray = new int[sArgs.length];
        int i = 0;
        for(String arg : sArgs){
            argsArray[i] = Integer.parseInt(arg);
            i++;
        }

        for(String cdt : sCdts){
            int cdtInt = Integer.parseInt(cdt);
            if ( cdtInt != 0 ||  cdtInt != enemy.getType().getValue()) continue;
            skills.execute(new Skills(name, "Vous utillisez "+name, new int[][]{{1,argsArray[0],0}},0));
            return;
        }
       skills.execute(new Skills(name, "Vous utillisez "+name, new int[][]{{0,0,0}},0));
    }

    public void setParams(SkillManager skills, Player player, Enemy enemy) {
        this.skills = skills;
        this.player = player;
        this.enemy = enemy;
    }
}
