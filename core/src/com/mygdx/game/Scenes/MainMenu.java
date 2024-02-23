package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

// MainMenu class extends the Scene class to represent the main menu of the game.
public class MainMenu extends Scene {
    public Vector3 tmp = new Vector3(); // Temporary vector for unprojecting touch coordinates

    // Constructor for MainMenu. Calls the superclass constructor with parameters specific to the MainMenu.
    public MainMenu(SceneManager sceneManager) {
        super(sceneManager, "MainMenu.png", "Welcome to the MainMenu.");
        // "MainMenu.png" is assumed to be the background texture for the menu,
        // and "Welcome to the MainMenu." is a description or title for this scene.
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
        // Render the MainMenu scene.
        super.render();
        batch.begin();
        // Draw menu options or instructions for the player using the font. These could be replaced with interactive UI elements.
        font.draw(batch, "Press Z on keyboard to transit to Gameplay Scene.", 1, 400);
        font.draw(batch, "Press X to transit to LeaderBoard Scene.", 1, 350);
        font.draw(batch, "Press SPACEBAR to transit to EndMenu Scene.", 1, 300);
        font.draw(batch,"Press M to mute/unmute the audio.", 1, 250);


        batch.end();
    }

    @Override
    public void handleInput() {
        // Handle user input specific to the MainMenu.
        // For example, touch or click input can be processed here to navigate to other scenes based on the location of the touch.
        if (Gdx.input.justTouched()) {
            // Convert screen coordinates of the touch input to world coordinates.
            camera.unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            // Based on the coordinates, you could determine which menu item was selected and transition to a different scene.
        }
    }

    @Override
    public void dispose() {
        // Dispose of any resources specific to the MainMenu when they are no longer needed or when the application is closing.
        // Always call the superclass dispose method to ensure proper cleanup.
        super.dispose();
    }
}