package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import GameEngine.InputControl.UIButtonManager;

//Level Menu for the game
public class LevelMenu extends BaseScene {

    // Constructor for the EndMenu scene
    private UIButtonManager uiButtonManager;
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
        uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupLevelMenu();
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
