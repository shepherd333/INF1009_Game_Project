package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Game_Engine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;



public class PauseMenu extends Scene {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture bg;
    private Sprite bgSprite;
    private ShapeRenderer shapeRenderer;

    public PauseMenu(SceneManager sceneManager) {
        super(sceneManager);
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));
        shapeRenderer = new ShapeRenderer();

        bg = new Texture(Gdx.files.internal("PauseMenu.png"));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int buttonWidth = 100;
        int buttonHeight = 25;
        int buttonSpacing = 5;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int totalHeight = (buttonHeight + buttonSpacing) * 5;
        int totalWidth = (buttonWidth + buttonSpacing) * 5;
        int verticalOffset = (screenHeight - totalHeight) / 2;
        int horizontolOffset = (screenWidth - totalWidth) /2;

        // Resume button setup
        TextButton resumeBtn = new TextButton("Resume", skin);
        resumeBtn.setSize(buttonWidth, buttonHeight); // Set the size of the button
        resumeBtn.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 100); // Center the button on the screen
        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.popScene(); // Return to the gameplay scene
            }
        });
        stage.addActor(resumeBtn);

        TextButton mutebtn = new TextButton(AudioManager.getInstance().isMusicMuted() ? "Unmute" : "Mute", skin);
        mutebtn.setSize(buttonWidth, buttonHeight);
        mutebtn.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 140); // Adjust Y position as needed
        mutebtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Toggle the mute state of the audio
                AudioManager.getInstance().toggleMusicMute();
                mutebtn.setText(AudioManager.getInstance().isMusicMuted() ? "Unmute" : "Mute");
            }
        });
        stage.addActor(mutebtn);

//        Gdx.input.setInputProcessor(new InputAdapter() {
//            @Override
//            public boolean keyDown(int keycode) {
//                if (keycode == Input.Keys.SPACE) {
//                    // Transition back to the MenuScene
//                    sceneManager.popScene();
//                    return true;
//                }
//                return false;
//            }
//        });
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
    public void initialize() {
        // Initialize any necessary resources here
    }

    @Override
    public void update(float deltaTime) {
        // Update logic here, if any
    }

    @Override
    public void resize(int width, int height) {
        // Update the stage's viewport when the screen size changes
        stage.getViewport().update(width, height, true);
        // Optionally, you can also adjust the positions of UI elements here if necessary
        // This ensures the viewport is recalculated to match the new screen size
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
