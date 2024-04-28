package org.example;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TextLetter {

    private Text text;
    private PathTransition pathTransition;
    private boolean originalParagraph;
    private LetterState letterState = LetterState.DEFAULT;

    TextLetter(char letter, boolean originalParagraph) {
        this.text = new Text();
        this.text.setFont(Font.font("Courier New", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        this.text.setFill(Color.GRAY);
        this.text.setText("" + letter);

        this.pathTransition = new PathTransition(Duration.seconds(0.5), this.text);
        Path path = new Path();
        path.getElements().add(new MoveTo(this.text.getLayoutBounds().getWidth()/2,12));
        path.getElements().add(new LineTo(this.text.getLayoutBounds().getWidth()/2, -10));
        path.getElements().add(new LineTo(this.text.getLayoutBounds().getWidth()/2, 12));
        this.pathTransition.setNode(this.text);
        this.pathTransition.setPath(path);

        this.originalParagraph = originalParagraph;
    }

    public LetterState getLetterState() {
        return letterState;
    }

    public void setTextState(LetterState textState) {
        switch (textState) {
            case CORRECT:
                this.text.setFill(Color.GREEN);
                letterState = textState;
                break;
            case INCORRECT:
                this.text.setFill(Color.RED);
                letterState = textState;
                break;
            case EXTRA:
                this.text.setFill(Color.ORANGE);
                letterState = textState;
                break;
            case MISSED:
                this.text.setFill(Color.BLACK);
                letterState = textState;
                break;
            default:
                this.text.setFill(Color.GRAY);
                letterState = LetterState.DEFAULT;
                break;
        }
    }

    public Text getTextComponent() {
        return this.text;
    }
    public boolean isOriginalParagraph() {
        return originalParagraph;
    }

    public void animate() {
        this.pathTransition.play();
    }

    public void setText(String newText) {
        this.text.setText(newText);
    }

}
