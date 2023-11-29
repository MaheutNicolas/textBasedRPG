package com.rpg;

import com.controller.InventoryController;
import com.fight.SkillManager;
import com.item.Item;
import com.item.ItemTemplate;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final InventoryController inventoryController;
    private final Map<Short, Item> itemsIventory;
    private Map<Short, ItemTemplate> itemTemplateMap;


    public Inventory(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
        this.itemsIventory = new HashMap<>();
    }
    public void addItem(final short key, final int value){

        if(this.itemsIventory.containsKey(key)) {
            this.updateItem(key, this.itemsIventory.get(key).getQuantity()+value);
            return;
        }

        this.itemsIventory.put(key, new Item(value, this.itemTemplateMap.get(key)));
        this.inventoryController.getInventoryPane().getChildren().add(this.itemsIventory.get(key).getMenuButton());
    }
    public void updateItem(final short key, final int newValue) {
        this.itemsIventory.get(key).setQuantity(newValue);
    }
    public void removeItem(final short key, final int value){
        final int newValue = this.itemsIventory.get(key).getQuantity() - value;
        if(newValue <= 0){
            this.inventoryController.getInventoryPane().getChildren().remove(this.itemsIventory.get(key).getMenuButton());
            this.itemsIventory.remove(key);
            return;
        }
        this.updateItem(key, newValue);
    }

    public Map<Short, Item> getItems() {
        return itemsIventory;
    }

    public void setItemTemplateMap(Map<Short, ItemTemplate> itemTemplateMap) {
        this.itemTemplateMap = itemTemplateMap;
    }

    public Map<Short, ItemTemplate> getItemTemplateMap() {
        return itemTemplateMap;
    }

    public void setParams(SkillManager skills, Player player, Enemy enemy) {
        for(ItemTemplate item : itemTemplateMap.values()){
            item.setParams(skills, player, enemy);
        }
    }

    public boolean contain(short key, int value) {
        if(!this.itemsIventory.containsKey(key))return false;
        return this.itemsIventory.get(key).getQuantity() >= value;
    }
}
