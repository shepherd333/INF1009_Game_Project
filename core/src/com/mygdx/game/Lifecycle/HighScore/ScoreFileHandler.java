//package com.mygdx.game.Lifecycle.HighScore;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.files.FileHandle;
//
//import java.util.ArrayList;
//
//public class ScoreFileHandler {
//    private static final String SCORE_FILE = "highscores.txt";
//
//    public ArrayList<Integer> loadScores() {
//        FileHandle file = Gdx.files.local(SCORE_FILE);
//        ArrayList<Integer> highScores = new ArrayList<>();
//        if (file.exists()) {
//            String scoreString = file.readString();
//            String[] scores = scoreString.split("\n");
//            for (String score : scores) {
//                try {
//                    highScores.add(Integer.parseInt(score.trim()));
//                } catch (NumberFormatException e) {
//                    Gdx.app.error("ScoreFileHandler", "Error parsing score from file", e);
//                }
//            }
//        }
//        return highScores;
//    }
//
//    public void saveScores(ArrayList<Integer> highScores) {
//        FileHandle file = Gdx.files.local(SCORE_FILE);
//        try {
//            StringBuilder sb = new StringBuilder();
//            for (Integer score : highScores) {
//                sb.append(score).append("\n");
//            }
//            file.writeString(sb.toString(), false);
//        } catch (Exception e) {
//            Gdx.app.error("ScoreFileHandler", "Error writing high scores", e);
//        }
//    }
//}
