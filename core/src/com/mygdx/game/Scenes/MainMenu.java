package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenu extends Scene {
    public Vector3 tmp = new Vector3();
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture bg;
    private Sprite bgSprite;


    public MainMenu(SceneManager sceneManager) {
        super(sceneManager);
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));

        bg = new Texture(Gdx.files.internal("MainMenu.png"));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int buttonWidth = 100;
        int buttonHeight = 25;
        int buttonSpacing = 10;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int totalHeight = (buttonHeight + buttonSpacing) * 5;

        int verticalOffset = (screenHeight - totalHeight) / 2;
        TextButton playButton = new TextButton("Play", skin);
        playButton.setSize(buttonWidth, buttonHeight);
        playButton.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 100); // Adjust Y position as needed
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the PlayScene
                sceneManager.set(new GamePlay(sceneManager));
            }
        });
        stage.addActor(playButton);

        TextButton leaderboardButton = new TextButton("Leaderboard", skin);
        leaderboardButton.setSize(buttonWidth, buttonHeight);
        leaderboardButton.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 120); // Adjust Y position as needed
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the PlayScene
                sceneManager.set(new Leaderboard(sceneManager));
            }
        });
        stage.addActor(leaderboardButton);

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

        TextButton exitButton = new TextButton("Exit Game", skin);
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 160); // Adjust Y position as needed
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);


    }

    @Override
    public void initialize() {
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void render() {
        super.render();
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        stage.draw();
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

    }
}
