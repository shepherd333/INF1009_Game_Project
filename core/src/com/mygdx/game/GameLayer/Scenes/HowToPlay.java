package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
/**
 * HowToPlay is a class that represents the how to play scene in a game.
 * This includes:
 * - Holding a reference to a UIButtonManager.
 * - Providing a constructor to initialize the HowToPlay with a SceneManager. In this constructor, it calls the superclass constructor with the SceneManager.
 * - Overriding a method to specify the background texture path for the HowToPlay scene. In this method, it returns the path to the background texture for the HowToPlay scene.
 * - Overriding a method to initialize the HowToPlay. In this method, it calls the initialize method of the superclass, initializes the UIButtonManager, and sets up the UI buttons for the HowToPlay scene.
 */
public class HowToPlay extends BaseScene {
    private UIButtonManager uiButtonManager;
    public HowToPlay(SceneManager sceneManager) {
        super(sceneManager);
    }
    // Method to specify the background texture path for the HowToPlay scene
    @Override
    protected String getBackgroundTexturePath() {
        return "HowToPlay.jpg"; // Set the path to the background texture for the how to play scene
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass
        uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupHowToPlay();
    }
}
