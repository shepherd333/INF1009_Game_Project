package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;

/**
 * LevelMenu is a class that represents the level menu scene in a game.
 * This includes:
 * - Holding a reference to a UIButtonManager.
 * - Providing a constructor to initialize the LevelMenu with a SceneManager. In this constructor, it calls the superclass constructor with the SceneManager.
 * - Overriding a method to specify the background texture path for the LevelMenu scene. In this method, it returns the path to the background texture for the LevelMenu scene.
 * - Overriding a method to initialize the LevelMenu. In this method, it calls the initialize method of the superclass, initializes the UIButtonManager, and sets up the UI buttons for the LevelMenu scene.
 * - Overriding a method to render the LevelMenu. In this method, it calls the render method of the superclass, begins a new batch for rendering, draws the background sprite on the batch, ends the batch, and draws the stage.
 */
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
}
