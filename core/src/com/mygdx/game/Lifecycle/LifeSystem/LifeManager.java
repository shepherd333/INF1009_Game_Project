package com.mygdx.game.Lifecycle.LifeSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Scenes.SceneManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LifeManager {
    private int lives;
    private boolean isGameOver;
    private Stage stage; // Replaces SpriteBatch for UI rendering
    private Label livesLabel; // Display for the lives
    private SceneManager sm;
    private static LifeManager instance;
    private Array<Image> lifeImages = new Array<>();
    private final float heartWidthPercentage = 0.05f; // 5% of the screen width
    private final float heartHeightPercentage = 0.05f; // 5% of the screen height
    private final float paddingPercentage = 0.02f; // 2% padding from the edges




    // Constructor initializes the manager with a specific number of initial lives.
    public LifeManager(Stage stage, int initialLives) {
        this.stage = stage; // Use the passed stage instead of creating a new one
        this.lives = initialLives;
        this.isGameOver = false;
        loadLifeImages();
        positionHearts();
    }

    private void loadLifeImages() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float heartWidth = screenWidth * heartWidthPercentage;
        float heartHeight = screenHeight * heartHeightPercentage;
        float rightPadding = screenWidth * paddingPercentage;
        float topPadding = screenHeight * paddingPercentage;

        for (int i = 1; i <= 3; i++) {
            Texture texture = new Texture("heart" + ".png");
            Image image = new Image(texture);
            image.setSize(heartWidth, heartHeight);
            lifeImages.add(image);
            image.setVisible(false); // Set visibility as needed
            stage.addActor(image);
        }

    }


    private void positionHearts() {
        float screenWidth = stage.getWidth();
        float screenHeight = stage.getHeight();

        float heartWidth = screenWidth * heartWidthPercentage;
        float heartHeight = screenHeight * heartHeightPercentage;
        float rightPadding = screenWidth * paddingPercentage;
        float topPadding = screenHeight * paddingPercentage;

        // Calculate positions and update Image actors
        float xStart = screenWidth - rightPadding - heartWidth;
        float yStart = screenHeight - topPadding - heartHeight;

        for (int i = 0; i < lifeImages.size; i++) {
            Image heart = lifeImages.get(i);
            float xPosition = xStart - i * (heartWidth + rightPadding);
            float yPosition = yStart;
            heart.setPosition(xPosition, yPosition);
            heart.setSize(heartWidth, heartHeight); // Set the size of the heart
            // Initially, you might want to set all hearts as visible,
            // or you might want to set visibility according to the number of lives
            heart.setVisible(i < lives);
        }
    }


    // Singleton pattern to ensure only one instance of LifeManager exists.
//    public static synchronized LifeManager getInstance() {
//        if (instance == null) {
//            instance = new LifeManager(3); // Default initial lives set to 5.
//        }
//        return instance;
//    }

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
        lives += 1; // Example increment, adjust according to game design.
    }

    // Resets the life count to indicate game over or pre-game state.
    public void endLife() {
        lives = -1;
    }

    // Renders the current life count on screen.
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void updateLives(int lives) {
        // Logic to update lives and determine which Image to show
        for (int i = 0; i < lifeImages.size; i++) {
            Image heart = lifeImages.get(i);
            // ... Set position and size ...
            heart.setVisible(i < lives); // Only make the heart visible if it represents a current life.
        }

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    // Ensures resources are properly disposed of to avoid memory leaks.
    public void dispose() {
        if (stage != null) {
            stage.dispose(); // Disposes the stage and all actors attached to it
        }
        for (Image image : lifeImages) {
            Drawable drawable = image.getDrawable();
            if (drawable instanceof TextureRegionDrawable) {
                TextureRegionDrawable textureRegionDrawable = (TextureRegionDrawable) drawable;
                if (textureRegionDrawable.getRegion() != null) {
                    textureRegionDrawable.getRegion().getTexture().dispose();
                }
            }
        }
        // If you have a Skin object that's only used within this class, you should dispose of it here as well.
        // However, if the Skin is shared across different parts of your application, make sure it's disposed of appropriately elsewhere to avoid accessing disposed resources.
    }
}
