package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameManager.initVariables(primaryStage);

        primaryStage.setTitle("Monkeytype clone");

        GameManager.setScreen(GameManager.settingsScene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}