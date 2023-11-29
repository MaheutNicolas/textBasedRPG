package com.phase;

import com.controller.MonitorController;
import com.rpg.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import java.util.Random;

public class ExplorationPhase extends Phase {

    private final Random random = new Random();
    private final Player player;
    private final Button explorationButton;
    private final Button restButton;
    private final Button nextStageButton;
    private final MonitorController monitor;
    private Button[] stageButton;
    private final Button exitDJButton;
    ExplorationPhase(Player player, FlowPane buttonPane, MonitorController monitor) {
        super(buttonPane);
        this.monitor = monitor;
        this.player = player;

        this.explorationButton = new Button("Avancer");
        this.restButton = new Button("Se reposer");
        this.nextStageButton = new Button("Monter d'un étage");
        this.exitDJButton = new Button("Sortir du Donjon");

        this.explorationButton.setOnAction(event -> setNextDungeonRoom());
        this.restButton.setOnAction(actionEvent -> {
            player.healHp();
            player.healMana();
        });
        this.nextStageButton.setOnAction(actionEvent -> setNextStage());
        this.exitDJButton.setOnAction(actionEvent -> {
            player.setDungeonEventNumber(0);
            onExit.accept(PhaseManager.TypePhase.PASSIVE);});
        setStageButton();
    }


    @Override
    void onStart() {
        this.player.setCurrentPosition(Player.TypePlace.DUNGEON);
        setExplorationButton();
    }
    private void setExplorationButton(){
        buttonPane.getChildren().addAll(explorationButton, restButton);
        if( this.player.isNextStageAvailable()) {
            buttonPane.getChildren().add(nextStageButton);
        }
        setStageButton();
        if( this.player.getDungeonEventNumber() == 0){
            buttonPane.getChildren().addAll(stageButton);
            firstRoom();
        }
        buttonPane.getChildren().add(exitDJButton);
    }
    private void setStageButton() {
        this.stageButton = new Button[player.getDungeonHighestStage()];
        for(int i = 0; i < stageButton.length; i++ ){
            this.stageButton[i] = new Button(String.valueOf(i));
            int finalI = i;
            this.stageButton[i].setOnAction(ActionEvent -> teleport(finalI));
        }
    }

    public void firstRoom(){
        this.monitor.addText("Vous vous trouvez dans la salle des téléporteurs");
        this.monitor.addText("Vous pouvez vous téléporter en dehors, ou à un étage déja visité");

    }

    protected void setNextDungeonRoom() {
        if (player.getHp() <= 0) {
            this.monitor.setText("vous n'avez pas assez de HP");
            return;
        }
        if(stageButton.length > 0 && buttonPane.getChildren().contains(stageButton[0])){
            buttonPane.getChildren().removeAll(stageButton);
        }
        this.monitor.setText("Vous avancez jusqu'a la prochaine pièce");
        this.player.setDungeonEventNumber(player.getDungeonEventNumber() + 1);
        int probability = random.nextInt(100);
        if (player.getDungeonEventNumber() == player.getDungeonBeforeNextStage()) {
            this.monitor.addText("Vous aperçevez l'escalier pour accèder a l'étages suivant");
            this.player.setNextStageAvailable(true);
        }
        if (probability < 60) {
            onExit.accept(PhaseManager.TypePhase.FIGHT);
        } else if (probability < 80) {
            onExit.accept(PhaseManager.TypePhase.EVENT);
        } else {
            this.monitor.addText("Le couloir du donjon est vide");
        }

    }

    @Override
    void onEnd() {
        buttonPane.getChildren().clear();
    }

    protected void setNextStage() {
        this.player.setNextStageAvailable(false);
        this.player.setDungeonEventNumber(0);
        this.player.setDungeonBeforeNextStage(random.nextInt(100) + 70);
        this.player.setDungeonStage(player.getDungeonStage() + 1);
        this.monitor.setMonitorPlace("étage "+ player.getDungeonStage());
        this.monitor.addText("Vous monter d'un étage pour atteindre l'étage " + player.getDungeonStage());
        if (player.getDungeonStage() >= player.getDungeonHighestStage()) {
            player.setDungeonHighestStage(player.getDungeonStage());
        }
        buttonPane.getChildren().clear();
        setExplorationButton();
    }
    private void teleport(int stage){
        this.monitor.setMonitorPlace("étage "+ player.getDungeonStage());
        this.monitor.setText("Vous êtes téléporter à l'étage " + stage);
        this.player.setDungeonStage(stage);
        this.player.setDungeonEventNumber(0);
        firstRoom();
    }
}
