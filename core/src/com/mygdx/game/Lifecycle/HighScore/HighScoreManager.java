//package com.mygdx.game.Lifecycle.HighScore;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.files.FileHandle;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.GlyphLayout;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.viewport.StretchViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//
//import javax.swing.text.View;
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class HighScoreManager {
//    private ArrayList<Integer> highScores;
//    private static final int MAX_SCORES = 10;
//    private int currentScore = 500;
//    private static HighScoreManager instance;
//    private ScoreRenderer scoreRenderer;
//    private ScoreFormatter scoreFormatter;
//
//
//    private HighScoreManager() {
//        highScores = new ArrayList<>();
//        scoreRenderer = new ScoreRenderer();
//        scoreFormatter = new ScoreFormatter();
//    }
//
//    public static HighScoreManager getInstance() {
//        if (instance == null) {
//            instance = new HighScoreManager();
//        }
//        return instance;
//    }
//
//    public void create() {
//        highScores = new ArrayList<>();
//    }
//
//    public int getCurrentScore() {
//        return currentScore;
//    }
//
//    public void addToCurrentScore(int points) {
//        currentScore += points;
//    }
//
//    public void minusToCurrentScore(int points) {
//        currentScore -= 20;
//    }
//
//    public void resetCurrentScore() {
//        currentScore = 0;
//    }
//
//    public void addScore(int score) {
//        highScores.add(score);
//        Collections.sort(highScores, Collections.reverseOrder());
//        if (highScores.size() > MAX_SCORES) {
//            highScores.remove(highScores.size() - 1);
//        }
//    }
//
//    public ArrayList<Integer> getHighScores() {
//        return highScores;
//    }
//
//    public int getHighestScore() {
//        if (!highScores.isEmpty()) {
//            return highScores.get(0);
//        } else {
//            return 0;
//        }
//    }
//
//    public void saveScores() {
//        FileHandle file = Gdx.files.local("highscores.txt");
//        try {
//            file.writeString(String.valueOf(currentScore), false); // Overwrites the file.
//        } catch (Exception e) {
//            Gdx.app.error("HighScoreManager", "Error writing high score", e);
//        }
//    }
//
//    public void renderHighestScore(SpriteBatch batch, BitmapFont font, Viewport viewport) {
//        if (!highScores.isEmpty()) {
//            font.getData().setScale(2f);
//            String highScoreDisplay = "High Score: " + scoreFormatter.formatScore(highScores.get(0));
//            GlyphLayout highScoreLayout = new GlyphLayout(font, highScoreDisplay);
//            float highScoreX = viewport.getWorldWidth() - highScoreLayout.width - 250;
//            float highScoreY = viewport.getWorldHeight() - highScoreLayout.height - 250;
//            scoreRenderer.render(highScoreDisplay, batch, highScoreX, highScoreY);
//        }
//    }
//
//    public void dispose() {
//        if (scoreRenderer != null) {
//            scoreRenderer.dispose();
//        }
//    }
//}