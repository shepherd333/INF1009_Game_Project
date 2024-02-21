package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Scene implements SceneInterface {
    protected SceneManager sceneManager;

    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    protected Viewport viewport;

    public Scene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(800, 600, camera);
        camera.position.set(400, 300, 0);
    }

    @Override
    public void initialize() {
        // Common initialization logic for all scenes
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    @Override
    public void dispose() {
        batch.dispose();
        // Dispose of other shared resources here
    }

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    // Abstract methods to be implemented by each specific scene
    @Override
    public abstract void update(float deltaTime);

    @Override
    public abstract void render();

    @Override
    public abstract void handleInput();
}


