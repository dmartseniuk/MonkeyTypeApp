package org.example;

import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Paragraph {
    // All words used since the gameStart
    public static List<Word> allWords = new ArrayList<Word>();
    private String activeParagraph = "";
    private final List<Word> paragraphWords = new ArrayList<Word>();
    private final List<TextLetter> textLetters = new ArrayList<TextLetter>();

    LanguageService languageService;

    Paragraph(LanguageService languageService) {
        this.languageService = languageService;
    }

    public List<TextLetter> getTextLetters() {
        return textLetters;
    }

    public List<Word> getParagraphWords() {
        return paragraphWords;
    }

    public void generateNewParagraph() {
        paragraphWords.clear();
        textLetters.clear();
        activeParagraph = "";
        try {
            String[] words = languageService.generateParagraph().toArray(new String[0]);
            int previousEndIndex = 0;
            for(String word : words) {
                Word newWord = new Word(previousEndIndex, previousEndIndex + word.length(), word);
                previousEndIndex = previousEndIndex + word.length() + 1; // + 1 is for space sign
                paragraphWords.add(newWord);
                allWords.add(newWord);
                activeParagraph = activeParagraph + word + " ";
            }
            activeParagraph = activeParagraph.substring(0,activeParagraph.length() -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTextFlow(TextFlow flow) {
        flow.getChildren().clear();
        for (char letter : activeParagraph.toCharArray()) {
            TextLetter newLetterTxt = new TextLetter(letter, true);
            textLetters.add(newLetterTxt);
            flow.getChildren().add(newLetterTxt.getTextComponent());
        }
    }

    public static void resetStats() {
        allWords = new ArrayList<Word>();
    }
}
