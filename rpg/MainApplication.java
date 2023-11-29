package com.rpg;

import com.controller.MainController;
import com.controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("Menu.fxml"));
        Parent pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        stage.setTitle("DungeonSeeker Tales");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void logout(Stage stage) {
        stage.close();
    }

    public static void main(String[] args) {
        launch();
    }
}