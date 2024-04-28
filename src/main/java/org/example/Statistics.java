package org.example;

import javafx.scene.chart.XYChart;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Statistics {
    public static int correctLetters = 0;
    public static int incorrectLetters = 0;
    public static int extraLetters = 0;
    public static int missedLetters = 0;
    // to calculate current WPM
    public static List<Double> correctLettersTimes = new ArrayList<Double>();

    public static void resetStats() {
        correctLetters = incorrectLetters = extraLetters = missedLetters = 0;
        correctLettersTimes.clear();
    }

    public static void saveStatsToFile() {
        double previousFinishTime = 0;

        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd---HH-mm-ss").format(Calendar.getInstance().getTime());
            FileWriter myWriter = new FileWriter(timeStamp + ".txt");
            for(Word word : Paragraph.allWords) {
                if(word.finishedSecond == -1) {
                    continue;
                }
                double value = 60/(word.finishedSecond-previousFinishTime);
                previousFinishTime = word.finishedSecond;
                myWriter.write(word.text + " -> " +  Math.round(value) + "wpm\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static XYChart.Series getCurrentWPMSeries() {
        XYChart.Series currentWpmSeries = new XYChart.Series();
        currentWpmSeries.setName("Current WPM");
        currentWpmSeries.getData().add(new XYChart.Data(0,  0));

        int lettersCompleted = 0;
        for(double second = 1, previousSecond = 0; second <= GameManager.getGameTime(); second++) {
            for(double letterTime : correctLettersTimes) {
                if(letterTime <= second && letterTime > previousSecond) {
                    lettersCompleted++;
                }
            }
            if(lettersCompleted < 1) {
                currentWpmSeries.getData().add(new XYChart.Data(second,  0));
                continue;
            }
            double value = ((double)(lettersCompleted/5)*60)/second;
            currentWpmSeries.getData().add(new XYChart.Data(second,  value));

            previousSecond = second;
        }

        return currentWpmSeries;
    }

    public static XYChart.Series getAverageWPMSeries() {
        XYChart.Series averageWpmSeries = new XYChart.Series();
        averageWpmSeries.setName("Average WPM");
        averageWpmSeries.getData().add(new XYChart.Data(0,  0));

        int wordsCompleted = 0;
        for(double second = 1, previousSecond = 0; second <= GameManager.getGameTime(); second++) {
            for(Word word : Paragraph.allWords) {
                if(word.finishedSecond == -1) {
                    continue;
                }
                if(word.completed && word.finishedSecond <= second && word.finishedSecond > previousSecond) {
                    wordsCompleted++;
                }
            }
            if(wordsCompleted < 1) {
                averageWpmSeries.getData().add(new XYChart.Data(second,  0));
                continue;
            }
            double value = (double)(wordsCompleted*60)/second;
            averageWpmSeries.getData().add(new XYChart.Data(second,  value));

            previousSecond = second;
        }

        return averageWpmSeries;
    }

    public static double getAverageWPM() {
        XYChart.Series averageWpmSeries = new XYChart.Series();
        averageWpmSeries.setName("Average WPM");
        averageWpmSeries.getData().add(new XYChart.Data(0,  0));

        double averageWPM = 0;
        int wordsCompleted = 0;
        for(double second = 1, previousSecond = 0; second <= GameManager.getGameTime(); second++) {
            for(Word word : Paragraph.allWords) {
                if(word.finishedSecond == -1) {
                    continue;
                }
                if(word.completed && word.finishedSecond <= second && word.finishedSecond > previousSecond) {
                    wordsCompleted++;
                }
            }
            if(wordsCompleted < 1) {
                averageWpmSeries.getData().add(new XYChart.Data(second,  0));
                continue;
            }
            double value = (double)(wordsCompleted*60)/second;
            averageWPM = value;

            previousSecond = second;
        }

        return averageWPM;
    }

    public static double getMaxWPMValue() {
        double maxValue = 0;
        double previousFinishTime = 0;
        for(Word word : Paragraph.allWords) {
            if(word.finishedSecond == -1) {
                continue;
            }
            double value = 60/(word.finishedSecond-previousFinishTime);
            maxValue = Math.max(value, maxValue);
            previousFinishTime = word.finishedSecond;
        }
        return maxValue;
    }

    public static String getAccuracyText() {
        if(correctLetters < 1) {
            return "0%";
        }
        else {
            return String.format("%.0f%%",((double)(Statistics.correctLetters)/(double)(Statistics.correctLetters+Statistics.missedLetters+Statistics.incorrectLetters+Statistics.extraLetters))*100);
        }
    }
}

