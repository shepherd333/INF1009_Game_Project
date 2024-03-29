package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import GameEngine.InputControl.UIButtonManager;

//Main Menu for the game
public class MainMenu extends BaseScene {
    private UIButtonManager uiButtonManager;

    public MainMenu(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected String getBackgroundTexturePath() {
        return "MainMenu.png";
    }

    @Override
    public void initialize() {
        super.initialize();
        uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupMainMenu();
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
    }
}
