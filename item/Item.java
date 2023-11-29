package com.item;

import com.fight.SkillManager;
import com.rpg.Enemy;
import com.rpg.Player;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class Item {
    private int quantity;
    private final MenuButton menuButton;
    private final MenuItem use;
    private final MenuItem sell;
    private final ItemTemplate itemTemplate;
    private Player player;

    public Item(int quantity, ItemTemplate itemTemplate) {
        this.quantity = quantity;
        this.itemTemplate = itemTemplate;

        this.menuButton = new MenuButton();
        this.menuButton.setMinWidth(215);

        this.use = new MenuItem("Utilliser");
        this.sell = new MenuItem("Vendre");

        this.use.setOnAction((event) -> useItem());
        this.sell.setOnAction((event) -> sellItem());

        this.menuButton.getItems().addAll(use,sell);
        updateMenuButton();
    }
    public void useItem(){
        this.itemTemplate.executeActions();
    }
    public void sellItem(){
        if(itemTemplate.getPlayer().getCurrentPosition() != Player.TypePlace.MERCHANT)return;
    }
    public MenuButton getMenuButton() {
        return menuButton;
    }

    public void updateMenuButton() {
        this.menuButton.setText(this.itemTemplate.getName() + " - " + getQuantity());
    }

    //----- Getter / Setter -------

    public void setQuantity(int quantity){
        this.quantity = quantity;
        updateMenuButton();
    }
    public int getQuantity() {
        return quantity;
    }

    public MenuItem getSell() {
        return sell;
    }

}
