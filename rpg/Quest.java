package com.rpg;

public class Quest {
    private final int gold;
    private int id;
    private String name;
    private short itemId;
    private int quantity;
    public Quest(int id, String name, short itemId, int quantity, int gold){
        this.id = id;
        this.name = name;
        this.itemId = itemId;
        this.quantity = quantity;
        this.gold = gold;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public short getItemId() {
        return itemId;
    }
    public int getGold() {
        return gold;
    }
    public int getQuantity() {
        return quantity;
    }
}
