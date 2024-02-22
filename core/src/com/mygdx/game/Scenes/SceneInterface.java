package com.mygdx.game.Scenes;

public interface SceneInterface {

    public void initialize(); // Initialize scene resources and setups
    public void update(float deltaTime); // Update game logic, deltaTime is the time between frames
    public void render(); // Render the scene
    public void resize(int width, int height);
    public void handleInput(); // (Optional) Handle user inputs
    public void setSceneManager(SceneManager sceneManager); // Set the scene manager
    public void dispose(); // Dispose of any resources used by the scene
}

