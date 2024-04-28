package org.example;

public class Word {
    public int start_index;
    public int end_index;

    public String text;

    public double finishedSecond = -1; //-1 means unfinished
    public boolean completed = false;

    Word(int start_index, int end_index, String text) {
        this.start_index = start_index;
        this.end_index = end_index;
        this.text = text;
    }

    public void finish(boolean correct) {
        finishedSecond = GameManager.getGameTime() - GameManager.countdownSeconds;
        completed = correct;
    }
}
