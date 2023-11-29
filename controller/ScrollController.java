package com.controller;

import com.rpg.Enemy;
import com.rpg.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ScrollController implements Initializable {
    @FXML
    Button hpButton;
    @FXML
    Button manaButton;
    @FXML
    Button speedButton;
    @FXML
    Button strengthButton;
    @FXML
    Button accuracyButton;
    @FXML
    Button intelButton;
    @FXML
    ImageView statImage;
    @FXML
    ImageView enemyScrollImage;
    @FXML
    ImageView fightIconImage;
    @FXML
    Label enemyLabel;
    @FXML
    Label playerLabel;
    @FXML
    private InventoryController inventoryController;
    private Player player;
    private MainController mainController;
    private ScrollPane scrollInventoryPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inventoryController.injectScrollController(this);
        scrollInventoryPane = inventoryController.getScrollPane();

        hpButton.setOnAction(actionEvent -> setLevelUpPoint(TypeStat.HP));
        manaButton.setOnAction(actionEvent -> setLevelUpPoint(TypeStat.MANA));
        speedButton.setOnAction(actionEvent -> setLevelUpPoint(TypeStat.SPEED));
        strengthButton.setOnAction(actionEvent -> setLevelUpPoint(TypeStat.STRENGTH));
        accuracyButton.setOnAction(actionEvent -> setLevelUpPoint(TypeStat.ACCURACY));
        intelButton.setOnAction(actionEvent -> setLevelUpPoint(TypeStat.INTEL));
        switchVisibleButtonStat(false);

    }
    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void playerSetText() {

        StringBuilder text = new StringBuilder( player.getName() + "\n");
        text.append("Niveau : ").append(player.getLevel()).append("\n");
        text.append("Exp: ").append(player.getExperience()).append(" / ").append(player.getExperienceMax()).append("\n\n");
        text.append("HP : ").append(player.getHp()).append("\n");
        text.append("Mana : ").append(player.getMana()).append("\n");
        text.append("Vitesse : ").append(player.getSpeed()).append("\n");
        text.append("Force : ").append(player.getStrength()).append("\n");
        text.append("Précision : ").append(player.getAccuracy()).append("\n");
        text.append("Intel : ").append(player.getIntelligence()).append("\n\n");
        text.append("Pièce d'or : ").append(player.getGold()).append("\n");

        if(player.getAwakening() >= 1){
            text.append("Eveil : ").append(player.getAwakening()).append("\n");
        }
        text.append("Point Restant : ").append(player.getLevelUpPoint()).append("\n");
        playerLabel.setText(text.toString());
    }

    public void enemySetText(Enemy enemy){
        String text = "      " + enemy.getName() + "\n" +
                "HP : " + enemy.getHp() + "\n" +
                "Mana : " + enemy.getMana() + "\n" +
                "Level : "+ enemy.getLevel() + "\n"+
                "Vitesse : " + enemy.getSpeed() + "\n" +
                "Force : " + enemy.getStrength() + "\n" +
                "Précision : " + enemy.getAccuracy() + "\n" +
                "Intel : " + enemy.getIntelligence() + "\n";
        enemyLabel.setText(text);
    }
    public void enemyClearText(){
        enemyLabel.setText("");
    }

    public void switchInventory(boolean inventoryOpen) {
        scrollInventoryPane.setVisible(!inventoryOpen);
        enemyLabel.setVisible(inventoryOpen);
        statImage.setVisible(!inventoryOpen);
        enemyScrollImage.setVisible(inventoryOpen);
        fightIconImage.setVisible(inventoryOpen);
    }

    public void setLevelUpButton() {
        switchVisibleButtonStat(true);
    }
    private void setLevelUpPoint(TypeStat typeStat) {
        switch(typeStat) {
            case HP:
                player.setMaxHP(player.getMaxHP()+10);
                player.setHp(player.getMaxHP());
                break;
            case MANA:
                player.setMaxMana(player.getMaxMana()+10);
                player.setMana(player.getMaxMana());
                break;
            case SPEED:
                player.setSpeed(player.getSpeed()+1);
                break;
            case INTEL:
                player.setIntelligence(player.getIntelligence()+1);
                break;
            case ACCURACY:
                player.setAccuracy(player.getAccuracy()+1);
                break;
            case STRENGTH:
                player.setStrength(player.getStrength()+1);
                break;
        }
        player.setLevelUpPoint(player.getLevelUpPoint()-1);
        if(player.getLevelUpPoint() == 0){
            switchVisibleButtonStat(false);
        }
    }

    public void switchVisibleButtonStat(final boolean visible) {

        hpButton.setVisible(visible);
        manaButton.setVisible(visible);
        speedButton.setVisible(visible);
        strengthButton.setVisible(visible);
        accuracyButton.setVisible(visible);
        intelButton.setVisible(visible);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public InventoryController getInventoryController() {
        return inventoryController;
    }

    private enum TypeStat{
        HP,
        MANA,
        INTEL,
        SPEED,
        STRENGTH,
        ACCURACY
    }
}
