package org.example;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameTimer {
    private Text timerText;

    private Timeline timeline;

    private Color currentColor = Color.BLACK;


    GameTimer(Text timerText) {
        this.timerText = timerText;
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.1),
                        event -> {
                            GameManager.countdownSeconds-=0.1;
                            this.timerText.setText(String.valueOf(Math.round(GameManager.countdownSeconds)));

                            if(GameManager.countdownSeconds> GameManager.getGameTime() * 0.4 && currentColor != Color.GREEN) {
                                FillTransition fillTransition = new FillTransition(Duration.seconds(1), this.timerText, currentColor, Color.GREEN);
                                currentColor = Color.GREEN;
                                fillTransition.play();
                            } else if(GameManager.countdownSeconds <= GameManager.getGameTime() * 0.4 && GameManager.countdownSeconds> GameManager.getGameTime() * 0.2 && currentColor!= Color.ORANGE) {
                                FillTransition fillTransition = new FillTransition(Duration.seconds(1), this.timerText, currentColor, Color.ORANGE);
                                currentColor = Color.ORANGE;
                                fillTransition.play();
                            } else if(GameManager.countdownSeconds <= GameManager.getGameTime() * 0.2 && currentColor!= Color.RED) {
                                FillTransition fillTransition = new FillTransition(Duration.seconds(1), this.timerText, currentColor, Color.RED);
                                currentColor = Color.RED;
                                fillTransition.play();
                            }

                            if (GameManager.countdownSeconds <= 0) {
                                timeline.stop();
                                GameManager.setScreen(GameManager.statsScene);
                            }
                        }));
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}
