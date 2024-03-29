package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;

/**
 * GameObjective is a class that represents the game objective scene in a game.
 * This includes:
 * - Holding a reference to a UIButtonManager.
 * - Providing a constructor to initialize the GameObjective with a SceneManager. In this constructor, it calls the superclass constructor with the SceneManager.
 * - Overriding a method to specify the background texture path for the GameObjective scene. In this method, it returns the path to the background texture for the GameObjective scene.
 * - Overriding a method to initialize the GameObjective. In this method, it calls the initialize method of the superclass, initializes the UIButtonManager, and sets up the UI buttons for the GameObjective scene.
 */
public class GameObjective extends BaseScene {
    private UIButtonManager uiButtonManager;

    // Constructor for the GameObjective scene
    public GameObjective(SceneManager sceneManager) {
        super(sceneManager);
    }

    // Method to specify the background texture path for the Gam scene
    @Override
    protected String getBackgroundTexturePath() {
        return "GameObjective.jpg"; // Set the path to the background texture for the how to play scene
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass
        uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupGameObjective();
    }
}
