package com.mygdx.game.GameLayer.Scenes;
import GameEngine.InputControl.UIButtonManager;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
//GameObjective scene for the game
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

    @Override
    public void update(float deltaTime) {
        // Update logic here, if any
    }

    @Override
    public void render() {
        super.render(); // Call the render method of the superclass
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); // Call the resize method of the superclass
    }

    @Override
    public void dispose() {
        super.dispose(); // Call the dispose method of the superclass
        // Dispose any additional resources specific to HowToPlay
    }
}
