package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;

/**
 * EndMenu is a class that represents the end menu scene in a game.
 * This includes:
 * - Holding a reference to a UIButtonManager.
 * - Providing a constructor to initialize the EndMenu with a SceneManager. In this constructor, it calls the superclass constructor with the SceneManager.
 * - Overriding a method to specify the background texture path for the EndMenu scene. In this method, it returns the path to the background texture for the EndMenu scene.
 * - Overriding a method to initialize the EndMenu. In this method, it calls the initialize method of the superclass, initializes the UIButtonManager, and sets up the UI buttons for the EndMenu scene.
 */
public class EndMenu extends BaseScene {
    private UIButtonManager uiButtonManager;

    // Constructor for the EndMenu scene
    public EndMenu(SceneManager sceneManager) {
        super(sceneManager); // Call the superclass constructor with the scene manager
    }

    // Method to specify the background texture path for the EndMenu scene
    @Override
    protected String getBackgroundTexturePath() {
        return "EndGame.png"; // Set the path to the background texture for the EndMenu scene
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass
        uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupEndMenu(); // Setup UI buttons for the EndMenu scene
    }
}