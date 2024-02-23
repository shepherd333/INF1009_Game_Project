package com.mygdx.game.Lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.Scenes.SceneManager;

public class LifeManager {
    private int lives; // Tracks the number of lives.
    private boolean isGameOver; // Indicates whether the game is over.
    private SpriteBatch batch; // For rendering text on the screen.
    private BitmapFont font; // Font used to display the lives count.
    private SceneManager sm; // Reference to the SceneManager to manage scene transitions.
    private static LifeManager instance; // Singleton instance.

    // Initializes the SceneManager. This allows LifeManager to control scene transitions based on lives.
    public void initializeSceneManager(SceneManager sm) {
        this.sm = sm;
    }

    // Constructor initializes the manager with a specific number of initial lives.
    public LifeManager(int initialLives) {
        this.lives = initialLives;
        this.isGameOver = false;
    }

    // Singleton pattern to ensure only one instance of LifeManager exists.
    public static synchronized LifeManager getInstance() {
        if (instance == null) {
            instance = new LifeManager(3); // Default initial lives set to 5.
        }
        return instance;
    }

    // Decreases the life count by one and checks for game over condition.
    public void loseLife() {
        if (lives > 0) {
            lives--;
            System.out.println("Life lost! Remaining lives: " + lives);
        }
        if (lives <= 0) {
            setGameOver();
            isGameOver = true;
        }
    }

    // Sets lives to a positive number if they were initialized to -1 (indicative of a new game start).
    public void gamecheckStart(){
        if (getLives() == -1){
            addLife(); // Resets lives back to a starting value.
        }
    }

    // Sets the game over condition and potentially triggers a scene change to the game over menu.
    private void setGameOver() {
        isGameOver = true;
        System.out.println("Game Over!");
        // Uncomment the following line to enable scene transition on game over.
        // sm.transitionTo("EndMenu", 1);
    }

    // Returns whether the game is over.
    public boolean isGameOver() {
        return isGameOver;
    }

    // Gets the current number of lives.
    public int getLives() {
        return lives;
    }

    // Adds lives (used for initializing or adding bonus lives).
    public void addLife() {
        lives += 6; // Example increment, adjust according to game design.
    }
    public void addPLife() {
        lives += 0; // Example increment, adjust according to game design.
    }

    // Resets the life count to indicate game over or pre-game state.
    public void endLife() {
        lives = -1;
    }

    // Renders the current life count on screen.
    public void render(){
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        font.getData().setScale(1.5f);

        GlyphLayout layout = new GlyphLayout(); // Use to calculate text width and height for positioning.

        // Calculate positions based on screen width and height for displaying lives.
        float xPosition = Gdx.graphics.getWidth() - layout.width - 500; // Adjust position as needed.
        float yPosition = Gdx.graphics.getHeight() - layout.height - 10; // Adjust position as needed.

        String lifeDisplay = "LIVES: " + getLives();
        layout.setText(font, lifeDisplay);
        font.draw(batch, lifeDisplay, xPosition, yPosition);
    }

    // Ensures resources are properly disposed of to avoid memory leaks.
    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}
