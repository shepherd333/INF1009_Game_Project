package com.mygdx.game.Scenes;

public interface SceneInterface {
    void initialize(); // Initialize scene resources and setups
    void update(float deltaTime); // Update game logic, deltaTime is the time between frames
    void render(); // Render the scene
    void resize(int width, int height);
    void dispose(); // Dispose of scene resources
    void setSceneManager(SceneManager sceneManager);
    void handleInput(); // (Optional) Handle user inputs
}

