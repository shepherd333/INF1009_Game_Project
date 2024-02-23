package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Game_Engine;

// The PauseMenu class extends the Scene class to create a pause menu for the game.
public class PauseMenu extends Scene {
    // Constructor for PauseMenu. Calls the superclass constructor with parameters specific to the PauseMenu.
    public PauseMenu(SceneManager sceneManager) {
        super(sceneManager, "PauseMenu.png", "This is the PauseMenu Scene.");
        // "PauseMenu.png" is the background texture for the pause menu,
        // and "This is the PauseMenu Scene." is a description or title for this scene.
    }

    @Override
    public void initialize() {
        // Initialize any additional resources if needed
    }

    @Override
    public void update(float deltaTime) {
        // Handle any animations or transitions in the menu.
    }

    @Override
    public void render() {
        // Render method for drawing the pause menu to the screen.
        super.render(); // Call the render method of the superclass (Scene) to handle common rendering tasks, such as drawing the background.
        batch.begin();
        // Draw additional elements for the pause menu here, such as buttons or menu items.
        batch.end();
    }

    @Override
    public void handleInput() {
        // Handle user input specific to the pause menu.
        // For example, toggling the game's music on or off when the M key is pressed.
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            // Toggle the music mute state in the Game_Engine class.
            Game_Engine.isMusicMuted = !Game_Engine.isMusicMuted;
        }
    }

    @Override
    public void dispose() {
        // Dispose of any resources that are specific to the pause menu when they are no longer needed or when the scene is being destroyed.
        // Always call the superclass dispose method to ensure proper cleanup.
        super.dispose();
    }
}