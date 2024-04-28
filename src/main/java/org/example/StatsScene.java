package org.example;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class StatsScene extends DefaultScene {
    public void init() {
        Statistics.saveStatsToFile();
        GameManager.mainStage.setMinWidth(750);
        GameManager.mainStage.setMinHeight(500);
    }

    public Scene getScene() {
        Button playBtn = new Button("Play again");

        XYChart.Series currentWpmSeries = Statistics.getCurrentWPMSeries();
        XYChart.Series averageWpmSeries = Statistics.getAverageWPMSeries();

        //Defining X axis
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Seconds");
        //Defining y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Words per minute");

        LineChart linechart = new LineChart(xAxis, yAxis);
        linechart.getData().addAll(averageWpmSeries, currentWpmSeries);

        playBtn.setOnAction(event -> GameManager.setScreen(GameManager.settingsScene));
        playBtn.setMinWidth(150);

        // Stats texts
        Text averageWPMText = new Text("Average WPM: " + Math.round(Statistics.getAverageWPM()));
        Text accuracyText = new Text("Accuracy: " + Statistics.getAccuracyText());
        Text statsText = new Text(Statistics.correctLetters + "/" + Statistics.incorrectLetters + "/" + Statistics.extraLetters + "/" + Statistics.missedLetters);

        VBox vBox = new VBox();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(averageWPMText, accuracyText, statsText);


        vBox.getChildren().addAll(playBtn, linechart, hBox);

        playBtn.setFocusTraversable(false);

        Scene scene = new Scene(vBox, 750, 500);
        scene.getStylesheets().add("StatsScene.css");

        return scene;
    }
}
