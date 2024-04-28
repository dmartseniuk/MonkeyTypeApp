package org.example;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class SettingsScene extends DefaultScene {
    private String[] languageOptions;
    private Integer[] timeOptions;

    public void init() {
        LanguageService languageService = new LanguageService("./dictionary");
        languageOptions = languageService.getLanguagesList();
        timeOptions = new Integer[]{15, 20, 45, 60, 90, 120, 300};
        GameManager.mainStage.setMinWidth(250);
        GameManager.mainStage.setMinHeight(250);
    }

    public Scene getScene() {
        ChoiceBox languageChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(languageOptions));
        ChoiceBox timeChoiceBox = new ChoiceBox<Integer>(FXCollections.observableArrayList(timeOptions));
        Button playBtn = new Button("Play");

        // set default values
        languageChoiceBox.setValue(languageOptions[0]);
        timeChoiceBox.setValue(timeOptions[0]);
        GameManager.setLanguage((String)languageChoiceBox.getValue());
        GameManager.setGameTime((Integer)timeChoiceBox.getValue());


        languageChoiceBox.setOnAction(event -> {
            GameManager.setLanguage((String)languageChoiceBox.getValue());
        });
        timeChoiceBox.setOnAction(event -> {
            GameManager.setGameTime((Integer)timeChoiceBox.getValue());
        });
        playBtn.setOnAction(event -> GameManager.setScreen(GameManager.gameScene));


        languageChoiceBox.setMinWidth(150);
        timeChoiceBox.setMinWidth(150);
        playBtn.setMinWidth(150);

        // Animation the play button
//        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), playBtn);
//        scaleTransition.setToX(0.4);
//        scaleTransition.setToY(0.8);
//        scaleTransition.setCycleCount(Animation.INDEFINITE);
//        scaleTransition.setAutoReverse(true);
//        scaleTransition.play();


        VBox vBox = new VBox();
        vBox.getChildren().addAll(languageChoiceBox, timeChoiceBox, playBtn);

        Scene scene = new Scene(vBox, 250, 250);

        scene.getStylesheets().add("SettingScene.css");


        return scene;
    }
}
