package com.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button continuButton;
    @FXML
    private Label menuLabel;
    @FXML
    private Button newButton;
    @FXML
    private Button quitButton;
    private Stage stage;
    private MainController mainController;
    private Parent pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newButton.setOnAction(ActionEvent -> startNewGame());
        quitButton.setOnAction(ActionEvent -> stage.close());
        continuButton.setOnAction(ActionEvent -> startGame());
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("RpgView.fxml"));
        try {
            pane = loader.load();
        } catch (IOException e) {
            System.out.println("cannot load");
            throw new RuntimeException(e);
        }
        mainController = loader.getController();
        try(final Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            final ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM players");){
            rs.getInt(1);
        }
        catch (SQLException e){
            continuButton.setDisable(true);
            System.out.println("Player not found");
        }
    }
    @FXML
    public void startGame() {
        loadMainController();
        mainController.continueButtonClicked();
    }
    private void startNewGame() {
        loadMainController();
        mainController.newButtonClicked();
    }
    private void loadMainController(){
            stage = (Stage) menuLabel.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
    }
}
