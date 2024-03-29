package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;

/**
 * MainMenu is a class that represents the main menu scene in a game.
 * This includes:
 * - Holding a reference to a UIButtonManager.
 * - Providing a constructor to initialize the MainMenu with a SceneManager. In this constructor, it calls the superclass constructor with the SceneManager.
 * - Overriding a method to specify the background texture path for the MainMenu scene. In this method, it returns the path to the background texture for the MainMenu scene.
 * - Overriding a method to initialize the MainMenu. In this method, it calls the initialize method of the superclass, initializes the UIButtonManager, and sets up the UI buttons for the MainMenu scene.
 * - Overriding a method to render the MainMenu. In this method, it calls the render method of the superclass, begins a new batch for rendering, draws the background sprite on the batch, ends the batch, and draws the stage.
 */
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
}
