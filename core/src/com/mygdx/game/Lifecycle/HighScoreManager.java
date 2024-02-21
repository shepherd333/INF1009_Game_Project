package com.mygdx.game.Lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HighScoreManager {
    private ArrayList<Integer> highScores;
    private static final int MAX_SCORES = 10; // Limit the number of stored scores if desired
    private int currentScore = 0;
    private BitmapFont font;
    private SpriteBatch batch;
    private HighScoreManager highScoreManager;

    public HighScoreManager() {
        highScores = new ArrayList<Integer>();
        loadScores(); // Load scores from persistent storage
    }

    public int getHighestScore() {
        if (!highScores.isEmpty()) {
            return highScores.get(0); // Assuming the list is sorted in descending order
        }
        return 0; // Default value if no scores are present
    }

    public void addToCurrentScore(int points) {
        currentScore += points;
        // Optionally check for high score updates here
    }
    public void resetCurrentScore() {
        currentScore = 0;
    }

    //highScoreManager.resetCurrentScore();

    public String getCurrentScoreFormatted() {
        return String.format("%06d", getHighestScore());
    }

    //highScoreManager.addToCurrentScore(pointsEarned);
    //Update the Score During Gameplay
    //Wherever in your game logic points are earned (e.g., defeating an enemy, completing a level),
    // call addToCurrentScore with the points earned. This will keep the current score updated.

    public void addScore(int score) {
        highScores.add(score);
        Collections.sort(highScores, Collections.reverseOrder()); // Sort scores in descending order
        if (highScores.size() > MAX_SCORES) {
            highScores.remove(highScores.size() - 1); // Remove the lowest score if exceeding limit
        }
        saveScores(); // Save scores to persistent storage
    }

    private void loadScores() {
        // Implement loading logic here (e.g., from a file or preferences)
        FileHandle file = Gdx.files.internal("highscores.txt");
        if (file.exists()) {
            String scoreString = file.readString();
            String[] scores = scoreString.split("\n");
            highScores.clear(); // Clear existing scores
            for (String score : scores) {
                try {
                    highScores.add(Integer.parseInt(score.trim())); // Add each score to the list
                } catch (NumberFormatException e) {
                    // Handle case where the line is not a valid integer
                    Gdx.app.error("HighScoreManager", "Error parsing score from file", e);
                }
            }
        }
    }

    private void saveScores() {
        // Implement saving logic here
        FileHandle file = Gdx.files.internal("highscores.txt");
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer score : highScores) {
            stringBuilder.append(score.toString());
            stringBuilder.append("\n"); // New line for each score
        }
        file.writeString(stringBuilder.toString(), false); // false to overwrite the file
    }
    // Method to add filler scores
    public void addFillerScores() {
        Random rand = new Random();
        for (int i = 0; i < MAX_SCORES; i++) {
            // Generate a random score (for example, between 1000 and 10000)
            int score = 1000 + rand.nextInt(9000);
            highScores.add(score);
        }
        Collections.sort(highScores, Collections.reverseOrder()); // Sort scores
    }

    public void create(){
        highScoreManager = new HighScoreManager();
        highScoreManager.addFillerScores(); // Optionally populate with filler scores
        batch = new SpriteBatch();

        font = new BitmapFont(); // Default font, consider using a custom font for better visuals
        font.setColor(Color.WHITE); // Set the font color
        font.getData().setScale(1); // Set the scale if needed
    }
    public void render(){
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        // Draw high score
        batch.begin();
        font.getData().setScale(1.5f);
        //final float padding = 200; // Padding from the top and right screen edges

        GlyphLayout layout = new GlyphLayout(); // Consider making this a field to avoid re-allocating each frame

        float width = layout.width; // Use this for calculating xPosition
        float xPosition = Gdx.graphics.getWidth() - width - 200;
        float yPosition = Gdx.graphics.getHeight() - layout.height - 10;

        String scoreDisplay = "High Score: " + highScoreManager.getCurrentScoreFormatted();
        layout.setText(font, scoreDisplay);
        font.draw(batch, scoreDisplay, xPosition, yPosition);
        batch.end();
    }

    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}
