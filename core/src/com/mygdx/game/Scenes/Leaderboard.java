package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Lifecycle.HighScoreManager;

// The Leaderboard class extends Scene to represent the leaderboard scene of the game.
// This is where players can view high scores.
public class Leaderboard extends Scene {
    public Vector3 tmp = new Vector3();  // Temporary vector for handling touch input coordinates.
    public HighScoreManager highScoreManager = HighScoreManager.getInstance(); // Access to the singleton HighScoreManager.

    // Constructor for the Leaderboard scene. Sets up the scene with a specific background and description.
    public Leaderboard(SceneManager sceneManager) {
        super(sceneManager, "Leaderboard.png", "This is the LeaderBoard Scene.");
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
        super.render(); // Calls render method from the Scene superclass to handle common rendering, such as drawing the background.
        batch.begin();
        // Scaling font (reduce quality, consider using a larger font as mentioned)
        font.getData().setScale(1.3f);
        // Display the high score on the leaderboard.
        String highScoreDisplay = "High Score: " + highScoreManager.getInstance().getHighestScoreFormatted();
        GlyphLayout highScoreLayout = new GlyphLayout(font, highScoreDisplay);
        float highScoreX = viewport.getWorldWidth() - highScoreLayout.width - 250;
        float highScoreY = viewport.getWorldHeight() - highScoreLayout.height - 200;
        font.draw(batch, highScoreDisplay, highScoreX, highScoreY);

        batch.end();
    }
    // Input handling, specifically for touch input.
    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            // Unprojects the screen touch coordinates to the world coordinates.
            camera.unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            // Here, you can add additional logic to respond to touch input, such as buttons on the leaderboard scene.
        }
    }

    @Override
    public void dispose() {
        // Dispose of resources when they are no longer needed or when the scene is being destroyed.
        // Always call the superclass dispose to ensure proper cleanup.
        super.dispose();
    }
}