package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Lifecycle.AudioManager;

public class MainMenu extends BaseScene {

    public MainMenu(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected String getBackgroundTexturePath() {
        return "MainMenu.png"; // Set the path to the background texture for the main menu
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass

        int buttonWidth = 100;
        int buttonHeight = 25;
        int buttonSpacing = 10;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int totalHeight = (buttonHeight + buttonSpacing) * 5;

        int verticalOffset = (screenHeight - totalHeight) / 2;
        TextButton playButton = new TextButton("Play", skin);
        playButton.setSize(buttonWidth, buttonHeight);
        playButton.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 70); // Adjust Y position as needed
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the PlayScene
                getSceneManager().set(new LevelMenu(getSceneManager()));
            }
        });
        stage.addActor(playButton);

        TextButton howtoplayButton = new TextButton("How to Play", skin);
        howtoplayButton.setSize(buttonWidth, buttonHeight);
        howtoplayButton.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 100); // Adjust Y position as needed
        howtoplayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the HowToPlayScene
                getSceneManager().set(new GameObjective(getSceneManager()));
            }
        });
        stage.addActor(howtoplayButton);

        TextButton leaderboardButton = new TextButton("Leaderboard", skin);
        leaderboardButton.setSize(buttonWidth, buttonHeight);
        leaderboardButton.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 130); // Adjust Y position as needed
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the LeaderboardScene
                getSceneManager().set(new Leaderboard(getSceneManager()));
            }
        });
        stage.addActor(leaderboardButton);

        TextButton mutebtn = new TextButton(AudioManager.getInstance().isMusicMuted() ? "Unmute" : "Mute", skin);
        mutebtn.setSize(buttonWidth, buttonHeight);
        mutebtn.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 160); // Adjust Y position as needed
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
        exitButton.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 190); // Adjust Y position as needed
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    @Override
    public void render() {
        super.render(); // Call the render method of the superclass
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); // Call the resize method of the superclass
    }

    @Override
    public void dispose() {
        super.dispose(); // Call the dispose method of the superclass
        // Dispose any additional resources specific to MainMenu
    }
}
