package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import GameEngine.SimulationLifecycleManagement.ScoreManager;
import GameEngine.InputControl.UIButtonManager;

// Constructor for the Leaderboard scene
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
    public void update(float deltaTime) {
        // Update logic for leaderboard here
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
