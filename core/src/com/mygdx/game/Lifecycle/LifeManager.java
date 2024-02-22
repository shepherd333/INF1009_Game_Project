package com.mygdx.game.Lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.Scenes.SceneManager;

public class LifeManager {
    private int lives;
    private boolean isGameOver;
    private SpriteBatch batch;
    private BitmapFont font;
    private SceneManager sm;
    private static LifeManager instance;

    public void initializeSceneManager(SceneManager sm) {
        this.sm = sm;
    }

    public LifeManager(int initialLives) {
        this.lives = initialLives;
        this.isGameOver = false;
    }

    public static synchronized LifeManager getInstance() {
        if (instance == null) {
            instance = new LifeManager(5);
        }
        return instance;
    }

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

    private void setGameOver() {
        isGameOver = true;
        System.out.println("Game Over!");
        //sm.transitionTo("EndMenu", 1);
        // Perform any game over logic here, like resetting the game or showing a game over screen
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getLives() {
        return lives;
    }

    public void addLife() {
        lives++;
        System.out.println("Extra life gained! Lives: " + lives);
    }

    public void render(){
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        // Draw high score
        font.getData().setScale(1.5f);
        //final float padding = 200; // Padding from the top and right screen edges

        GlyphLayout layout = new GlyphLayout(); // Consider making this a field to avoid re-allocating each frame

        float width = layout.width; // Use this for calculating xPosition
        float xPosition = Gdx.graphics.getWidth() - width - 500;
        float yPosition = Gdx.graphics.getHeight() - layout.height - 10;

        String lifeDisplay = "LIVES: " + getLives();
        layout.setText(font, lifeDisplay);
        font.draw(batch, lifeDisplay, xPosition, yPosition);
    }
}
