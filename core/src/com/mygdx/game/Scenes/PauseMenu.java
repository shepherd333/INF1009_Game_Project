package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseMenu extends BaseScene {
    private ShapeRenderer shapeRenderer;

    public PauseMenu(SceneManager sceneManager) {
        super(sceneManager);
        shapeRenderer = new ShapeRenderer();
    }


    @Override
    protected String getBackgroundTexturePath() {
        return "PauseMenu.png"; // Set the path to the background texture for the pause menu
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass

        int buttonWidth = 100;
        int buttonHeight = 25;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int verticalOffset = (screenHeight - ((buttonHeight + 5) * 5)) / 2; // Calculate vertical offset for button placement

        // Resume button setup
        addButton("Resume", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getSceneManager().popScene(); // Return to the gameplay scene
            }
        }, (screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 100, buttonWidth, buttonHeight);

        // Mute button setup
        final TextButton muteButton = new TextButton(AudioManager.getInstance().isMusicMuted() ? "Unmute" : "Mute", skin);
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.getInstance().toggleMusicMute();
                muteButton.setText(AudioManager.getInstance().isMusicMuted() ? "Unmute" : "Mute");
                event.stop(); // Stop the event from propagating to parent actors
            }
        });
        addButton(muteButton.getText().toString(), muteButton.getClickListener(), (screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 120, buttonWidth, buttonHeight);

        // Back to Home button setup
        addButton("Home", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getSceneManager().set(new MainMenu(getSceneManager())); // Transition to the main menu
            }
        }, (screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 140, buttonWidth, buttonHeight);
    }

    @Override
    public void render() {
        // Enable blending using LibGDX's GL20 wrapper
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Ensure the shape renderer's matrix is set correctly
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);

        // Draw a semi-transparent grey rectangle to serve as the overlay
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.05f); // Semi-transparent grey
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        // Disable blending to reset state for other rendering operations
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Proceed with the rest of the pause menu rendering
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
