package com.mygdx.game.GameLayer.Scenes;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/**
 * PauseMenu is a class that represents the pause menu scene in a game.
 * This includes:
 * - Holding references to a ShapeRenderer and a UIButtonManager.
 * - Providing a constructor to initialize the PauseMenu with a SceneManager. In this constructor, it calls the superclass constructor with the SceneManager and initializes the ShapeRenderer.
 * - Overriding a method to specify the background texture path for the PauseMenu scene. In this method, it returns the path to the background texture for the PauseMenu scene.
 * - Overriding a method to initialize the PauseMenu. In this method, it calls the initialize method of the superclass, initializes the UIButtonManager, and sets up the UI buttons for the PauseMenu scene.
 * - Overriding a method to render the PauseMenu. In this method, it begins a new batch for rendering, draws the background sprite on the batch, ends the batch, updates the stage with the time since the last frame, and draws the stage.
 */
public class PauseMenu extends BaseScene {
    private ShapeRenderer shapeRenderer;
    private UIButtonManager uiButtonManager;

    public PauseMenu(SceneManager sceneManager) {
        super(sceneManager);
        shapeRenderer = new ShapeRenderer();
    }
    @Override
    protected String getBackgroundTexturePath() {
        return "PauseMenu.jpg"; // Set the path to the background texture for the pause menu
    }
    @Override
    public void initialize() {
        super.initialize(); // Call the initialize method of the superclass
        uiButtonManager = new UIButtonManager(skin, stage, getSceneManager());
        uiButtonManager.setupPauseMenu();
    }
    @Override
    public void render() {
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
