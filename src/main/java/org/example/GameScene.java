package org.example;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;

public class GameScene extends DefaultScene {
    private Scene scene;
    private final VBox vBox = new VBox();
    public TextFlow gameTextFlow = new TextFlow();
    private final Text timerLabel = new Text();
    private GameTimer gameTimer = new GameTimer(timerLabel);
    private final Paragraph paragraph = new Paragraph(new LanguageService("./dictionary"));
//    private WaveLetterAnimationTimeline waveAnimation = new WaveLetterAnimationTimeline(this.paragraph);
    private TypingHandler handler;
    // Shortcuts handling
    private boolean tabPressed = false;
    private boolean enterPressed = false;

    public void init() {
//        waveAnimation.stop();
        gameTimer.stop();

        GameManager.initGameVariables();
        Paragraph.resetStats();
        Statistics.resetStats();
        gameTimer = new GameTimer(timerLabel);
        paragraph.generateNewParagraph();
        paragraph.setTextFlow(gameTextFlow);
//        waveAnimation = new WaveLetterAnimationTimeline(paragraph);
        timerLabel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

        GameManager.mainStage.setMinWidth(750);
        GameManager.mainStage.setMinHeight(500);

        gameTimer.start();
//        waveAnimation.start();

        if(handler==null) {
            handler = new TypingHandler(paragraph, gameTextFlow);
            vBox.getChildren().addAll(timerLabel, gameTextFlow);

        } else {
            handler.reset();
        }

        gameTextFlow.setLineSpacing(15);
    }


    private EventHandler<KeyEvent> shortCutsHandler() {
        return event -> {
            if (event.getCode() == KeyCode.TAB) {
                tabPressed = event.getEventType() == KeyEvent.KEY_PRESSED;
            } else if (event.getCode() == KeyCode.ENTER) {
                enterPressed = event.getEventType() == KeyEvent.KEY_PRESSED;
            } else if (event.getCode() == KeyCode.ESCAPE) {
                tabPressed = false;
                enterPressed = false;
                gameTimer.stop();
                GameManager.setScreen(GameManager.statsScene);
            } else if (
                    event.getCode() == KeyCode.P && event.isShiftDown() && event.isControlDown()
            ) {
                if(GameManager.gameStop) {
                    gameTimer.start();
                }
                else {
                    gameTimer.stop();
                }
                GameManager.gameStop = !GameManager.gameStop;
            }

            if (tabPressed && enterPressed) {
                init();
                // Reset key states
                tabPressed = false;
                enterPressed = false;
            }
        };
    }


    public Scene getScene() {
        if(scene == null) {
            scene = new Scene(vBox, 750, 500);

            scene.addEventHandler(KeyEvent.KEY_PRESSED, shortCutsHandler());

            scene.setOnKeyPressed(handler.getTypingHandler());

            scene.getStylesheets().add("GameScene.css");

        }

        return scene;
    }
}
