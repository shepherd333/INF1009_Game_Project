package com.mygdx.game.GameLayer.Scenes;

import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class PauseMenuHowToPlay extends BaseScene {

    public PauseMenuHowToPlay(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected String getBackgroundTexturePath() {
        return "HowToPlay.jpg"; // Set the path to the background texture for the how to play scene
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass

        int buttonWidth = 100;
        int buttonHeight = 25;
        int rightMargin = 10;
        int topMargin = 10;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int verticalOffset = (screenHeight - buttonHeight) / 2;

        addButton("Back", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the PlayScene
                getSceneManager().set(new PauseMenu(getSceneManager()));
            }
        }, screenWidth - buttonWidth - rightMargin, screenHeight - buttonHeight - topMargin, buttonWidth, buttonHeight);

    }

    @Override
    public void update(float deltaTime) {
        // Update logic here, if any
    }

    @Override
    public void render() {
        super.render(); // Call the render method of the superclass
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); // Call the resize method of the superclass
    }

    @Override
    public void dispose() {
        super.dispose(); // Call the dispose method of the superclass
        // Dispose any additional resources specific to HowToPlay
    }
}

