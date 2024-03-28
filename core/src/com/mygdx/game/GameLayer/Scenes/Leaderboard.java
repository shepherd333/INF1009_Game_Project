package com.mygdx.game.GameLayer.Scenes;

import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import GameEngine.SimulationLifecycleManagement.ScoreSystem.ScoreManager;

public class Leaderboard extends BaseScene {
    private BitmapFont font;

    public Leaderboard(SceneManager sceneManager) {
        super(sceneManager);
        this.font = new BitmapFont(); // Or use a custom font
        ScoreManager.getInstance().loadScores(); // Load scores at scene initialization
    }

    @Override
    protected String getBackgroundTexturePath() {
        return "Leaderboard.jpg"; // Set the path to the background texture for the leaderboard scene
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass

        int buttonWidth = 100;
        int buttonHeight = 25;
        int buttonSpacing = 5;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int totalHeight = (buttonHeight + buttonSpacing) * 5;

        int verticalOffset = (screenHeight - totalHeight) / 2;

        addButton("Next", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the MainMenuScene
                getSceneManager().set(new EndMenu(getSceneManager()));
            }
        }, 1085, 745, buttonWidth, buttonHeight);
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


        // Retrieve and render scores
        for (String entry : ScoreManager.getInstance().getFormattedScores()) {
            font.draw(batch, entry, 530, startY); // Adjust X position as needed
            startY -= 107; // Adjust for the next entry
        }

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose(); // Call the dispose method of the superclass
        // Dispose any additional resources specific to Leaderboard
        if (font != null) font.dispose();
    }
}
