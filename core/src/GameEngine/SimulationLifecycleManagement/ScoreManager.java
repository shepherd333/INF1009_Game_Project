package GameEngine.SimulationLifecycleManagement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.Collections;

// ScoreManager manages the scoring system in the game.
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
    public void render(SpriteBatch batch, Viewport viewport) {
        batch.begin();
        String scoreText = "Score: " + currentScore;
        GlyphLayout layout = new GlyphLayout(font, scoreText);
        float x = viewport.getWorldWidth() - layout.width - 10; // Example x coordinate
        float y = viewport.getWorldHeight() - layout.height - 70; // Example y coordinate
        font.draw(batch, scoreText, x, y); // Render the score text
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