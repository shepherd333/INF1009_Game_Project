package com.mygdx.game.Lifecycle.ScoreSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreManager {
    private ArrayList<Integer> scores;
    private static final int MAX_SCORES = 10;
    private int currentScore = 0;
    private static ScoreManager instance;
    private BitmapFont font;

    private ScoreManager() {
        scores = new ArrayList<>();
        font = new BitmapFont(); // Optionally use a .fnt file for a custom font
        font.getData().setScale(1.5f); // Scale to fit your game's aesthetic
    }

    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public void create() {
        scores = new ArrayList<>();
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void addToCurrentScore(int points) {
        currentScore += points;
        Gdx.app.log("ScoreManager", "Added score, new score: " + currentScore);
    }

    public void subtractFromCurrentScore(int points) {
        currentScore -= points;
        Gdx.app.log("ScoreManager", "Subtracted score, new score: " + currentScore);
    }

    public void resetCurrentScore() {
        currentScore = 0;
    }

    public void addScore(int score) {
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder());
        if (scores.size() > MAX_SCORES) {
            scores.remove(scores.size() - 1);
        }

        saveScores(score);
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public int getTopScore() {
        if (!scores.isEmpty()) {
            return scores.get(0);
        } else {
            return 0;
        }
    }

    public void saveScores(int score) {
        FileHandle file = Gdx.files.local("scores.txt");
        try {
            file.writeString(String.valueOf(currentScore) + "\n", true); // Append to the file.
        } catch (Exception e) {
            Gdx.app.error("ScoreManager", "Error writing score", e);
        }
    }




    public void loadScores() {
        FileHandle file = Gdx.files.internal("scores.txt");
        if (file.exists()) {
            String scoreContents = file.readString();
            Gdx.app.log("ScoreManager", "File contents: \n" + scoreContents);
            String[] scoreLines = scoreContents.split("\\r?\\n");
            scores.clear();
            for (String line : scoreLines) {
                try {
                    int score = Integer.parseInt(line.trim());
                    scores.add(score);
                    Gdx.app.log("ScoreManager", "Loaded score: " + score);
                } catch (NumberFormatException e) {
                    Gdx.app.error("ScoreManager", "Error parsing score: " + line, e);
                }
            }
            // Sort scores in descending order
            Collections.sort(scores, Collections.reverseOrder());
        } else {
            Gdx.app.log("ScoreManager", "scores.txt file not found.");
        }
    }




    // Method to get formatted high scores
    public ArrayList<String> getFormattedScores() {
        ArrayList<String> formattedScores = new ArrayList<>();
        int rank = 1;
        for (Integer score : scores) {
            formattedScores.add(rank + ". " + score);
            rank++;
        }
        return formattedScores;
    }

    public void renderTopScore(SpriteBatch batch, BitmapFont font, Viewport viewport) {
        if (!scores.isEmpty()) {
            font.getData().setScale(2f);
            String scoreDisplay = "Top Score: " + scores.get(0);
            GlyphLayout scoreLayout = new GlyphLayout(font, scoreDisplay);
            float scoreX = viewport.getWorldWidth() - scoreLayout.width - 250;
            float scoreY = viewport.getWorldHeight() - scoreLayout.height - 250;
            batch.begin();
            font.draw(batch, scoreDisplay, scoreX, scoreY);
            batch.end();
        }
    }

    public void render(SpriteBatch batch, Viewport viewport) {
        batch.begin();
        String scoreText = "Score: " + currentScore;
        GlyphLayout layout = new GlyphLayout(font, scoreText);
        float x = viewport.getWorldWidth() - layout.width - 10; // Example x coordinate
        float y = viewport.getWorldHeight() - layout.height - 70; // Example y coordinate
        font.draw(batch, scoreText, x, y);
        batch.end();
    }

    public void dispose() {
        if (font != null) font.dispose();
    }
}
