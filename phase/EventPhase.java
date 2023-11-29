package com.phase;

import com.controller.MonitorController;
import com.djevent.*;
import com.item.Item;
import com.item.ItemTemplate;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.Map;
import java.util.Random;

public class EventPhase extends Phase{

    private final Random random = new Random();
    private final Player player;
    private final MonitorController monitor;
    private final Button explorationButton;
    private final Button InspectButton;
    private final Button hitButton;
    private final Button speakButton;
    private final Button tasteButton;
    private final Button manaInspectButton;
    private final Button mechanismButton;
    private final Hit hit;
    private final Inspection inspection;
    private final ManaInspect manaInspect;
    private final Mechanism mechanism;
    private final Speak speak;
    private final Taste taste;
    private int eventRef;
    private int eventCount = 0;
    EventPhase(Player player, FlowPane buttonPane, MonitorController monitor){
        
        super(buttonPane);
        this.player = player;
        this.monitor = monitor;

        this.inspection = new Inspection(this, monitor);
        this.hit = new Hit(this, monitor);
        this.speak = new Speak(this, monitor);
        this.taste = new Taste(this, monitor, player);
        this.manaInspect = new ManaInspect(this, monitor);
        this.mechanism = new Mechanism(this, monitor);

        this.explorationButton = new Button("Avancer");
        this.InspectButton = new Button("Investiguer");
        this.hitButton = new Button("Frapper");
        this.speakButton = new Button("Communiqué");
        this.tasteButton = new Button("Goûter");
        this.manaInspectButton = new Button("Sense Magique");
        this.mechanismButton = new Button("Activer un Méchanisme");

        this.explorationButton.setOnAction(ActionEvent -> onExit.accept(PhaseManager.TypePhase.NEXTROOM));
        this.InspectButton.setOnAction(ActionEvent -> inspection.execute());
        this.hitButton.setOnAction(ActionEvent -> hit.execute());
        this.speakButton.setOnAction(ActionEvent -> speak.execute());
        this.tasteButton.setOnAction(ActionEvent -> taste.execute());
        this.manaInspectButton.setOnAction(ActionEvent -> manaInspect.execute());
        this.mechanismButton.setOnAction(ActionEvent -> mechanism.execute());

    }

    @Override
    void onStart() {
        this.player.setCurrentPosition(Player.TypePlace.EVENT);
        buttonPane.getChildren().addAll(explorationButton, InspectButton, hitButton, speakButton,
                tasteButton, manaInspectButton, mechanismButton);
        setNextEvent(random.nextInt(2));
    }
    private void setNextEvent(int eventRef) {
        this.eventRef = eventRef;
        switch (eventRef) {
            case 0:
                monitor.addText("Vous aperçevez un mur craquelé");
                break;
            case 1:
                monitor.addText("Vous voyez une fontaine centrale au centre du couloir");
                break;
            case 2:
                break;
        }
    }
    @Override
    void onEnd() {
        buttonPane.getChildren().clear();
    }

    public boolean isEventAvailable() {
        if (eventCount == 2) {
            monitor.addText("Tous disparaît comme par magie, et vous vous retrouvez dans un couloirs vide");
            setExplorationPhase();
            return false;
        }
        eventCount++;
        return true;
    }
    public void eventReward(int num){
        switch (num){
            case 0:
                monitor.addText("Vous frappez le mur et une faille apparaît");
                if(random.nextInt(2) == 1){
                    player.setGold(player.getGold()+20);
                    monitor.addText("Vous recevez 20 pièces d'or");
                }
                else{
                    Map<Short, ItemTemplate> item = player.getInventory().getItemTemplateMap();
                    short rdm = (short) random.nextInt(item.size());
                    player.addItem( rdm , 1);
                    System.out.println(rdm);
                    monitor.addText("Vous receçevez : " + item.get(rdm).getName());
                }
                break;
            case 1:
                monitor.addText("la fontaine vous confère une partie de ça magie");
                player.setMaxMana(player.getMaxMana()+5);
                player.healMana();
                monitor.addText("Vous avez gagné 5 point de Mana " +
                        "La fontaine disparaît au moment ou la magie s'arrête");
                break;
                //TODO equillibrer Recompense (ex : 5 pts de mana c'est de trops)
        }
        setExplorationPhase();
    }
    public void eventLoss(int num){
        switch (num){
            case 0:
                int damage = player.getHp()/10 ;
                player.setHp(player.getHp() - damage);
                monitor.addText("Vous goûtez à un morceau du mur, vous souffrez d'empoissonement et perdez "
                        + damage+ " hp");
                break;
            case 1:
                break;
        }
        setExplorationPhase();
    }

    public int getEventRef() {
        return eventRef;
    }

    private  void setExplorationPhase(){
        eventCount = 0;
        onExit.accept(PhaseManager.TypePhase.EXPLORATION);
    }
}
