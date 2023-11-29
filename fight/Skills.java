package com.fight;

import javafx.scene.control.Button;

public class Skills {
    private final int[][] params;
    private final String name;
    private final String descriptions;
    private final int manaCost;
    private Button button;

    /**
     * @param params Array of instruction 3 int based = [x,y,z] {
     *               x -> action of reference : 0 = miss, 1 = Damage //
     *               y -> reference point of the player : 1 = speed, 2 = strength, 3 = accuracy, 4 = intelligence, DEFAULT : raw value //
     *               z -> reference point of the enemy }
     */
    public Skills(String name, String descriptions, int[][] params, int manaCost) {
        this.name = name;
        this.descriptions = descriptions;
        this.manaCost = manaCost;
        this.params = params;
    }
    //-------- Setter / Getter ---------

    public void setButton(Button button) {
        this.button = button;
    }
    public Button getButton() {
        return button;
    }
    public String getName() {
        return name;
    }
    public String getDescriptions(){
        return descriptions;
    }
    public int[][] getParams() {
        return params;
    }
    public int getManaCost() {
        return manaCost;
    }
}
