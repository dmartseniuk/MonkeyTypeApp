package org.example;

import javafx.stage.Stage;

public class GameManager {
    // config
    public static int nParagraphWords = 30;

    // window elements
    public static Stage mainStage;
    public static DefaultScene settingsScene, gameScene, statsScene;

    // Game variables
    private static String language;
    private static double gameTime;

    public static Double countdownSeconds = 0.0;
    public static boolean gameStop = false;


    // Getters and setters
    public static void setLanguage (String _language) {
        language = _language;
    }
    public static String getLanguage () {
        return language;
    }
    public static void setGameTime (Integer _gameTime) {
        gameTime = _gameTime;
    }
    public static double getGameTime () {
        return gameTime;
    }

    public static void initVariables(Stage _mainStage) {
        mainStage = _mainStage;
        settingsScene = new SettingsScene();
        gameScene = new GameScene();
        statsScene = new StatsScene();
    }

    public static void initGameVariables() {
        countdownSeconds = gameTime;
    }

    public static void setScreen(DefaultScene scene) {
       scene.init();
       mainStage.setScene(scene.getScene());
    }
}
