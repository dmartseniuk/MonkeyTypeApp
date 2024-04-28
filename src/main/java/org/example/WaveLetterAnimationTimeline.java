package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class WaveLetterAnimationTimeline {
    private Timeline timeline;
    private int animateLetterIndex = 0;
    private Paragraph paragraph;

    WaveLetterAnimationTimeline(Paragraph paragraph) {
        this.paragraph = paragraph;
        this.timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.1),
                        new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                paragraph.getTextLetters().get(animateLetterIndex).animate();
                                if(animateLetterIndex<paragraph.getTextLetters().size()-1) {
                                    animateLetterIndex++;
                                }
                                else {
                                    animateLetterIndex = 0;
                                }
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
