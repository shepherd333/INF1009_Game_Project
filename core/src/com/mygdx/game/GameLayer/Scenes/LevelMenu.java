package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import GameEngine.SimulationLifecycleManagement.LevelConfig;
import com.mygdx.game.GameLayer.enums.ItemType;


public class LevelMenu extends BaseScene {
    public LevelMenu(SceneManager sceneManager) {
        super(sceneManager);
    }
    @Override
    protected String getBackgroundTexturePath() {
        return "MainMenu.png"; // Set the path to the background texture for the main menu
    }
    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass

        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonSpacing = 10;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int totalHeight = (buttonHeight + buttonSpacing) * 5;
        int verticalOffset = (screenHeight - totalHeight) / 2;

        TextButton level1btn = new TextButton("Level 1", skin);
        level1btn.setSize(buttonWidth, buttonHeight);
        level1btn.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 70); // Adjust Y position as needed
        level1btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelConfig defaultLevelConfig = new LevelConfig(0.5f, 1f, 0,
                        new ItemType[] { ItemType.GLASS, ItemType.PAPER, ItemType.PLASTIC });
                getSceneManager().set(new GamePlay(getSceneManager(), defaultLevelConfig));
            }
        });
        stage.addActor(level1btn);
        TextButton level2btn = new TextButton("Level 2", skin);
        level2btn.setSize(buttonWidth, buttonHeight);
        level2btn.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 140); // Adjust Y position as needed
        level2btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelConfig lvl2config = new LevelConfig(1f, 1.5f, 2,
                        new ItemType[] { ItemType.GLASS, ItemType.PAPER, ItemType.PLASTIC, ItemType.METAL}
                        );
                getSceneManager().set(new GamePlay(getSceneManager(), lvl2config));
            }
        });
        stage.addActor(level2btn);
        TextButton level3btn = new TextButton("Level 3", skin);
        level3btn.setSize(buttonWidth, buttonHeight);
        level3btn.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 210); // Adjust Y position as needed
        level3btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelConfig lvl3config = new LevelConfig(1.5f, 2f, 3,
                        new ItemType[] { ItemType.GLASS, ItemType.PAPER, ItemType.PLASTIC, ItemType.METAL, ItemType.TRASH});
                getSceneManager().set(new GamePlay(getSceneManager(),lvl3config));
            }
        });
        stage.addActor(level3btn);
        TextButton backbtn = new TextButton("Back", skin);
        backbtn.setSize(buttonWidth, buttonHeight);
        backbtn.setPosition((screenWidth - buttonWidth) / 2, screenHeight - verticalOffset - 280); // Adjust Y position as needed
        backbtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the PlayScene
                getSceneManager().set(new MainMenu(getSceneManager()));
            }
        });
        stage.addActor(backbtn);
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