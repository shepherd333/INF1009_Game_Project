package com.mygdx.game.GameLayer.Scenes;
import GameEngine.InputControl.UIButtonManager;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
//End Menu for the game
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

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}