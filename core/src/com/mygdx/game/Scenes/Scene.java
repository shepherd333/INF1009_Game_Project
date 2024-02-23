package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game_Engine;
// Defines an abstract base class for scenes in the game, providing common functionality and structure.
public abstract class Scene implements SceneInterface {
    // Camera for viewing the scene.
    protected OrthographicCamera camera;
    // Viewport to manage how the scene is scaled and displayed.
    protected Viewport viewport;
    // Reference to the SceneManager that manages transitions between scenes.
    protected SceneManager sceneManager;
    // SpriteBatch for efficient rendering of textures or sprites in this scene.
    protected SpriteBatch batch;
    // Texture for the scene's background.
    protected Texture img;
    // Font for drawing text within the scene.
    protected BitmapFont font;
    // Text associated with the scene, potentially for instructions or titles.
    protected String sceneText;

    // Constructor initializing the scene with a camera, viewport, texture, and text.
    protected Scene(SceneManager sceneManager, String texturePath, String sceneText) {
        this.sceneManager = sceneManager;
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();
        this.img = new Texture(Gdx.files.internal(texturePath)); // Loads the background texture.
        this.font = new BitmapFont(); // Initializes the font for text rendering.
        this.sceneText = sceneText; // Sets the text to be displayed within the scene.
        // Creates a FitViewport to maintain aspect ratio across different screen sizes.
        this.viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
    }
    // Abstract method for initializing any scene-specific elements or logic.
    public abstract void initialize();

    public abstract void update(float deltaTime);

    // Renders the scene, including the background image and scene text.
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1); // Clears the screen with a solid color.
        camera.update(); // Updates the camera's projection matrix.
        batch.setProjectionMatrix(camera.combined); // Sets the SpriteBatch to use the camera's view.

        batch.begin();
        batch.draw(img, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // Draws the scene background.
        font.draw(batch, sceneText, 1, 450); // Renders the scene's text.
        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true); // Ensures the viewport correctly scales content.
    }
    // Disposes of resources used by the scene to free up memory.
    public void dispose() {
        batch.dispose(); // Disposes the SpriteBatch.
        img.dispose(); // Releases the texture resource.
        font.dispose(); // Cleans up the font resource.
    }
    // Setter method for changing the scene manager reference.
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    // Abstract method for handling input; specifics depend on the scene implementation.
    public abstract void handleInput();
}