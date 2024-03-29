package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;

/**
 * PauseMenuHowToPlay is a class that represents the pause menu how to play scene in a game.
 * This includes:
 * - Holding a reference to a UIButtonManager.
 * - Providing a constructor to initialize the PauseMenuHowToPlay with a SceneManager. In this constructor, it calls the superclass constructor with the SceneManager.
 * - Overriding a method to specify the background texture path for the PauseMenuHowToPlay scene. In this method, it returns the path to the background texture for the PauseMenuHowToPlay scene.
 * - Overriding a method to initialize the PauseMenuHowToPlay. In this method, it calls the initialize method of the superclass, initializes the UIButtonManager, and sets up the UI buttons for the PauseMenuHowToPlay scene.
 */
public class PauseMenuHowToPlay extends BaseScene {
    private UIButtonManager uiButtonManager;
    public PauseMenuHowToPlay(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    protected String getBackgroundTexturePath() {
        return "HowToPlay.jpg"; // Set the path to the background texture for the how to play scene
    }

    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass
        UIButtonManager uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupPauseMenuHowToPlay();
    }
}

