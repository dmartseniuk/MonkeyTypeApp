package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LanguageService {
    private String filesLocation;

    public LanguageService(String filesLocation) {
        this.filesLocation = filesLocation;
    }

    public String[] getLanguagesList() {
        String[] languageFiles;

        File dictionary = new File("./dictionary");

        languageFiles = dictionary.list();

        for (int i = 0; i < languageFiles.length; i++) {
            languageFiles[i] = languageFiles[i].substring(0, 1).toUpperCase() + languageFiles[i].substring(1, languageFiles[i].length() - 4);
        }

        return languageFiles;
    }

    public List<String> generateParagraph() throws IOException {
        String pickedLanguage = GameManager.getLanguage();
        String languageFilePath = "./dictionary/" + pickedLanguage.substring(0,1).toLowerCase() + pickedLanguage.substring(1, pickedLanguage.length()) + ".txt";

        List<String> randomLines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(languageFilePath));
        long totalLines = reader.lines().count();
        reader.close();

        // Generate n random line numbers
        Random random = new Random();
        List<Integer> randomLineNumbers = new ArrayList<>();
        while (randomLineNumbers.size() < GameManager.nParagraphWords) {
            int lineNumber = random.nextInt((int) totalLines);
            randomLineNumbers.add(lineNumber);
        }

        // Read the lines corresponding to the random line numbers
        for (int i = 0; i<randomLineNumbers.size() || i < GameManager.nParagraphWords;) {
            reader = new BufferedReader(new FileReader(languageFilePath));
            String line = reader.lines().skip(randomLineNumbers.get(i)).findFirst().get();
            String[] words = line.split(" ");
            for(String word : words) {
                randomLines.add(word);
                i++;
                if(i >= GameManager.nParagraphWords) {
                    break;
                }
            }
            reader.close();
        }

        return randomLines;
    }
}
