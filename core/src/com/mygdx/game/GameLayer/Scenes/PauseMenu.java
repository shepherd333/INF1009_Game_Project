package com.mygdx.game.GameLayer.Scenes;

import GameEngine.InputControl.UIButtonManager;
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
    private UIButtonManager uiButtonManager;

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
        uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupPauseMenu();
    }
    @Override
    public void render() {
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
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
