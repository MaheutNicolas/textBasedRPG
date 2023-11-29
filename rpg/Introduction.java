package com.rpg;

import com.controller.MainController;
import com.controller.MonitorController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

public class Introduction {
    private Player player;
    private final MonitorController monitor;
    private final FlowPane buttonPane;
    private final Button forwardButton;
    private final Button backwardButton;
    private final Button exitIntro;
    private final TextField nameField;
    int position = 1;
    private String[] introductionMessage;
    private final RPGLoader RPGLoader;
    public Introduction(MainController mainController, RPGLoader RPGLoader) {
        this.RPGLoader = RPGLoader;
        monitor = mainController.getMonitorController();
        buttonPane = mainController.getActionController().getButtonPane();

        nameField = new TextField();
        nameField.setFont(new Font("DejaVu Sans Bold", 14));

        Button validNameField = new Button("Valider");
        forwardButton = new Button("Suivant");
        backwardButton = new Button("Précédant");
        exitIntro = new Button("Commencer");

        validNameField.setOnAction(ActionEvent -> validAndStart());
        forwardButton.setOnAction(ActionEvent -> NextMessage());
        backwardButton.setOnAction(ActionEvent -> LastMessage());
        exitIntro.setOnAction(ActionEvent -> startGame());

        monitor.setMonitorPlace("Introduction");
        monitor.setText("Bienvenu dans DungeonSeeker Tales\nMais commençons dès à présent, Quel est votre nom ?");

        buttonPane.getChildren().addAll(nameField, validNameField);
    }

    private void startGame() {
        buttonPane.getChildren().clear();
        RPGLoader.loadNewGame(this.player);
    }
    private void validAndStart() {
        if(nameField.getText().equals(""))return;
        this.player = new Player.PlayerBuilder(nameField.getText()).build();
        this.buttonPane.getChildren().clear();
        this.buttonPane.getChildren().addAll(forwardButton, backwardButton);
        initMessageTable();
        NextMessage();
    }
    private void NextMessage(){
        if(position < introductionMessage.length-1){
            position++;
            monitor.setText(introductionMessage[position]);
        }
        if(position == introductionMessage.length - 1 && !buttonPane.getChildren().contains(exitIntro)){
            buttonPane.getChildren().add(exitIntro);
        }

    }
    private void LastMessage(){
        if(position > 0) {
            position--;
            monitor.setText(introductionMessage[position]);
        }
    }
    private void initMessageTable() {
        introductionMessage = new String[]{
                "Bienvenu dans DungeonSeeker Tales\nMais commençons dès à présent, Quel est votre nom ?",
                "Etant née dans une famille modeste,\nvous n'avez eu accès que a très peu de littérature\nVous ne savez donc que très peu de choses sur le mondes qui vous entours.",
                "Lors de vos 18 ans, une marque apparu sur votre main\nne vous laissant plus que le choix d'êtres Seeker,\nAventurier explorant les célèbres Donjon.",
                "Vous dites une dernière fois aurevoir a votre famille\ncomme le veux la tradition, vous vous dirigez vers l'un des grands Donjon,\népreuve laissé par dieux pour tester les mortels.",
                "Vous savez que n'importe quel mortels peuvent rentrée\nmais que seul les élu peuvent en sortir.\nCes élu possèdent une marque qui leur permet de défié la mort.\nEn effets, un élu a sa mort, retourne a l'entrée.",
                "Au cours de votre voyage, vous rencontrez différents voyageurs et leur histoires.\nTous font preuve d'un grand respect pour les seeker,\nmême les gardes des différentes villes se montre tès accueillant en votre égard.",
                "Vous apprennez ainsi moulte connaissance sur le mondes vous entourant.\nLes seekers sont des êtres élu pour perçés les secrets que dieux a laissé\nPersonne ne s'oppose a eux car il rammène des richesses et des connaissances inédites.",
                "Vous passez par divers villes au couleurs et au traditions bien différentes,\nMais votre tâche vous retiens de vous arrêtez.\nC'est ainsi que vous arrivez à votre destination\nLa villes du 3eme Grand Donjon, une villes avec une immense tours surponblant les environs",
                "Vous vous approchez de l'entrée de la villes\net vous montrez votre marque au gardes.\nUn des gardes vous conduit vers le chef des gardes\net en entrant dans son bureau,\nvous apperçevez un homme imposant, avec une balaffre traversant le visage",
                "Le capitaine vous montre en premier la Guilde des aventuriers,\nelle est le point centrale des connaissances pratique et\ntechniques sur ces guerriers explorateurs.\nVous y trouverez aussi des quêtes a remplirs.",
                "Vient ensuite l'église,\nbatiments abritant l'élément sacrée.\nUn endroits remplies de miracles.",
                "Le capitaine enchaîne avec l'entrée du donjon,\nVous pourrez rentrée dans ce lieux regorgant de danger et de trésors,\naprès avoir passez le poste de guarde à l'entrée.",
                "Après le donjon, vous entrez ensemble sur le terrain d'entrainement, Vous montrez vos compétences à l'épée qui sont encore immature.\nVotre attaque ce base sur votre force pour infliger des dégats bruts sur le corps de l'énemie (sa force). Le capitaine vous aprend Estoc, qui utillise votre Précision pour infligé des dégats au point vitaux (précision aussi)",
                "Enfin, il vous conduit à la taverne,\nlieux de repos et votre base.\n* Pensez à Sauvegarder *"
        };
    }
}
