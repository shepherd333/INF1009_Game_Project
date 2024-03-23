package com.mygdx.game.Scenes;

// Defines the essential structure and lifecycle methods for scenes in a LibGDX game.
public interface SceneInterface {
    public void initialize(); // Initialize scene resources and setups
    public void update(float deltaTime); // Update game logic, deltaTime is the time between frames
    public void render(); // Render the scene
    public void resize(int width, int height); // Resize the scene to the specified width and height
    public void dispose(); // Dispose of any resources used by the scene
    SceneManager getSceneManager(); // Get the SceneManager instance associated with the scene
}

