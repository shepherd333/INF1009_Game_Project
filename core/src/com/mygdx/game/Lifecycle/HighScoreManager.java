package com.mygdx.game.Lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// Manages high scores throughout the game lifecycle, including loading, saving, and displaying scores.
public class HighScoreManager {
    private ArrayList<Integer> highScores; // Stores the list of high scores.
    private static final int MAX_SCORES = 10; // Limits the number of stored scores.
    private int currentScore = 500; // Initializes the current score.
    private BitmapFont font; // Font for rendering scores.
    private SpriteBatch batch; // Batch for drawing text.
    private HighScoreManager highScoreManager; // Redundant self-reference, not required.
    private static HighScoreManager instance; // Singleton instance of the HighScoreManager.
    private Viewport viewport;

    // Constructor initializes the highScores list and loads existing scores.
    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadScores(); // Load scores from a file or other persistent storage.
    }

    // Singleton pattern to ensure only one instance of HighScoreManager exists.
    public static HighScoreManager getInstance() {
        if (instance == null) {
            instance = new HighScoreManager();
        }
        return instance;
    }

    // Returns the highest score in the list or 0 if no scores are present.
    public int getHighestScore() {
        if (!highScores.isEmpty()) {
            return highScores.get(0); // Assumes list is sorted in descending order.
        }
        return 0;
    }

    // Adds points to the current score.
    public void addToCurrentScore(int points) {
        currentScore += points;
        Gdx.app.log("HighScoreManager", "Added points: " + points + ", New score: " + currentScore);
    }

    // Resets the current score to 0.
    public void resetCurrentScore() {
        currentScore = 0;
    }

    // Retrieves the current score.
    public int getCurrentScore() {
        return currentScore;
    }

    // Formats the highest score as a string with leading zeros.
    public String getHighestScoreFormatted() {
        return String.format("%06d", getHighestScore());
    }

    // Formats the current score as a string with leading zeros.
    public String getCurrentScoreFormatted(){
        return String.format("%06d", currentScore);
    }

    // Adds a score to the list, sorts the list, and removes the lowest score if necessary.
    public void addScore(int score) {
        highScores.add(score);
        Collections.sort(highScores, Collections.reverseOrder()); // Sort scores in descending order.
        if (highScores.size() > MAX_SCORES) {
            highScores.remove(highScores.size() - 1); // Remove the lowest score.
        }
        saveScores(); // Save the updated list of scores.
    }

    // Loads scores from a file.
    private void loadScores() {
        FileHandle file = Gdx.files.local("highscores.txt");
        if (file.exists()) {
            String scoreString = file.readString();
            String[] scores = scoreString.split("\n");
            highScores.clear();
            for (String score : scores) {
                try {
                    highScores.add(Integer.parseInt(score.trim()));
                } catch (NumberFormatException e) {
                    Gdx.app.error("HighScoreManager", "Error parsing score from file", e);
                }
            }
        }
    }

    // Saves the current score to a file.
    public void saveScores() {
        FileHandle file = Gdx.files.local("highscores.txt");
        try {
            file.writeString(String.valueOf(currentScore), false); // Overwrites the file.
        } catch (Exception e) {
            Gdx.app.error("HighScoreManager", "Error writing high score", e);
        }
    }

    // Adds filler scores for testing or initial setup.
    public void addFillerScores() {
        Random rand = new Random();
        for (int i = 0; i < 1; i++) {
            int score = 1000 + rand.nextInt(9000);
            highScores.add(score);
        }
        Collections.sort(highScores, Collections.reverseOrder());
    }

    // Initializes resources like SpriteBatch and BitmapFont for rendering.
    public void create(){
        highScoreManager = new HighScoreManager();
        highScoreManager.addFillerScores();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1);
    }

    // Renders the highest score.
    public void render(){
    }

    // Disposes of resources to avoid memory leaks.
    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}
