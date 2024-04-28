package org.example;

import javafx.event.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;

public class TypingHandler {
    private int lettersIndex = 0;
    private int minIndex = 0; // after space press

    private int overflowCount = 0;

    private int endWordIndex = 0;
    private int wordIndex = 0;
    private int wordBadLetterIndex = -1; // index of first bad letter in the world (if -1 word is correct)
    private Paragraph paragraph;
    private TextFlow textFlow;

    TypingHandler(Paragraph paragraph, TextFlow textFlow) {
        this.paragraph = paragraph;
        this.textFlow = textFlow;
        this.endWordIndex = this.paragraph.getParagraphWords().get(0).end_index;
    }

    public void reset() {
        lettersIndex = 0;
        minIndex = 0;
        overflowCount = 0;
        wordIndex = 0;
        this.paragraph.generateNewParagraph();
        this.paragraph.setTextFlow(this.textFlow);
        endWordIndex = this.paragraph.getParagraphWords().get(0).end_index;
    }

    private void finishWord() {
        if(lettersIndex == endWordIndex && wordBadLetterIndex == -1) {
            this.paragraph.getParagraphWords().get(wordIndex).finish(true);
            return;
        }
        this.paragraph.getParagraphWords().get(wordIndex).finish(false);
    }

    private void incrementLetterIndex() {
        lettersIndex = lettersIndex + 1 <= this.paragraph.getTextLetters().size() ? lettersIndex + 1 : lettersIndex;
    }

    private void handleStats(LetterState fromState, LetterState toState) {
        switch (fromState) {
            case CORRECT:
                Statistics.correctLetters--;
                break;
            case INCORRECT:
                Statistics.incorrectLetters--;
                break;
            case EXTRA:
                Statistics.extraLetters--;
                break;
            case MISSED:
                Statistics.missedLetters--;
                break;
        }
        switch (toState) {
            case CORRECT:
                Statistics.correctLetters++;
                break;
            case INCORRECT:
                Statistics.incorrectLetters++;
                break;
            case EXTRA:
                Statistics.extraLetters++;
                break;
            case MISSED:
                Statistics.missedLetters++;
                break;
        }
    }

    private void handleDelete() {
        // Normal letters
        if(lettersIndex <= endWordIndex) {
            lettersIndex--;
            handleStats(this.paragraph.getTextLetters().get(lettersIndex).getLetterState(), LetterState.DEFAULT);
            this.paragraph.getTextLetters().get(lettersIndex).setTextState(LetterState.DEFAULT);

        }
        // Overflow
        if(lettersIndex == this.paragraph.getTextLetters().size() || lettersIndex>endWordIndex) {
            lettersIndex--;
            overflowCount--;
            this.paragraph.getTextLetters().remove(lettersIndex);
            this.textFlow.getChildren().remove(lettersIndex);
        }
    }

    private void handleSpace() {
        finishWord();

        if(lettersIndex >= this.paragraph.getTextLetters().size()) {
            reset();
            return;
        }

        // Move letters index to next word
        while(lettersIndex<=endWordIndex) {
            handleStats(this.paragraph.getTextLetters().get(lettersIndex).getLetterState(), LetterState.MISSED);
            this.paragraph.getTextLetters().get(lettersIndex).setTextState(LetterState.MISSED);
            incrementLetterIndex();
            if(lettersIndex>=this.paragraph.getTextLetters().size()) {
                break;
            }
        }

        if(lettersIndex >= this.paragraph.getTextLetters().size()) {
            reset();
            return;
        }

        wordIndex++;
        endWordIndex = this.paragraph.getParagraphWords().get(wordIndex).end_index + overflowCount;

        minIndex = lettersIndex;
    }

    private void handleNewLetter(String newLetterStr) {
        if (newLetterStr.length() <= 0) {
            return;
        }
        // correct letter
        if(lettersIndex < endWordIndex && (this.paragraph.getTextLetters().get(lettersIndex).getTextComponent().getText()).equals(newLetterStr)) {
            handleStats(this.paragraph.getTextLetters().get(lettersIndex).getLetterState(), LetterState.CORRECT);
            this.paragraph.getTextLetters().get(lettersIndex).setTextState(LetterState.CORRECT);
            Statistics.correctLettersTimes.add(GameManager.getGameTime() - GameManager.countdownSeconds);
            incrementLetterIndex();
        }
        else if (lettersIndex < endWordIndex && !(this.paragraph.getTextLetters().get(lettersIndex).getTextComponent().getText()).equals(newLetterStr)) {
            wordBadLetterIndex = Math.min(lettersIndex, wordBadLetterIndex);
            handleStats(this.paragraph.getTextLetters().get(lettersIndex).getLetterState(), LetterState.INCORRECT);
            this.paragraph.getTextLetters().get(lettersIndex).setTextState(LetterState.INCORRECT);
            incrementLetterIndex();
        }
        // overflow
        else if (lettersIndex >= endWordIndex) {
            this.paragraph.getTextLetters().add(lettersIndex, new TextLetter(newLetterStr.charAt(0), false));
            this.textFlow.getChildren().add(lettersIndex, this.paragraph.getTextLetters().get(lettersIndex).getTextComponent());
            handleStats(this.paragraph.getTextLetters().get(lettersIndex).getLetterState(), LetterState.EXTRA);
            this.paragraph.getTextLetters().get(lettersIndex).setTextState(LetterState.EXTRA);
            overflowCount++;
            incrementLetterIndex();
        }
    }

    public EventHandler<KeyEvent> getTypingHandler() {
        return event -> {
            if(GameManager.gameStop) {
                return;
            }
            if(event.getCode() == KeyCode.BACK_SPACE && lettersIndex>minIndex) {
                handleDelete();
                event.consume();
                return;
            }
            else if (event.getCode() == KeyCode.SPACE) {
                handleSpace();
                event.consume();
                return;
            }
            else if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER) {
                event.consume();
                return;
            }

            String newLetterStr = event.getText();
            handleNewLetter(newLetterStr);

            event.consume();
        };
    }
}
