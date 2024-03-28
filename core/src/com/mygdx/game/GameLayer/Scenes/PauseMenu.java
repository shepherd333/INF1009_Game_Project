package com.mygdx.game.GameLayer.Scenes;

import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import GameEngine.SimulationLifecycleManagement.AudioManager;

public class PauseMenu extends BaseScene {
    private ShapeRenderer shapeRenderer;

    public PauseMenu(SceneManager sceneManager) {
        super(sceneManager);
        shapeRenderer = new ShapeRenderer();
    }


    @Override
    protected String getBackgroundTexturePath() {
        return "PauseMenu.jpg"; // Set the path to the background texture for the pause menu
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass

        int buttonWidth = 200;
        int buttonHeight = 50;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int verticalOffset = (screenHeight - ((buttonHeight + 5) * 5)) / 2; // Calculate vertical offset for button placement

        // Resume button setup
        addButton("Resume", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getSceneManager().popScene(); // Return to the gameplay scene
            }
        }, (screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 70, buttonWidth, buttonHeight);

        addButton("How to Play", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getSceneManager().set(new PauseMenuHowToPlay(getSceneManager())); // Transition to the main menu
            }
        }, (screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 140, buttonWidth, buttonHeight);

        // Mute button setup
        final TextButton muteButton = new TextButton(AudioManager.getInstance().isMusicMuted() ? "Unmute" : "Mute", skin);
        muteButton.setSize(buttonWidth, buttonHeight); // Set the size as before
        muteButton.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 210);
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.getInstance().toggleMusicMute();
                // Directly set the text of the muteButton
                muteButton.setText(AudioManager.getInstance().isMusicMuted() ? "Unmute" : "Mute");
            }
        });
        stage.addActor(muteButton);

        // Back to Home button setup
        addButton("Exit to Home", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getSceneManager().set(new MainMenu(getSceneManager())); // Transition to the main menu
            }
        }, (screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 280, buttonWidth, buttonHeight);
    }

    @Override
    public void render() {
        // Enable blending using LibGDX's GL20 wrapper
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Start the SpriteBatch to draw the background and other textures
        batch.begin();
        // Assuming bgSprite is correctly initialized and set to cover the whole screen
        bgSprite.draw(batch);
        batch.end();

        // Update and draw the stage which contains UI elements like buttons
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void update(float deltaTime) {
        // Update logic here, if any
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); // Call the resize method of the superclass
    }

    @Override
    public void dispose() {
        super.dispose(); // Call the dispose method of the superclass
    }
}
