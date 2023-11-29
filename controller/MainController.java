package com.controller;

import com.rpg.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainController implements Initializable {
    @FXML
    private MonitorController monitorController;
    @FXML
    private ScrollController scrollController;
    @FXML
    private ActionController actionController;
    @FXML
    TextArea noteArea;
    private Player player;
    private Enemy enemy;
    RPGLoader rpgLoader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.actionController.injectMainController(this);
        this.monitorController.injectMainController(this);
        this.scrollController.injectMainController(this);

        rpgLoader = new RPGLoader(this);

        loadNoteArea();
    }
    public void continueButtonClicked(){
        rpgLoader.loadSavedGame();
    }
    public void newButtonClicked(){
        rpgLoader.initIntroduction();
    }

    /**
     * Set all Listener for the different controller
     */
    public void addAllListener(){
        Stage stage = (Stage) noteArea.getScene().getWindow();
        stage.setOnCloseRequest(WindowEvent -> {
            WindowEvent.consume();
            rpgLoader.getFileManager().save(player);
            saveNoteArea();
            stage.close();
        });
        this.player.addLabelListener(() -> {
            if(this.player.getHp() <= 0 ){
                this.scrollController.enemyClearText();
            }
            this.scrollController.playerSetText();
        });

        this.player.addLevelListener(() -> {
            this.scrollController.setLevelUpButton();
            this.monitorController.addText("Vous avez gagné un level");
        });
        this.enemy.addListener(clear -> {
            if(clear) scrollController.enemyClearText();
            else scrollController.enemySetText(this.enemy);
        });
        this.actionController.addListener(inventoryOpen -> {
            this.actionController.switchButton.setText(this.actionController.isInventoryOpen() ? "Vue Inventaire" : "Vue Enemie");
            this.getScrollController().switchInventory(this.actionController.isInventoryOpen());
        });
    }
    public void saveNoteArea() {
        String text = this.noteArea.getText();
        File file = new File("NoteArea.txt");
        try(PrintWriter printer = new PrintWriter(file.getAbsolutePath())){
            file.createNewFile();
            printer.print(text);
        } catch (FileNotFoundException e) {
            System.out.println("NoteArea File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadNoteArea(){
        File file = new File("NoteArea.txt");
        StringBuilder text = new StringBuilder();
        try{
            Scanner sc = new Scanner(new File(file.getAbsolutePath()));
            while(sc.hasNext()){
                text.append(sc.nextLine()).append("\n");
            }
            this.noteArea.setText(text.toString());
        } catch (FileNotFoundException e) {
            System.out.println("NoteArea File not found");
        }

    }
    //---------- Getter / Setter ----------
    public void setPlayer(Player player) {
        this.player = player;
    }

    public ActionController getActionController() {
        return this.actionController;
    }

    public MonitorController getMonitorController() {
        return this.monitorController;
    }

    public ScrollController getScrollController() {
        return this.scrollController;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
