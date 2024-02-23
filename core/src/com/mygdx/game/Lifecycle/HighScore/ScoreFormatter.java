package com.mygdx.game.Lifecycle.HighScore;

public class ScoreFormatter {
    public String formatScore(int score){
        return String.format("%06d", score);
    }
}
