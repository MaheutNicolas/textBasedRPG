package com.rpg;

import com.controller.InventoryController;
import com.controller.MainController;
import com.fight.SkillManager;
import com.item.ItemTemplate;
import com.phase.PhaseManager;

import java.util.Map;

public class RPGLoader {
    private final MainController mainController;
    private Player player;
    private Enemy enemy;
    private final DataBase dataBase;
    private final Inventory inventory;
    private SkillManager skills;

    private final Map<Short, ItemTemplate> itemTemplateMap;

    //create the first object and prepare for the game start
    public RPGLoader(MainController mainController){
        this.mainController = mainController;
        this.dataBase = new DataBase();
        this.enemy = new Enemy();
        mainController.setEnemy(enemy);
        InventoryController inventoryController = mainController.getScrollController().getInventoryController();
        this.inventory = new Inventory(inventoryController);
        itemTemplateMap = dataBase.loadItemsTemplate();
        this.inventory.setItemTemplateMap(itemTemplateMap);

    }
    //The first method is call when the new game is click, and at the end of Introduction, loadNewGame() is call
    public void initIntroduction(){
        new Introduction( this.mainController, this);
    }
    public void loadNewGame(Player player){
        this.player = player;
        this.player.setInventory(inventory);
        loadGame();
    }
    //method executed when the continue Button is clicked
    public void loadSavedGame(){
        this.player = this.dataBase.load(inventory);
        if(this.player.getLevelUpPoint() > 0) {
            this.mainController.getScrollController().switchVisibleButtonStat(true);
        }
        loadGame();
    }
    //general method for start the game, call by the 2 above
    private void loadGame(){
        this.mainController.setPlayer(player);
        this.mainController.addAllListener();
        this.player.expMaxForLevelUp();
        this.mainController.getScrollController().setPlayer(player);
        this.mainController.getScrollController().playerSetText();
        this.skills = new SkillManager(player, mainController.getMonitorController(), mainController.getActionController().getButtonPane());
        this.player.setSkills(skills);
        this.inventory.setParams(skills, player, enemy);
        loadPhaseManager();

    }
    public DataBase getFileManager() {
        return this.dataBase;
    }

    //launch the game logic (Phase) and finish init.
    public void loadPhaseManager() {
        PhaseManager phaseManager = new PhaseManager(this.mainController, this.player, this.enemy, getFileManager(), skills, itemTemplateMap);
        skills.setFightPhase(phaseManager.getFightPhase());
        player.setAttackMove();
    }

}
