package GameEngine.SimulationLifecycleManagement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.Collections;

/**
 * ScoreManager is responsible for managing the scoring system in the game.
 * This includes:
 * - Maintaining the current score and a list of high scores.
 * - Adding and subtracting points from the current score.
 * - Resetting the current score to zero.
 * - Saving and loading scores to and from a file.
 * - Rendering the current score on the screen.
 * - Disposing of resources when they are no longer needed.
 *
 * ScoreManager follows the Singleton design pattern to ensure there is only one instance of ScoreManager in the game.
 */
public class ScoreManager {
    private ArrayList<Integer> scores; // List to store high scores
    private static final int MAX_SCORES = 10; // Maximum number of high scores to keep
    private int currentScore = 0; // Current score
    private static ScoreManager instance; // Singleton instance
    private BitmapFont font; // Font for rendering text
    private int currentLevel = 1; // Default to level 1

    // Private constructor to prevent direct instantiation
    private ScoreManager() {
        scores = new ArrayList<>();
        font = new BitmapFont(); // Optionally use a .fnt file for a custom font
        font.getData().setScale(1.5f); // Scale to fit your game's aesthetic
    }

    // Singleton getInstance method
    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    // Getter for current score
    public int getCurrentScore() {
        return currentScore;
    }

    // Method to add points to the current score
    public void addToCurrentScore(int points) {
        currentScore += points;
        Gdx.app.log("ScoreManager", "Added score, new score: " + currentScore);
    }

    // Method to subtract points from the current score
    public void subtractFromCurrentScore(int points) {
        currentScore -= points;
        Gdx.app.log("ScoreManager", "Subtracted score, new score: " + currentScore);
    }

    // Method to reset the current score to zero
    public void resetCurrentScore() {
        currentScore = 0;
    }

    // Method to get the file path for storing scores
    private String getScoresFilePath() {
        // Adjust the file name based on the current level
        String filePath = "scores" + currentLevel + ".txt";
        Gdx.app.log("ScoreManager", "getScoresFilePath: Retrieving file path for level " + currentLevel + " - " + filePath);
        return filePath;
    }

    // Method to set the current level
    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    // Method to add a score to the list of high scores
    public void addScore(int score) {
        scores.add(score);
        Collections.sort(scores, Collections.reverseOrder()); // Sort scores in descending order
        if (scores.size() > MAX_SCORES) {
            scores.remove(scores.size() - 1); // Remove excess scores
        }
        saveScores(); // Save scores to file
    }

    // Method to save scores to file
    public void saveScores() {
        FileHandle file = Gdx.files.local(getScoresFilePath());
        try {
            // Append the current score followed by a newline character
            file.writeString("\n" + String.valueOf(currentScore), true);
        } catch (Exception e) {
            Gdx.app.error("ScoreManager", "Error writing scores", e);
        }
    }

    // Method to load scores from file
    public void loadScores() {
        FileHandle file = Gdx.files.local(getScoresFilePath());
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
            // Sort scores in descending order and keep only the top 5
            Collections.sort(scores, Collections.reverseOrder());
            while (scores.size() > 5) {
                scores.remove(scores.size() - 1);
            }
        } else {
            Gdx.app.log("ScoreManager", "Scores file not found for level " + currentLevel);
        }
    }

    // Method to get formatted high scores
    public ArrayList<String> getFormattedScores() {
        // Load scores if not already loaded
        if (scores.isEmpty()) {
            loadScores();
        }

        ArrayList<String> formattedScores = new ArrayList<>();
        for (Integer score : scores) {
            formattedScores.add(" " + score); // Add each score to the list
        }
        return formattedScores;
    }

    // Method to render the current score
    public void render(SpriteBatch batch, Viewport viewport, ShapeRenderer shapeRenderer) {
        String scoreText = "Score: " + currentScore;
        GlyphLayout layout = new GlyphLayout(font, scoreText);
        float padding = 10f; // Adjust padding as needed
        float x = viewport.getWorldWidth() - layout.width - 250; // X coordinate for the text
        float y = viewport.getWorldHeight() - layout.height - 10; // Y coordinate for the text

        // Calculate background size
        float backgroundWidth = layout.width + (padding * 2);
        float backgroundHeight = layout.height + (padding * 2);
        float backgroundX = x - padding; // Adjust X to include padding
        float backgroundY = y - padding; // Adjust Y to include padding

        // Start ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK); // Set background color
        shapeRenderer.rect(backgroundX, backgroundY, backgroundWidth, backgroundHeight);
        shapeRenderer.end();

        // Now render the text over the background
        batch.begin();
        font.draw(batch, scoreText, x, y + layout.height); // Adjust y to draw text correctly
        batch.end();
    }

    public BitmapFont getFont() {
        return font;
    }

    // Method to dispose of resources
    public void dispose() {
        if (font != null) {
            font.dispose(); // Dispose of the BitmapFont
        }
    }
}