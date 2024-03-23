package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Leaderboard extends BaseScene {

    public Leaderboard(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected String getBackgroundTexturePath() {
        return "LeaderBoard.png"; // Set the path to the background texture for the leaderboard scene
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

        addButton("Home", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the MainMenuScene
                getSceneManager().set(new MainMenu(getSceneManager()));
            }
        }, (screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 100, buttonWidth, buttonHeight);
    }

    @Override
    public void update(float deltaTime) {
        // Update logic for leaderboard here
    }

    @Override
    public void render() {
        super.render(); // Call the render method of the superclass
    }

    @Override
    public void dispose() {
        super.dispose(); // Call the dispose method of the superclass
        // Dispose any additional resources specific to Leaderboard
    }
}
