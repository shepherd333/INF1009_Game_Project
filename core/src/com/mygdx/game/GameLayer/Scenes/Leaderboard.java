package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import GameEngine.SimulationLifecycleManagement.ScoreManager;

/**
 * Leaderboard is a class that represents the leaderboard scene in a game.
 * This includes:
 * - Holding references to a BitmapFont and a UIButtonManager.
 * - Providing a constructor to initialize the Leaderboard with a SceneManager. In this constructor, it calls the superclass constructor with the SceneManager, initializes the BitmapFont, and loads the scores at scene initialization.
 * - Overriding a method to specify the background texture path for the Leaderboard scene. In this method, it returns the path to the background texture for the Leaderboard scene.
 * - Overriding a method to initialize the Leaderboard. In this method, it calls the initialize method of the superclass, initializes the UIButtonManager, and sets up the UI buttons for the Leaderboard scene.
 * - Overriding a method to render the Leaderboard. In this method, it calls the render method of the superclass, begins a new batch for rendering, adjusts the scale of the font as needed, sets the starting Y position for the scores, draws each score entry on the batch, and ends the batch.
 * - Overriding a method to dispose of resources when the Leaderboard scene is closed. In this method, it calls the dispose method of the superclass and disposes of the BitmapFont if it is not null.
 */
public class Leaderboard extends BaseScene {
    private BitmapFont font;
    private UIButtonManager uiButtonManager;

    public Leaderboard(SceneManager sceneManager) {
        super(sceneManager);
        this.font = new BitmapFont(); // Or use a custom font
        ScoreManager.getInstance().loadScores(); // Load scores at scene initialization

    }
    // Method to specify the background texture path for the Leaderboard scene
    @Override
    protected String getBackgroundTexturePath() {
        return "Leaderboard.jpg"; // Set the path to the background texture for the leaderboard scene
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass
        uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupLeaderboard();
    }

    @Override
    public void render() {
        super.render();
        batch.begin();
        font.getData().setScale(5.0f); // Adjust the scale as needed
        int startY = Gdx.graphics.getHeight() - 222; // Starting Y position for the scores
        for (String entry : ScoreManager.getInstance().getFormattedScores()) {
            font.draw(batch, entry, 530, startY); // Adjust X position as needed
            startY -= 107; // Adjust for the next entry
        }
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose(); // Call the dispose method of the superclass
        if (font != null) font.dispose();
    }
}
